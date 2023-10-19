package main;

import entity.Entity;
import object.Energy;
import object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UIHandler {

    GamePanel gp;
    Graphics2D g2;
    Font fixedsys,delaGothic;
    BufferedImage heart_full,heart_half,heart_empty,energy_full,energy_empty;
    Color opaqueBlack;
    Color lessOpaqueBlack;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounters  = new ArrayList<>();
    public String currentDialogueText = "";
    int titlePlayerSpriteCounter=0;
    int titlePlayerSpriteNum=1;
    public int commandNum=0;
    public int slotCol=0,slotRow=0;
    int subState=0;
    int counter=0;

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

        //Create Hud Object
        Entity heart = new Heart(gp);
        heart_full=heart.image1;
        heart_half=heart.image2;
        heart_empty=heart.image3;

        Entity energy = new Energy(gp);
        energy_full=energy.image1;
        energy_empty=energy.image2;

    }

    public void addMessage(String text){
        messages.add(text);
        messageCounters.add(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        //TitleState
        if (gp.gameState==gp.titleState){
            drawTitleScreen();
        }

        //PlayState
        if (gp.gameState==gp.playState){
            drawPlayerHealthAndEnergy();
            drawMessages();
        }

        //PauseState
        if (gp.gameState==gp.pauseState){
            drawPlayerHealthAndEnergy();
            drawPauseScreen();
        }

        //DialogueState
        if (gp.gameState==gp.dialogueState){
            drawPlayerHealthAndEnergy();
            drawDialogueScreen();
        }

        //CharInfoState
        if (gp.gameState==gp.charInfoState){
            drawCharInfoScreen();
            drawInventory();
        }

        //SettingsState
        if (gp.gameState==gp.settingsState){
            drawPlayerHealthAndEnergy();
            drawSettingsScreen();
        }

        //DeadState
        if (gp.gameState==gp.deadState){
            drawPlayerHealthAndEnergy();
            drawDeadScreen();
        }

        //TransitionState
        if (gp.gameState==gp.transitionState){
            drawTransition();
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

        g2.setColor(Color.black);
        g2.fillRect(x,y,gp.tileSize*2,gp.tileSize*2);
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
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);
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

    public void drawPlayerHealthAndEnergy(){

        //Draw Health
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

        //Draw Energy
        x = gp.tileSize/2-5;
        y = gp.tileSize*2;
        i=0;

        while (i<gp.player.maxEnergy){
            g2.drawImage(energy_empty,x,y,null);
            i++;
            x+=40;
        }

        x = gp.tileSize/2-5;
        i = 0;

        while(i<gp.player.energy){
            g2.drawImage(energy_full,x,y,null);
            i++;
            x+=40;
        }
    }

    public void drawMessages(){
        int messageX = gp.tileSize/2;
        int messageY = gp.screenHeight-gp.tileSize/2;
        g2.setFont(fixedsys.deriveFont(30f));

        for(int i=0;i<messages.size();i++){
            if(messages.get(i)!=null){
                g2.setColor(Color.darkGray);
                g2.drawString(messages.get(i),messageX+3,messageY+3);
                g2.setColor(Color.white);
                g2.drawString(messages.get(i),messageX,messageY);

                int counter = messageCounters.get(i) + 1;
                messageCounters.set(i,counter);
                messageY-=40;

                if(messageCounters.get(i)>180){
                    messages.remove(i);
                    messageCounters.remove(i);
                }
            }
        }
    }

    public void drawPauseScreen(){
        g2.setColor(lessOpaqueBlack);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setFont(fixedsys.deriveFont(Font.BOLD,96f));

        String text = "Oyun Durdu";
        int x = getXPosForCenteredText(text);
        int y = gp.tileSize*2;

        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+4,y+4);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        g2.setFont(fixedsys.deriveFont(Font.PLAIN,48f));

        text = "Oyuna Dön";
        x = getXPosForCenteredText(text);
        y += gp.tileSize*7/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==0){
            if(gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.playState;
                subState=0;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Ayarlar";
        x = getXPosForCenteredText(text);
        y += gp.tileSize*3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1){
            if(gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.settingsState;
                subState=0;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        }
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Kaydet";
        x=getXPosForCenteredText(text);
        y+=gp.tileSize*3/2;
        g2.setColor(new Color(25,0,53));
        g2.drawString(text,x+3,y+3);
        if(commandNum==2)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.darkGray);
        g2.drawString(text,x,y);

        text = "Çıkış";
        x = getXPosForCenteredText(text);
        y += gp.tileSize*3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==3){
            if(gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.settingsState;
                subState=3;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        }
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        gp.keyH.xKeyPressed=false;
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

    public void drawCharInfoScreen(){
        //Create a window
        final int windowX=gp.tileSize/2;
        final int windowY=gp.tileSize;
        final int windowWidth=gp.tileSize*5;
        final int windowHeight=gp.tileSize*9;
        drawSubWindow(windowX,windowY,windowWidth,windowHeight);

        //Text
        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(20f));

        int textX = windowX+20;
        int textY = windowY+gp.tileSize;
        final int lineHeight = 21;

        //Value Names
        g2.drawString("Seviye",textX,textY);
        textY+=lineHeight;
        g2.drawString("Can",textX,textY);
        textY+=lineHeight;
        g2.drawString("Enerji",textX,textY);
        textY+=lineHeight;
        g2.drawString("Güç",textX,textY);
        textY+=lineHeight;
        g2.drawString("Yetenek",textX,textY);
        textY+=lineHeight;
        g2.drawString("Saldırı",textX,textY);
        textY+=lineHeight;
        g2.drawString("Savunma",textX,textY);
        textY+=lineHeight;
        g2.drawString("Deneyim",textX,textY);
        textY+=lineHeight;
        g2.drawString("Sonraki Seviye",textX,textY);
        textY+=lineHeight;
        g2.drawString("Para",textX,textY);
        textY+=lineHeight+30;
        g2.drawString("Ateş Topu",textX,textY);
        textY+=lineHeight+70;
        g2.drawString("Zırh",textX,textY);

        //Values
        int endOfRect = windowX+windowWidth-20;
        textY = windowY+gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = gp.player.hp+"/"+gp.player.maxHp;
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = gp.player.energy+"/"+gp.player.maxEnergy;
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForRightAlignedText(value,endOfRect);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        g2.drawImage(gp.player.currentFireball.down1,endOfRect-gp.tileSize,textY,null);
        textY+=lineHeight*2+gp.tileSize;
        g2.drawImage(gp.player.currentArmor.down1,endOfRect-gp.tileSize,textY,null);
    }

    public void drawSettingsScreen(){
        g2.setColor(lessOpaqueBlack);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(Font.PLAIN,32));

        //Sub Window
        int frameX = gp.tileSize*6, frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8, frameHeight = gp.tileSize*10;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(subState){
            case 0 -> settingsNormal(frameX,frameY);
            case 1 -> settingsFullScreenNotification(frameY);
            case 2 -> settingsControls(frameY);
            case 3 -> settingsQuitConfirmation(frameY);
        }

        gp.keyH.xKeyPressed=false;
    }

    public void drawDeadScreen(){
        g2.setColor(lessOpaqueBlack);
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setFont(fixedsys.deriveFont(Font.BOLD,96f));

        String text = "Oyun Bitti";
        int x = getXPosForCenteredText(text);
        int y = gp.tileSize*2;

        g2.setColor(new Color(150,0,0));
        g2.drawString(text,x+4,y+4);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        g2.setFont(fixedsys.deriveFont(Font.PLAIN,48f));

        text = "Yeniden Doğ";
        x = getXPosForCenteredText(text);
        y += gp.tileSize*9/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==0){
            if(gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.playState;
                gp.retry();
                gp.playMusic(0);
                subState=0;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Çıkış";
        x = getXPosForCenteredText(text);
        y += gp.tileSize*3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1){
            if(gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.titleState;
                subState=0;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        }
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        gp.keyH.xKeyPressed=false;
    }

    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        if(counter==50){
            counter=0;
            gp.gameState=gp.playState;
            gp.currentMap=gp.eventH.tempMap;
            gp.player.worldX=gp.eventH.tempCol*gp.tileSize;
            gp.player.worldY=gp.eventH.tempRow*gp.tileSize;
            gp.eventH.previousEventX=gp.player.worldX;
            gp.eventH.previousEventY=gp.player.worldY;
        }
    }

    public void settingsNormal(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Ayarlar";
        textX=getXPosForCenteredText(text);
        textY=frameY+gp.tileSize;

        g2.drawString(text,textX,textY);

        //Full Screen
        textX=frameX+gp.tileSize/2;
        textY+=gp.tileSize*2;

        if(commandNum==0) {
            if (gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.fullScreenOn = !gp.fullScreenOn;
                subState=1;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString("Tam Ekran",textX,textY);

        //Music
        textY+=gp.tileSize;

        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawString("Müzik",textX,textY);

        //SFX
        textY+=gp.tileSize;

        if(commandNum==2)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawString("Ses E.",textX,textY);

        //Controls
        textY+=gp.tileSize;

        if(commandNum==3) {
            if (gp.keyH.xKeyPressed) {
                gp.playSoundEffect(4);
                subState = 2;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString("Kontroller",textX,textY);

        //Back
        text="Geri Dön";
        textX=getXPosForCenteredText(text);
        textY+=gp.tileSize*10/3;

        if(commandNum==4){
            if(gp.keyH.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.pauseState;
                commandNum=1;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString(text,textX,textY);


        //-----------------------------------------------------------------------------------------------------------------------


        //Full Screen CheckBox
        g2.setColor(Color.white);
        textX=frameX+gp.tileSize*9/2;
        textY=frameY+gp.tileSize*2+26;
        g2.setStroke(new BasicStroke(3));

        if(commandNum==0){
            g2.setColor(Color.yellow);
        }
        else
            g2.setColor(Color.white);

        g2.drawRect(textX,textY,24,24);
        if(gp.fullScreenOn)
            g2.fillRect(textX,textY,24,24);

        //Music Volume
        textY+=gp.tileSize;

        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawRect(textX,textY,120,24);

        int volumeWidth = 20*gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        //SFX Volume
        textY+=gp.tileSize;

        if(commandNum==2)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawRect(textX,textY,120,24);

        volumeWidth = 20*gp.soundEffect.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        gp.config.saveConfig();
    }

    public void settingsFullScreenNotification(int frameY){
        int textX;
        int textY = frameY + gp.tileSize*3;

        if(gp.fullScreenOn)
            currentDialogueText = "Tam ekrana geçmek\niçin oyunu yeniden\nbaşlatmanız gerek.";
        else
            currentDialogueText = "Tam ekrandan çıkmak\niçin oyunu yeniden\nbaşlatmanız gerek";

        for(String line:currentDialogueText.split("\n")){
            textX=getXPosForCenteredText(line);
            g2.drawString(line,textX,textY);
            textY+=40;
        }

        g2.setColor(Color.yellow);
        currentDialogueText = "Tamamdir";
        textX = getXPosForCenteredText(currentDialogueText);
        textY+=80;

        g2.drawString(currentDialogueText,textX,textY);
        if(gp.keyH.xKeyPressed){
            gp.playSoundEffect(4);
            subState=0;
        }

    }

    public void settingsControls(int frameY){

        int textX;
        int textY;

        //Title
        String text = "Kontroller";
        textX=getXPosForCenteredText(text);
        textY=frameY+gp.tileSize;

        g2.drawString(text,textX,textY);

        //Texts
        text = "Yürüme - Oklar";
        textX = getXPosForCenteredText(text);
        textY+=gp.tileSize*2;

        g2.drawString(text,textX,textY);

        text = "Ateş Topu - X";
        textX = getXPosForCenteredText(text);
        textY+=gp.tileSize;

        g2.drawString(text,textX,textY);

        text = "Fırlat - Z";
        textX = getXPosForCenteredText(text);
        textY+=gp.tileSize;

        g2.drawString(text,textX,textY);

        text = "Envanter - C";
        textX = getXPosForCenteredText(text);
        textY+=gp.tileSize;

        g2.drawString(text,textX,textY);

        text = "Durdur - ESC";
        textX = getXPosForCenteredText(text);
        textY+=gp.tileSize;

        g2.drawString(text,textX,textY);

        //Back
        text="Geri Dön";
        textX=getXPosForCenteredText(text);
        textY+=gp.tileSize*7/3;

        if(gp.keyH.xKeyPressed){
            gp.playSoundEffect(4);
            subState=0;
            commandNum=3;
        }
        g2.setColor(Color.yellow);

        g2.drawString(text,textX,textY);


    }

    public void settingsQuitConfirmation(int frameY){
        int textX;
        int textY = frameY + gp.tileSize*2;

        currentDialogueText="Oyundan çıkıp\nana menüye dönmek\nistediğinize\nemin misiniz?";
        for(String line:currentDialogueText.split("\n")){
            textX=getXPosForCenteredText(line);
            g2.drawString(line,textX,textY);
            textY+=40;
        }

        //Yes
        String text = "Evet";
        textX = getXPosForCenteredText(text);
        textY += gp.tileSize;
        if(commandNum==0){
            if(gp.keyH.xKeyPressed){
                subState=0;
                gp.gameState=gp.titleState;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString(text,textX,textY);

        //No
        text = "Hayır";
        textX = getXPosForCenteredText(text);
        textY += gp.tileSize;
        if(commandNum==1){
            if(gp.keyH.xKeyPressed){
                gp.gameState=gp.pauseState;
                subState=0;
                commandNum=3;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString(text,textX,textY);
    }

    public void drawInventory() {
        //Window
        int windowX = gp.screenWidth-gp.tileSize*13/2;
        int windowY = gp.tileSize;
        int windowWidth = gp.tileSize*6;
        int windowHeight = gp.tileSize*5;

        drawSubWindow(windowX,windowY,windowWidth,windowHeight);

        //Slot
        final int slotXStart = windowX + 20;
        final int slotYStart = windowY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize+2;

        //Draw Inventory
        for (int i=0; i<gp.player.inventory.size();i++){
            if(gp.player.inventory.get(i)==gp.player.currentFireball || gp.player.inventory.get(i)==gp.player.currentArmor){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX+=slotSize;
            if(i==4 || i==9 || i==14){
                slotX=slotXStart;
                slotY+=slotSize;
            }
        }

        //Cursor
        int cursorX = slotXStart + slotCol*slotSize;
        int cursorY = slotYStart + slotRow*slotSize;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

        //DescriptionWindow
        int dWindowX = windowX;
        int dWindowY = windowY+windowHeight;
        int dWindowWidth = windowWidth;
        int dWindowHeight = gp.tileSize*4;

        //Description
        int textX = dWindowX + 20;
        int textY = dWindowY + 37;
        g2.setFont(fixedsys.deriveFont(20f));

        int itemIndex = getItemIndex();

        if(itemIndex<gp.player.inventory.size()){
            drawSubWindow(dWindowX,dWindowY,dWindowWidth,dWindowHeight);

            g2.setColor(Color.yellow);
            g2.drawString("["+gp.player.inventory.get(itemIndex).displayedName+"]",textX,textY);
            textY+=24;
            g2.setColor(Color.white);
            for(String line:gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line,textX,textY);
                textY+=24;
            }
        }
    }

    public int getItemIndex(){
        return slotCol+slotRow*5;
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

    public int getXForRightAlignedText(String text,int endOfRect){
        return endOfRect-(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
    }

}
