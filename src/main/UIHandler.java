package main;

import java.awt.*;

public class UIHandler {

    GamePanel gp;
    Graphics2D g2;
    Font arial40;
    Color opaqueBlack;
    Color lessOpaqueBlack;
    Font arial80B;
    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    public boolean gameWon = false;
    public String currentDialogueText = "";

    public UIHandler(GamePanel gp) {
        this.gp = gp;

        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        opaqueBlack = new Color(0,0,0,150);
        lessOpaqueBlack = new Color(0,0,0,220);
    }

    public void showMessage(String msg){
        message = msg;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial40);
        g2.setColor(Color.white);

        //PlayState
        if (gp.gameState==gp.playState){

        }

        //PauseState
        if (gp.gameState==gp.pauseState){
            drawPauseScreen();
        }

        //DialogueState
        if (gp.gameState==gp.dialogueState){
            drawDialogueScreen();
        }

    }

    public void drawPauseScreen(){

        g2.setColor(opaqueBlack);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60));

        String text = "Oyun Durdu";
        int x = getXPosForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){
        //Window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth-gp.tileSize*4;
        int height = gp.tileSize*5;

        drawSubWindow(x,y,width,height);

        //Text
        x+=gp.tileSize/2;
        y+=gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28f));

        for(String line:currentDialogueText.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){

        g2.setColor(lessOpaqueBlack);
        g2.fillRoundRect(x,y,width,height,35,35);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

    }

    public int getXPosForCenteredText(String text){
        int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        return gp.screenWidth/2-textLength/2;
    }

}
