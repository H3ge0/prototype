package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class UIHandler {

    GamePanel gp;
    Graphics2D g2;
    Font arial40;
    Color opaqueBlack;
    Font arial80B;
    public boolean messageOn = false;
    boolean paintedBlack=false;
    public String message = "";
    public boolean gameFinished = false;
    public boolean gameWon = false;

    public UIHandler(GamePanel gp) {
        this.gp = gp;

        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        opaqueBlack = new Color(0,0,0,100);
    }

    public void showMessage(String msg){
        message = msg;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial40);
        g2.setColor(Color.white);

        if (gp.gameState==gp.playState){
            paintedBlack=false;
        }
        if (gp.gameState==gp.pauseState){
            drawPauseScreen();
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

    public int getXPosForCenteredText(String text){
        int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        return gp.screenWidth/2-textLength/2;
    }

}
