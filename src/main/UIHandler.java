package main;

import object.Heart;
import object.Object;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UIHandler {

    GamePanel gp;
    Graphics2D g2;
    Font fixedsys,delaGothic;
    BufferedImage heart_full,heart_half,heart_empty;
    Color opaqueBlack;
    Color lessOpaqueBlack;
    public boolean gameFinished = false;
    public boolean gameWon = false;
    public String currentDialogueText = "";
    int titlePlayerSpriteCounter=0;
    int titlePlayerSpriteNum=1;
    int commandNum=0;

    public UIHandler(GamePanel gp) {
        this.gp = gp;

        InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/fonts/fixedsys.ttf"));

        try {
            fixedsys = Font.createFont(Font.TRUETYPE_FONT,inputStream);
            inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/fonts/DelaGothicOne.ttf"));
            delaGothic = Font.createFont(Font.TRUETYPE_FONT,inputStream);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        opaqueBlack = new Color(0,0,0,150);
        lessOpaqueBlack = new Color(0,0,0,220);

        //Create Hud Project
        Object heart = new Heart(gp);
        heart_full=heart.image1;
        heart_half=heart.image2;
        heart_empty=heart.image3;

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        //TitleState
        if (gp.gameState==gp.titleState){
            drawTitleScreen();
        }

        //PlayState
        if (gp.gameState==gp.playState){
            drawPlayerHealth();
        }

        //PauseState
        if (gp.gameState==gp.pauseState){
            drawPlayerHealth();
            drawPauseScreen();
        }

        //DialogueState
        if (gp.gameState==gp.dialogueState){
            drawPlayerHealth();
            drawDialogueScreen();
        }

    }

    public void drawTitleScreen(){

        //Title
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(fixedsys.deriveFont(Font.BOLD,96f));

        String text = "GOPİ";
        int x = getXPosForCenteredText(text);
        int y = gp.tileSize*2;

        g2.setColor(Color.darkGray);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //GopiImage
        x = gp.screenWidth/2-gp.tileSize;
        y += gp.tileSize*2;
        BufferedImage image = null;

        if (titlePlayerSpriteNum == 1)
            image = gp.player.down1;
        if (titlePlayerSpriteNum == 2)
            image = gp.player.down2;
        if (titlePlayerSpriteNum == 3)
            image = gp.player.down1;
        if (titlePlayerSpriteNum == 4)
            image = gp.player.down2;

        g2.drawImage(image,x,y,gp.tileSize*2,gp.tileSize*2,null);

        titlePlayerSpriteCounter++;
        if(titlePlayerSpriteCounter>20){
            if(titlePlayerSpriteNum==4)
                titlePlayerSpriteNum=1;
            else
                titlePlayerSpriteNum++;
            titlePlayerSpriteCounter=0;
        }

        //Options
        g2.setFont(fixedsys.deriveFont(Font.PLAIN,48f));
        text = "Yeni Oyun";
        x=getXPosForCenteredText(text);
        y+=gp.tileSize*7/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==0)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Devam Et";
        x=getXPosForCenteredText(text);
        y+=gp.tileSize*3/2;
        g2.setColor(new Color(25,0,53));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.darkGray);
        g2.drawString(text,x,y);

        text = "Çıkış";
        x=getXPosForCenteredText(text);
        y+=gp.tileSize*3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==2)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

    }

    public void drawPlayerHealth(){

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while (i<gp.player.maxHp/2){
            g2.drawImage(heart_empty,x,y,null);
            i++;
            x+=gp.tileSize*6/5;
        }

        x = gp.tileSize/2;
        i = 0;

        while(i<gp.player.hp){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.hp)
                g2.drawImage(heart_full,x,y,null);
            i++;
            x+=gp.tileSize*6/5;
        }
    }

    public void drawPauseScreen(){

        g2.setColor(opaqueBlack);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(Font.BOLD,60));

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
        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(26f));

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
