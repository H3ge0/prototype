package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class UIHandler {

    GamePanel gp;
    Font arial40;
    Font arial80B;
    BufferedImage carrotImg;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public boolean gameWon = false;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    double playTime = 0;

    public UIHandler(GamePanel gp) {
        this.gp = gp;

        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);

        getCarrotImage();
    }

    public void getCarrotImage(){
        UtilityTool utility = new UtilityTool();
        try {
            carrotImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/carrot_ico.png")));
            carrotImg = utility.scaleImage(carrotImg,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showMessage(String msg){
        message=msg;
        messageOn =true;
    }

    public void draw(Graphics2D g2){

        g2.setFont(arial40);
        g2.setColor(Color.white);

        if(gameFinished&&gameWon){
            drawGameWon(g2);
        }else if(gameFinished){
            drawGameLost(g2);
        } else {
            g2.drawImage(carrotImg,gp.tileSize/2,gp.tileSize/2,null);
            g2.drawString(":"+gp.player.carrotCount,79,65);

            //Play Time
            playTime+=(double)1/60;
            if(playTime<10)
                g2.drawString("Süre:"+decimalFormat.format(playTime),gp.screenWidth-200,50);
            else if(playTime<100)
                g2.drawString("Süre:"+decimalFormat.format(playTime),gp.screenWidth-225,50);
            else
                g2.drawString("Süre:"+decimalFormat.format(playTime),gp.screenWidth-250,50);

            //Message
            if(messageOn){
                g2.setFont(g2.getFont().deriveFont(30f));
                g2.drawString(message,gp.tileSize/2,gp.screenHeight-40);
                messageCounter++;
                if(messageCounter>120){
                    messageCounter=0;
                    messageOn=false;
                }
            }
        }

    }

    public void drawGameWon(Graphics2D g2){
        String text;
        int textLength;
        int x,y;

        text = "Kazandın!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2-textLength/2;
        y = gp.screenHeight/2-gp.tileSize*2;

        g2.setColor(Color.green);
        g2.drawString(text,x,y);

        g2.setFont(arial80B);
        g2.setColor(Color.yellow);

        text = "TEBRİKLER!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2-textLength/2;
        y = gp.screenHeight/2-gp.tileSize*3;

        g2.drawString(text,x,y);

        g2.setFont(arial40);
        g2.setFont(g2.getFont().deriveFont(24f));
        g2.setColor(Color.white);

        text = "Oyun Süresi: "+decimalFormat.format(playTime)+" saniye";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2-textLength/2;
        y = gp.screenHeight/2+gp.tileSize*2;

        g2.drawString(text,x,y);

        gp.gameThread=null;
    }

    public void drawGameLost(Graphics2D g2){
        String text;
        int textLength;
        int x,y;

        text = "Kaybettin";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2-textLength/2;
        y = gp.screenHeight/2-gp.tileSize*2;

        g2.setColor(Color.red);
        g2.drawString(text,x,y);

        g2.setFont(arial80B);
        g2.setColor(Color.yellow);

        text = "TEBRİKLER!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2-textLength/2;
        y = gp.screenHeight/2-gp.tileSize*3;

        g2.drawString(text,x,y);

        g2.setFont(arial40);
        g2.setFont(g2.getFont().deriveFont(24f));
        g2.setColor(Color.white);

        text = "Oyun Süresi: "+decimalFormat.format(playTime)+" saniye";
        textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();

        x = gp.screenWidth/2-textLength/2;
        y = gp.screenHeight/2+gp.tileSize*2;

        g2.drawString(text,x,y);

        gp.gameThread=null;
    }

}
