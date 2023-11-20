package main;

import entity.Entity;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent=true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.mapAmount][gp.maxWorldCol][gp.maxWorldRow];

        int map=0,col=0,row=0;

        while(map<gp.mapAmount && col<gp.maxWorldCol && row<gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col==gp.maxWorldCol){
                col=0;
                row++;

                if(row==gp.maxWorldRow){
                    row=0;
                    map++;
                }
            }
        }

        setDialogues();
    }

    public void setDialogues(){
        eventMaster.dialogues[0][0] = "Çukura düştün! Canın azaldı!";
        eventMaster.dialogues[1][0] = "Bu sefer çukuru fark ettin. Aptallık bir\nyere kadar...";
        eventMaster.dialogues[2][0] = "Aptallık bir yere kadar...";
        eventMaster.dialogues[3][0] = "Sudan içtin. Nedense canavarlar tekrar doğdu!?";
        eventMaster.dialogues[4][0] = "\"YOOOK.\"";
        eventMaster.dialogues[5][0] = "Suda böcek gördün. \"Ben daha içmem aga.\"";
    }

    public void checkEvent(){
        int xDistance = Math.abs(gp.player.worldX-previousEventX);
        int yDistance = Math.abs(gp.player.worldY-previousEventY);
        int distance = Math.max(xDistance,yDistance);
        if (distance> gp.tileSize/2){
            canTouchEvent=true;
        }

        if(canTouchEvent){
            if(hit(0,27,13,"right"))
                damagePit(gp.dialogueState);
            else if(hit(0,23,7,"up"))
                healingPool(gp.dialogueState);

            else if(hit(0,13,39,"any"))
                teleport(1,12,13,14,gp.inside);
            else if(hit(1,12,13,"any"))
                teleport(0,13,39,14,gp.outside);

            else if(hit(1,12,9,"up"))
                speak(gp.npcs[1][0]);

            else if(hit(0,10,7,"any"))  //Enter Dungeon
                teleport(2,9,41,21,gp.dungeon);
            else if(hit(2,7,40,"any"))  //Exit Dungeon
                teleport(0,10,7,22,gp.outside);

            else if(hit(2,8,7,"any")) //Enter 2nd floor
                teleport(3,26,40,21,gp.dungeon);
            else if(hit(3,24,39,"any")) //Exit 2nd floor
                teleport(2,8,7,22,gp.dungeon);
        }

    }

    public boolean hit(int map,int col, int row, String reqDirection){
        boolean hit = false;

        if(map==gp.currentMap){
            gp.player.collisionBox.x += gp.player.worldX;
            gp.player.collisionBox.y += gp.player.worldY;
            eventRect[map][col][row].x += col*gp.tileSize;
            eventRect[map][col][row].y += row*gp.tileSize;

            if(gp.player.collisionBox.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone){
                if(reqDirection.equals("any")||reqDirection.equals(gp.player.direction)){
                    hit=true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.collisionBox.x = gp.player.collisionBoxDefaultX;
            gp.player.collisionBox.y = gp.player.collisionBoxDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState=gameState;
        if(gp.player.canFall){
            if(gp.player.hp>=2) {
                eventMaster.startDialogue(eventMaster,0);
                gp.playSoundEffect(7);
                gp.player.hp -= 1;
            } else {
                eventMaster.startDialogue(eventMaster,1);
                gp.player.canFall=false;
            }
        } else {
            eventMaster.startDialogue(eventMaster,2);
            gp.playSoundEffect(2);
        }
        canTouchEvent=false;
    }

    public void healingPool(int gameState){
        if(gp.keyH.xKeyPressed){
            gp.objectH.setMonsters();
            gp.gameState=gameState;
            gp.player.attackCanceled=true;
            if(gp.player.hp<gp.player.maxHp) {
                if(gp.player.canDrink){
                    eventMaster.startDialogue(eventMaster,3);
                    gp.playSoundEffect(1);
                    gp.player.hp+=1;
                } else {
                    eventMaster.startDialogue(eventMaster,4);
                    gp.playSoundEffect(2);
                }
            } else {
                if(gp.player.canDrink){
                    gp.player.canDrink = false;
                    eventMaster.startDialogue(eventMaster,5);
                } else {
                    eventMaster.startDialogue(eventMaster,4);
                    gp.playSoundEffect(2);
                }
            }
        }
    }

    public void teleport(int map,int col,int row,int soundNum,int areaNum){
        gp.gameState=gp.transitionState;

        gp.nextArea = areaNum;

        tempMap=map;
        tempCol=col;
        tempRow=row;

        canTouchEvent=false;
        gp.playSoundEffect(soundNum);
    }

    public void speak(Entity entity){
        if(gp.keyH.xKeyPressed){
            gp.gameState=gp.dialogueState;
            gp.player.attackCanceled=true;
            entity.speak();
        }
    }

}
