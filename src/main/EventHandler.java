package main;

import data.Progress;
import entity.Bobo;
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

        eventRect = new EventRect[gp.MAP_AMOUNT][gp.tileManager.getCurrentMapMaxCol()][gp.tileManager.getCurrentMapMaxRow()];

        int map=0,col=0,row=0;

        while(map<gp.MAP_AMOUNT && col<gp.tileManager.getCurrentMapMaxCol() && row<gp.tileManager.getCurrentMapMaxRow()){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col==gp.tileManager.getCurrentMapMaxCol()){
                col=0;
                row++;

                if(row==gp.tileManager.getCurrentMapMaxRow()){
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
        if (distance> gp.TILE_SIZE /2){
            canTouchEvent=true;
        }

        if(canTouchEvent){
            if(hit(0,27,13,"right"))
                damagePit(gp.DIALOGUE_STATE);
            else if(hit(0,23,7,"up"))
                healingWater(gp.DIALOGUE_STATE);

            else if(hit(0,13,39,"any"))
                teleport(1,3,6,14);
            else if(hit(1,3,7,"any"))
                teleport(0,13,39,14);

            else if(hit(1,12,9,"up")) {
                if(gp.npcs[1][0]!=null && gp.npcs[1][0].name.equals(Bobo.npcName)){
                    speak(gp.npcs[1][0]);
                }
            }

            else if(hit(0,10,7,"any"))  //Enter Dungeon
                teleport(2,9,41,21);
            else if(hit(2,7,40,"any"))  //Exit Dungeon
                teleport(0,10,7,22);

            else if(hit(2,8,7,"any")) //Enter 2nd floor
                teleport(3,26,40,21);
            else if(hit(3,24,39,"any")) //Exit 2nd floor
                teleport(2,8,7,22);

            else if(hit(3,25,27,"any")) //Boss
                ipog();
        }

    }

    public boolean hit(int map,int col, int row, String reqDirection){
        boolean hit = false;

        if(map==gp.currentMapNum){
            gp.player.collisionBox.x += gp.player.worldX;
            gp.player.collisionBox.y += gp.player.worldY;
            eventRect[map][col][row].x += col*gp.TILE_SIZE;
            eventRect[map][col][row].y += row*gp.TILE_SIZE;

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

    public void healingWater(int gameState){
        if(gp.keyHandler.xKeyPressed){
            gp.objectHandler.setMonsters(0);
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

    public void teleport(int map,int col,int row,int soundNum){
        gp.gameState=gp.TRANSITION_STATE;

        tempMap=map;
        tempCol=col;
        tempRow=row;

        canTouchEvent=false;
        gp.playSoundEffect(soundNum);
    }

    public void speak(Entity entity){
        if(gp.keyHandler.xKeyPressed){
            gp.gameState=gp.DIALOGUE_STATE;
            gp.player.attackCanceled=true;
            entity.speak();
        }
    }

    public void ipog(){
        if(!gp.bossBattleOn && !Progress.ipogDefeated){
            gp.gameState= gp.CUTSCENE_STATE;
            gp.cutsceneHandler.sceneNum = gp.cutsceneHandler.ipogScene;
        }
    }

}
