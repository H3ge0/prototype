package main;

public class EventHandler {

    GamePanel gp;
    EventRect[][] eventRect;

    int previousEventX, previousEventY;
    boolean canTouchEvent=true;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col=0,row=0;

        while(col<gp.maxWorldCol && row<gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col==gp.maxWorldCol){
                col=0;
                row++;
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
            if(hit(27,13,"right"))
                damagePit(gp.dialogueState);
            if(hit(23,7,"up"))
                healingPool(gp.dialogueState);
        }

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

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;

        gp.player.collisionBox.x += gp.player.worldX;
        gp.player.collisionBox.y += gp.player.worldY;
        eventRect[col][row].x += col*gp.tileSize;
        eventRect[col][row].y += row*gp.tileSize;

        if(gp.player.collisionBox.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if(reqDirection.equals("any")||reqDirection.equals(gp.player.direction)){
                hit=true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.collisionBox.x = gp.player.collisionBoxDefaultX;
        gp.player.collisionBox.y = gp.player.collisionBoxDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

}
