package main;

public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent=true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
        this.gp = gp;

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
                teleport(1,12,13);
            else if(hit(1,12,13,"any"))
                teleport(0,13,39);
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
                gp.uiH.currentDialogueText = "Çukura düştün! Canın azaldı!";
                gp.playSoundEffect(7);
                gp.player.hp -= 1;
            } else {
                gp.uiH.currentDialogueText = "Bu sefer çukuru fark ettin. Aptallık bir\nyere kadar...";
                gp.player.canFall=false;
            }
        } else {
            gp.uiH.currentDialogueText = "Aptallık bir yere kadar...";
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
                    gp.uiH.currentDialogueText = "Sudan içtin. Nedense tuzlu değildi.\n\n\n*İyileştin!*";
                    gp.playSoundEffect(1);
                    gp.player.hp+=1;
                } else {
                    gp.uiH.currentDialogueText = "\"YOOOK.\"";
                    gp.playSoundEffect(2);
                }
            } else {
                if(gp.player.canDrink){
                    gp.player.canDrink = false;
                    gp.uiH.currentDialogueText = "Suda böcek gördün. \"Ben daha içmem aga.\"";
                } else {
                    gp.uiH.currentDialogueText = "\"YOOOK.\"";
                    gp.playSoundEffect(2);
                }
            }
        }
    }

    public void teleport(int map,int col,int row){
        gp.gameState=gp.transitionState;
        tempMap=map;
        tempCol=col;
        tempRow=row;



        canTouchEvent=false;
        gp.playSoundEffect(14);
    }

}
