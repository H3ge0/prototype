package main;

import entity.Entity;
import object.BronzeCoin;
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
    public Font fixedsys,delaGothic;
    BufferedImage heart_full,heart_half,heart_empty,energy_full,energy_empty,coinImg;
    BufferedImage heart_bar_full,heart_bar_half,heart_bar_empty,heart_bar_icon,energy_bar_full,energy_bar_empty,energy_bar_icon;
    Color opaqueBlack;
    Color lessOpaqueBlack;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messageCounters  = new ArrayList<>();
    public String currentDialogueText = "";
    int titlePlayerSpriteCounter=0;
    int titlePlayerSpriteNum=1;
    public int commandNum=0;
    public int playerSlotCol=0,playerSlotRow=0;
    public int npcSlotCol=0,npcSlotRow=0;
    public int subState=0;
    int counter=0;
    public Entity npc;
    Entity huso;
    int charIndex = 0;
    String combinedText = "";

    public UIHandler(GamePanel gp) {
        this.gp = gp;

        huso = new Entity(gp);

        huso.dialogues[0][0] = "Oyun Kaydedildi!";

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

        heart_bar_full=heart.down1v2;
        heart_bar_half=heart.down2v2;
        heart_bar_empty=heart.downidlev2;
        heart_bar_icon=heart.left1;

        Entity energy = new Energy(gp);
        energy_full=energy.image1;
        energy_empty=energy.image2;

        energy_bar_full=energy.left1;
        energy_bar_empty=energy.left2;
        energy_bar_icon=energy.leftidle;

        Entity bronzeCoin = new BronzeCoin(gp);
        coinImg=bronzeCoin.down1;

    }

    public void addMessage(String text){
        messages.add(text);
        messageCounters.add(0);
    }

    public void draw(Graphics2D g2, boolean aboveLighting){
        this.g2 = g2;

        //TitleState
        if (gp.gameState==gp.TITLE_STATE){
            drawTitleScreen();
        }

        //PlayState
        if (gp.gameState==gp.PLAY_STATE){
            drawPlayerHealthAndEnergy();
            drawMonsterHp(aboveLighting);
            gp.miniMap.drawMiniMap(g2);
            drawMessages();
        }

        //PauseState
        if (gp.gameState==gp.PAUSE_STATE){
            drawPlayerHealthAndEnergy();
            drawPauseScreen();
        }

        //DialogueState
        if (gp.gameState==gp.DIALOGUE_STATE){
            drawPlayerHealthAndEnergy();
            drawDialogueScreen();
        }

        //CharInfoState
        if (gp.gameState==gp.CHAR_INFO_STATE){
            drawCharInfoScreen();
            drawInventory(gp.player,true);
        }

        //SettingsState
        if (gp.gameState==gp.SETTINGS_STATE){
            drawPlayerHealthAndEnergy();
            drawSettingsScreen();
        }

        //DeadState
        if (gp.gameState==gp.DEAD_STATE){
            drawPlayerHealthAndEnergy();
            drawDeadScreen();
        }

        //TransitionState
        if (gp.gameState==gp.TRANSITION_STATE){
            drawTransition();
        }

        //TradeState
        if (gp.gameState==gp.TRADE_STATE){
            drawPlayerHealthAndEnergy();
            drawTradeScreen();
        }

        //SleepState
        if (gp.gameState==gp.SLEEP_STATE){
            drawPlayerHealthAndEnergy();
            drawSleepScreen();
        }

    }

    public void drawTitleScreen(){

        //Title
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setFont(fixedsys.deriveFont(Font.BOLD,96f));

        String text = "GOPİ";
        int x = getXPosForCenteredText(text);
        int y = gp.TILE_SIZE *2;

        g2.setColor(Color.darkGray);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //GopiImage
        x = gp.SCREEN_WIDTH /2-gp.TILE_SIZE;
        y += gp.TILE_SIZE *2;
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
        g2.fillRect(x,y,gp.TILE_SIZE *2,gp.TILE_SIZE *2);
        g2.drawImage(image,x,y,gp.TILE_SIZE *2,gp.TILE_SIZE *2,null);

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
        y+=gp.TILE_SIZE *7/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==0)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Devam Et";
        x=getXPosForCenteredText(text);
        y+=gp.TILE_SIZE *3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Çıkış";
        x=getXPosForCenteredText(text);
        y+=gp.TILE_SIZE *3/2;
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
        int x = gp.TILE_SIZE/2;
        int y = gp.TILE_SIZE/2;
        int i = 0;

        if(gp.barMode){
            x+=gp.SCALE*9;
            while (i<gp.player.maxHp/2){
                g2.drawImage(heart_bar_empty,x,y,null);
                i++;
                x+=gp.TILE_SIZE/3;
            }

            x = gp.TILE_SIZE/2 + gp.SCALE*9;
            i = 0;

            while(i<gp.player.hp){
                g2.drawImage(heart_bar_half,x,y,null);
                i++;
                if(i<gp.player.hp)
                    g2.drawImage(heart_bar_full,x,y,null);
                i++;
                x+=gp.TILE_SIZE/3;
            }

            x = gp.TILE_SIZE/2;
            y = gp.TILE_SIZE/2;

            g2.drawImage(heart_bar_icon,x,y,null);
        }else{
            while (i<gp.player.maxHp/2){
                g2.drawImage(heart_empty,x,y,null);
                i++;
                x+=gp.TILE_SIZE*5/6;
            }

            x = gp.TILE_SIZE /2;
            i = 0;

            while(i<gp.player.hp){
                g2.drawImage(heart_half,x,y,null);
                i++;
                if(i<gp.player.hp)
                    g2.drawImage(heart_full,x,y,null);
                i++;
                x+=gp.TILE_SIZE*5/6;
            }
        }

        if(gp.barMode){
            x = gp.TILE_SIZE/2;
            y = gp.TILE_SIZE*3/2;
            i=0;

            x+=gp.SCALE*9;
            while (i<gp.player.maxEnergy){
                g2.drawImage(energy_bar_empty,x,y,null);
                i++;
                x+=gp.TILE_SIZE/3;
            }

            x = gp.TILE_SIZE/2 + gp.SCALE*9;
            i = 0;

            while(i<gp.player.energy){
                g2.drawImage(energy_bar_full,x,y,null);
                i++;
                x+=gp.TILE_SIZE/3;
            }

            x = gp.TILE_SIZE/2;

            g2.drawImage(energy_bar_icon,x,y,null);
        }else{
            x = gp.TILE_SIZE/2-5;
            y = gp.TILE_SIZE*3/2;
            i=0;

            while (i<gp.player.maxEnergy){
                g2.drawImage(energy_empty,x,y,null);
                i++;
                x+=30;
            }

            x = gp.TILE_SIZE/2-5;
            i = 0;

            while(i<gp.player.energy){
                g2.drawImage(energy_full,x,y,null);
                i++;
                x+=30;
            }
        }
    }

    public void drawMonsterHp(boolean aboveLighting){
        int biggestBossType=0;
        Entity biggestBoss=null;

        for(Entity monster:gp.monsters[gp.currentMapNum]){
            //BOSS
            if(monster!=null && monster.boss){
                if(biggestBossType<monster.bossType){
                    biggestBossType=monster.bossType;
                    biggestBoss=monster;
                }
            }

            //NORMAL MONSTER
            if(!aboveLighting && monster!=null && monster.inScreen() && monster.hpBarOn && !monster.boss){
                double oneScale = (double) gp.TILE_SIZE / monster.maxHp;
                double hpBarWidth = oneScale*monster.hp;

                g2.setColor(new Color(135,35,35));
                g2.fillRect(monster.getScreenX()-1,monster.getScreenY()-16,gp.TILE_SIZE +2,12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(monster.getScreenX(),monster.getScreenY()-15,(int)hpBarWidth,10);

                monster.hpBarCounter++;
                if(monster.hpBarCounter>=600){
                    monster.hpBarCounter=0;
                    monster.hpBarOn=false;
                }
            }
        }

        g2.setFont(fixedsys.deriveFont(Font.BOLD,24f));

        if(biggestBoss!=null && gp.bossBattleOn){
            switch(biggestBoss.bossType){
                case Entity.gen3 -> {
                    int x = gp.SCREEN_WIDTH /2 - gp.TILE_SIZE *5;
                    int y = gp.TILE_SIZE *10;

                    double oneScale = (double)gp.TILE_SIZE *2/biggestBoss.maxHp;
                    double hpBarWidth = oneScale*biggestBoss.hp;

                    g2.setColor(new Color(54,184,184));
                    g2.fillRect(x-1,y-1,gp.TILE_SIZE *6+1,22);

                    g2.setColor(new Color(16,255,255));
                    g2.fillRect(x,y,gp.TILE_SIZE *6,20);

                    g2.setColor(Color.white);
                    g2.drawString(biggestBoss.connectedEntity.connectedEntity.name,x+4,y-10);

                    //Obob
                    x += gp.TILE_SIZE *6;
                    g2.setColor(new Color(50,81,191));
                    g2.fillRect(x,y-1,gp.TILE_SIZE *2,22);

                    g2.setColor(new Color(103,123,193));
                    g2.fillRect(x,y,gp.TILE_SIZE *2,20);

                    g2.setColor(Color.white);
                    g2.drawString(biggestBoss.connectedEntity.name,x+4,y-10);

                    //Idub
                    x += gp.TILE_SIZE *2;
                    g2.setColor(new Color(105,90,146));
                    g2.fillRect(x,y-1,gp.TILE_SIZE *2+1,22);

                    g2.setColor(new Color(149,129,210));
                    g2.fillRect(x,y,(int)hpBarWidth,20);

                    g2.setColor(Color.white);
                    g2.drawString(biggestBoss.name,x+4,y-10);
                }

                case Entity.gen2 -> {
                    int x = gp.SCREEN_WIDTH /2 - gp.TILE_SIZE *5;
                    int y = gp.TILE_SIZE *10;

                    double oneScale = (double)gp.TILE_SIZE *2/biggestBoss.maxHp;
                    double hpBarWidth = oneScale*biggestBoss.hp;

                    g2.setColor(new Color(54,184,184));
                    g2.fillRect(x-1,y-1,gp.TILE_SIZE *6+1,22);

                    g2.setColor(new Color(16,255,255));
                    g2.fillRect(x,y,gp.TILE_SIZE *6,20);

                    g2.setColor(Color.white);
                    g2.drawString(biggestBoss.connectedEntity.name,x+4,y-10);

                    //Obob
                    x += gp.TILE_SIZE *6;
                    g2.setColor(new Color(50,81,191));
                    g2.fillRect(x,y-1,gp.TILE_SIZE *2,22);

                    g2.setColor(new Color(103,123,193));
                    g2.fillRect(x,y,(int)hpBarWidth,20);

                    g2.setColor(Color.white);
                    g2.drawString(biggestBoss.name,x+4,y-10);

                    //Idub
                    x += gp.TILE_SIZE *2;
                    g2.setColor(new Color(105,90,146));
                    g2.fillRect(x,y-1,gp.TILE_SIZE *2+1,22);
                }

                case Entity.gen1 -> {
                    int x = gp.SCREEN_WIDTH /2 - gp.TILE_SIZE *5;
                    int y = gp.TILE_SIZE *10;

                    double oneScale = (double)gp.TILE_SIZE *6/biggestBoss.maxHp;
                    double hpBarWidth = oneScale*biggestBoss.hp;

                    g2.setColor(new Color(54,184,184));
                    g2.fillRect(x-1,y-1,gp.TILE_SIZE *6+1,22);

                    g2.setColor(new Color(16,255,255));
                    g2.fillRect(x,y,(int)hpBarWidth,20);

                    g2.setColor(Color.white);
                    g2.drawString(biggestBoss.name,x+4,y-10);

                    //Obob
                    x += gp.TILE_SIZE *6;
                    g2.setColor(new Color(50,81,191));
                    g2.fillRect(x,y-1,gp.TILE_SIZE *2,22);

                    //Idub
                    x += gp.TILE_SIZE *2;
                    g2.setColor(new Color(105,90,146));
                    g2.fillRect(x,y-1,gp.TILE_SIZE *2+1,22);
                }
            }
        }
    }

    public void drawMessages(){
        int messageX = gp.TILE_SIZE /2;
        int messageY = gp.SCREEN_HEIGHT -gp.TILE_SIZE /2;
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
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        g2.setFont(fixedsys.deriveFont(Font.BOLD,96f));

        String text = "Oyun Durdu";
        int x = getXPosForCenteredText(text);
        int y = gp.TILE_SIZE *2;

        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+4,y+4);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        g2.setFont(fixedsys.deriveFont(Font.PLAIN,48f));

        text = "Oyuna Dön";
        x = getXPosForCenteredText(text);
        y += gp.TILE_SIZE *7/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==0){
            if(gp.keyHandler.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.PLAY_STATE;
                subState=0;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Ayarlar";
        x = getXPosForCenteredText(text);
        y += gp.TILE_SIZE *3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1){
            if(gp.keyHandler.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.SETTINGS_STATE;
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
        y+=gp.TILE_SIZE *3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==2) {
            if(gp.keyHandler.xKeyPressed){
                gp.saveLoad.save();
                huso.startDialogue(huso,0);
            }
            g2.setColor(Color.yellow);
        }else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        text = "Çıkış";
        x = getXPosForCenteredText(text);
        y += gp.TILE_SIZE *3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==3){
            if(gp.keyHandler.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.SETTINGS_STATE;
                subState=3;
                commandNum=0;
            }
            g2.setColor(Color.yellow);
        }
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        gp.keyHandler.xKeyPressed=false;
    }

    public void drawDialogueScreen(){
        //Window
        int x = gp.TILE_SIZE *2;
        int y = gp.TILE_SIZE /2;
        int width = gp.SCREEN_WIDTH -gp.TILE_SIZE *4;
        int height = gp.TILE_SIZE *5;

        drawSubWindow(x,y,width,height);

        //Text
        x+=gp.TILE_SIZE /2;
        y+=gp.TILE_SIZE;
        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(26f));

        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex]!=null){
            char[] chars = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if(charIndex<chars.length){
                String str = String.valueOf(chars[charIndex]);
                combinedText+=str;
                gp.playSoundEffect(20);

                currentDialogueText=combinedText;

                charIndex++;
            }

            if(gp.keyHandler.xKeyPressed){
                charIndex=0;
                combinedText="";
                if(gp.gameState==gp.DIALOGUE_STATE || gp.gameState==gp.CUTSCENE_STATE){
                    npc.dialogueIndex++;
                    gp.keyHandler.xKeyPressed=false;
                }
            }
        }else{
            npc.dialogueIndex=0;

            if(gp.gameState==gp.DIALOGUE_STATE){
                gp.gameState=gp.PLAY_STATE;
            }
            if(gp.gameState==gp.CUTSCENE_STATE){
                gp.cutsceneHandler.scenePhase++;
            }
        }

        for(String line:currentDialogueText.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }
    }

    public void drawCharInfoScreen(){
        //Create a window
        final int windowX=gp.TILE_SIZE /2;
        final int windowY=gp.TILE_SIZE;
        final int windowWidth=gp.TILE_SIZE *5;
        final int windowHeight=gp.TILE_SIZE *9;
        drawSubWindow(windowX,windowY,windowWidth,windowHeight);

        //Text
        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(20f));

        int textX = windowX+20;
        int textY = windowY+gp.TILE_SIZE;
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
        textY = windowY+gp.TILE_SIZE;
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

        g2.drawImage(gp.player.currentFireball.down1,endOfRect-gp.TILE_SIZE,textY,null);
        textY+=lineHeight*2+gp.TILE_SIZE;
        g2.drawImage(gp.player.currentArmor.down1,endOfRect-gp.TILE_SIZE,textY,null);
    }

    public void drawSettingsScreen(){
        g2.setColor(lessOpaqueBlack);
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        g2.setColor(Color.white);
        g2.setFont(fixedsys.deriveFont(Font.PLAIN,32));

        //Sub Window
        int frameX = gp.TILE_SIZE *6, frameY = gp.TILE_SIZE;
        int frameWidth = gp.TILE_SIZE *8, frameHeight = gp.TILE_SIZE *10;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(subState){
            case 0 -> settingsNormal(frameX,frameY);
            case 1 -> settingsFullScreenNotification(frameY);
            case 2 -> settingsControls(frameY);
            case 3 -> settingsQuitConfirmation(frameY);
        }

        gp.keyHandler.xKeyPressed=false;
    }

    public void drawDeadScreen(){
        g2.setColor(lessOpaqueBlack);
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        g2.setFont(fixedsys.deriveFont(Font.BOLD,96f));

        String text = "Oyun Bitti";
        int x = getXPosForCenteredText(text);
        int y = gp.TILE_SIZE *2;

        g2.setColor(new Color(150,0,0));
        g2.drawString(text,x+4,y+4);

        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        g2.setFont(fixedsys.deriveFont(Font.PLAIN,48f));

        text = "Yeniden Doğ";
        x = getXPosForCenteredText(text);
        y += gp.TILE_SIZE *9/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==0){
            if(gp.keyHandler.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.PLAY_STATE;
                gp.resetGame(false);
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
        y += gp.TILE_SIZE *3/2;
        g2.setColor(new Color(75,0,103));
        g2.drawString(text,x+3,y+3);
        if(commandNum==1){
            if(gp.keyHandler.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.TITLE_STATE;
                subState=0;
                commandNum=0;
                gp.resetGame(false);
                gp.saveLoad.save();
            }
            g2.setColor(Color.yellow);
        }
        else
            g2.setColor(Color.white);
        g2.drawString(text,x,y);

        gp.keyHandler.xKeyPressed=false;
    }

    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        boolean isTheSameArea = gp.tileManager.maps[gp.currentMapNum].musicNum==gp.tileManager.maps[gp.eventHandler.tempMap].musicNum;

        if(counter==50){
            counter=0;
            gp.gameState = gp.PLAY_STATE;
            gp.currentMapNum = gp.eventHandler.tempMap;
            gp.currentArea = gp.eventHandler.gp.tileManager.maps[gp.currentMapNum].area;
            gp.player.worldX = gp.eventHandler.tempCol*gp.TILE_SIZE;
            gp.player.worldY = gp.eventHandler.tempRow*gp.TILE_SIZE;
            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;
            gp.changeArea(isTheSameArea);
        }
    }

    public void drawTradeScreen(){
        switch(subState){
            case 0 -> tradeSelect();
            case 1 -> tradeBuy();
            case 2 -> tradeSell();
        }

        gp.keyHandler.xKeyPressed=false;
    }

    public void drawSleepScreen(){
        counter++;

        if(counter<120){
            if(gp.environmentHandler.lighting.dayState!=gp.environmentHandler.lighting.dawn)
                gp.environmentHandler.lighting.filterAlpha += 0.01f;
            if(gp.environmentHandler.lighting.filterAlpha>1f || gp.environmentHandler.lighting.dayState==gp.environmentHandler.lighting.dawn){
                gp.environmentHandler.lighting.filterAlpha=1f;
                counter=120;
            }
        }else {
            gp.environmentHandler.lighting.filterAlpha -= 0.01f;
            if(gp.environmentHandler.lighting.filterAlpha<=0f){
                gp.environmentHandler.lighting.filterAlpha=0f;
                counter=0;
                gp.environmentHandler.lighting.dayState = gp.environmentHandler.lighting.day;
                gp.environmentHandler.lighting.dayCounter=0;
                gp.environmentHandler.lighting.dayNumber++;
                gp.gameState=gp.PLAY_STATE;
                gp.player.getImages();
            }
        }
    }

    public void tradeSelect(){
        npc.dialogueSet=0;

        //Shorter Dialogue Screen
            //Window
            int x = gp.TILE_SIZE *2;
            int y = gp.TILE_SIZE /2;
            int width = gp.SCREEN_WIDTH -gp.TILE_SIZE *7;
            int height = gp.TILE_SIZE *5;

            drawSubWindow(x,y,width,height);

            //Text
            x+=gp.TILE_SIZE /2;
            y+=gp.TILE_SIZE;
            g2.setColor(Color.white);
            g2.setFont(fixedsys.deriveFont(26f));

            for(String line:npc.dialogues[0][0].split("\n")){
                g2.drawString(line,x,y);
                y+=40;
            }
        //Shorter Dialogue Screen

        x = gp.SCREEN_WIDTH -gp.TILE_SIZE *5;
        y = gp.TILE_SIZE /2;
        width = gp.TILE_SIZE *3;
        height = gp.TILE_SIZE *5;
        drawSubWindow(x,y,width,height);

        //Options
        String text = "Satın Al";
        x=getXPosForCenteredText(text)+gp.TILE_SIZE *13/2;
        y+=gp.TILE_SIZE;

        if(commandNum==0) {
            g2.setColor(Color.yellow);
            if(gp.keyHandler.xKeyPressed){
                subState=1;
            }
        }else
            g2.setColor(Color.white);

        g2.drawString(text,x,y);

        text = "Sat";
        x=getXPosForCenteredText(text)+gp.TILE_SIZE *13/2;
        y+=gp.TILE_SIZE *3/2;

        if(commandNum==1){
            g2.setColor(Color.yellow);
            if(gp.keyHandler.xKeyPressed){
                subState=2;
            }
        }else
            g2.setColor(Color.white);

        g2.drawString(text,x,y);

        text = "Ayrıl";
        x=getXPosForCenteredText(text)+gp.TILE_SIZE *13/2;
        y+=gp.TILE_SIZE *3/2;

        if(commandNum==2){
            g2.setColor(Color.yellow);
            if(gp.keyHandler.xKeyPressed){
                commandNum=0;
                npc.startDialogue(npc,1);
            }
        }else
            g2.setColor(Color.white);

        g2.drawString(text,x,y);
    }

    public void tradeBuy(){
        drawInventory(gp.player,false);
        drawInventory(npc,true);

        //Hint Box
        int x,y,width,height;

        x = gp.TILE_SIZE /2;
        width = gp.TILE_SIZE *6;
        height = gp.TILE_SIZE *2;

        if(getItemIndex(npc)<npc.inventory.size()){
            y = gp.TILE_SIZE *19/2;
        }else{
            y = gp.TILE_SIZE *11/2;
        }
        drawSubWindow(x,y,width,height);
        g2.drawString("[Z] Çıkış, [X] Onayla",x+24,y+54);

        //Player Coin
        x = gp.SCREEN_WIDTH -gp.TILE_SIZE *13/2;
        y = gp.TILE_SIZE *11/2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Şu anki paran:"+gp.player.coin,x+64,y+54);

        //Price
        int itemIndex = getItemIndex(npc);
        if(itemIndex<npc.inventory.size()){
            x=gp.TILE_SIZE *4;
            y=gp.TILE_SIZE *5;
            width=gp.TILE_SIZE *5/2;
            height=gp.TILE_SIZE;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coinImg,x+10,y+8,32,32,null);
            int price = npc.inventory.get(itemIndex).coin;
            g2.drawString(":"+price,x+38,y+30);

            //Buy
            if(gp.keyHandler.xKeyPressed){
                if(price>gp.player.coin){
                    commandNum=0;
                    subState=0;
                    npcSlotCol=0;
                    npcSlotRow=0;
                    npc.startDialogue(npc,2);
                }else{
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex))){
                        gp.player.coin-=price;
                        if(!npc.inventory.get(itemIndex).stackable){
                            npc.inventory.remove(itemIndex);
                        }
                    }else{
                        commandNum=0;
                        subState=0;
                        npcSlotCol=0;
                        npcSlotRow=0;
                        npc.startDialogue(npc,3);
                    }
                }
            }
        }
    }

    public void tradeSell(){
        drawInventory(gp.player,true);
        drawInventory(npc,false);

        //Hint Box
        int x,y,width,height;

        x = gp.TILE_SIZE /2;
        y = gp.TILE_SIZE *11/2;
        width = gp.TILE_SIZE *6;
        height = gp.TILE_SIZE *2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[Z] Çıkış, [X] Onayla",x+24,y+54);

        //Player Coin
        x = gp.SCREEN_WIDTH -gp.TILE_SIZE *13/2;
        if(getItemIndex(gp.player)<gp.player.inventory.size())
            y = gp.TILE_SIZE *19/2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Şu anki paran:"+gp.player.coin,x+64,y+54);

        //Value
        int itemIndex = getItemIndex(gp.player);
        if(itemIndex<gp.player.inventory.size()){
            y=gp.TILE_SIZE *5;
            width=gp.TILE_SIZE *5/2;
            height=gp.TILE_SIZE;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coinImg,x+10,y+8,32,32,null);
            int price = gp.player.inventory.get(itemIndex).coin/2;
            g2.drawString(":"+price,x+38,y+30);

            //Sell
            if(gp.keyHandler.xKeyPressed){
                boolean npcHasItem=false;
                for(Entity entity:npc.inventory){
                    if(entity.name.equals(gp.player.inventory.get(itemIndex).name)){
                        npcHasItem = true;
                        break;
                    }
                }

                if(gp.player.inventory.get(itemIndex)==gp.player.currentFireball || gp.player.inventory.get(itemIndex)==gp.player.currentArmor || gp.player.inventory.get(itemIndex)==gp.player.currentLightSource){
                    commandNum=0;
                    subState=0;
                    playerSlotCol=0;
                    playerSlotRow=0;
                    npc.startDialogue(npc,4);
                } else if(npc.inventory.size()==npc.maxInvSize){
                    commandNum=0;
                    subState=0;
                    playerSlotCol=0;
                    playerSlotRow=0;
                    npc.startDialogue(npc,5);
                } else{
                    if(!gp.player.inventory.get(itemIndex).stackable && !npcHasItem)
                        npc.inventory.add(gp.player.inventory.get(itemIndex));
                    if(gp.player.inventory.get(itemIndex).amount>1)
                        gp.player.inventory.get(itemIndex).amount--;
                    else
                        gp.player.inventory.remove(itemIndex);
                    gp.player.coin+=price;
                }
            }
        }
    }

    public void settingsNormal(int frameX, int frameY){
        int textX;
        int textY;

        //Title
        String text = "Ayarlar";
        textX=getXPosForCenteredText(text);
        textY=frameY+gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        //Full Screen
        textX=frameX+gp.TILE_SIZE /2;
        textY+=gp.TILE_SIZE *2;

        if(commandNum==0) {
            if (gp.keyHandler.xKeyPressed){
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
        textY+=gp.TILE_SIZE;

        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawString("Müzik",textX,textY);

        //SFX
        textY+=gp.TILE_SIZE;

        if(commandNum==2)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawString("Ses E.",textX,textY);

        //HP and Energy Bar
        textY+=gp.TILE_SIZE;

        if(commandNum==3)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawString("Can ve Enrj",textX,textY);

        //Controls
        textY+=gp.TILE_SIZE;

        if(commandNum==4) {
            if (gp.keyHandler.xKeyPressed) {
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
        textY+=gp.TILE_SIZE *7/3;

        if(commandNum==5){
            if(gp.keyHandler.xKeyPressed){
                gp.playSoundEffect(4);
                gp.gameState=gp.PAUSE_STATE;
                commandNum=1;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString(text,textX,textY);


        //-----------------------------------------------------------------------------------------------------------------------


        //Full Screen CheckBox
        g2.setColor(Color.white);
        textX=frameX+gp.TILE_SIZE *9/2;
        textY=frameY+gp.TILE_SIZE *2+26;
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
        textY+=gp.TILE_SIZE;

        if(commandNum==1)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawRect(textX,textY,120,24);

        int volumeWidth = 20*gp.music.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        //SFX Volume
        textY+=gp.TILE_SIZE;

        if(commandNum==2)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        g2.drawRect(textX,textY,120,24);

        volumeWidth = 20*gp.soundEffect.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        //HP and Energy Type
        textY=frameY+gp.TILE_SIZE *6;

        if(commandNum==3)
            g2.setColor(Color.yellow);
        else
            g2.setColor(Color.white);

        String type;
        if(gp.barMode)
            type = "Bar";
        else
            type = "Normal";

        g2.drawString("<"+type+">",textX,textY);

        gp.config.saveConfig();
    }

    public void settingsFullScreenNotification(int frameY){
        int textX;
        int textY = frameY + gp.TILE_SIZE *3;

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
        if(gp.keyHandler.xKeyPressed){
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
        textY=frameY+gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        //Texts
        text = "Yürüme - Oklar";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE *5/4;

        g2.drawString(text,textX,textY);

        text = "Ateş Topu - X";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        text = "Fırlat - Z";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        text = "Envanter - C";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        text = "Harita - M";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        text = "Savunma - S";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        text = "Durdur - ESC";
        textX = getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE;

        g2.drawString(text,textX,textY);

        //Back
        text="Geri Dön";
        textX=getXPosForCenteredText(text);
        textY+=gp.TILE_SIZE *13/12;

        if(gp.keyHandler.xKeyPressed){
            gp.playSoundEffect(4);
            subState=0;
            commandNum=3;
        }
        g2.setColor(Color.yellow);

        g2.drawString(text,textX,textY);
    }

    public void settingsQuitConfirmation(int frameY){
        int textX;
        int textY = frameY + gp.TILE_SIZE *2;

        currentDialogueText="Oyundan çıkıp\nana menüye dönmek\nistediğinize\nemin misiniz?";
        for(String line:currentDialogueText.split("\n")){
            textX=getXPosForCenteredText(line);
            g2.drawString(line,textX,textY);
            textY+=40;
        }

        //Yes
        String text = "Evet";
        textX = getXPosForCenteredText(text);
        textY += gp.TILE_SIZE;
        if(commandNum==0){
            if(gp.keyHandler.xKeyPressed){
                subState=0;
                gp.gameState=gp.TITLE_STATE;
                gp.stopMusic();
                gp.saveLoad.save();
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString(text,textX,textY);

        //No
        text = "Hayır";
        textX = getXPosForCenteredText(text);
        textY += gp.TILE_SIZE;
        if(commandNum==1){
            if(gp.keyHandler.xKeyPressed){
                gp.gameState=gp.PAUSE_STATE;
                subState=0;
                commandNum=3;
            }
            g2.setColor(Color.yellow);
        } else
            g2.setColor(Color.white);

        g2.drawString(text,textX,textY);
    }

    public void drawInventory(Entity entity,boolean cursorIsVisible) {

        boolean isPlayer = entity==gp.player;

        int windowX,windowY,windowWidth,windowHeight;
        int slotCol,slotRow;

        if(isPlayer){
            windowX = gp.SCREEN_WIDTH -gp.TILE_SIZE *13/2;
            windowY = gp.TILE_SIZE /2;
            windowWidth = gp.TILE_SIZE *6;
            windowHeight = gp.TILE_SIZE *5;
            slotCol=playerSlotCol;
            slotRow=playerSlotRow;
        }else{
            windowX = gp.TILE_SIZE /2;
            windowY = gp.TILE_SIZE /2;
            windowWidth = gp.TILE_SIZE *6;
            windowHeight = gp.TILE_SIZE *5;
            slotCol=npcSlotCol;
            slotRow=npcSlotRow;
        }

        drawSubWindow(windowX,windowY,windowWidth,windowHeight);

        //Slot
        final int slotXStart = windowX + 20;
        final int slotYStart = windowY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.TILE_SIZE +2;

        //Draw Inventory
        for (int i=0; i<entity.inventory.size();i++){
            if(entity.inventory.get(i)==entity.currentFireball || entity.inventory.get(i)==entity.currentArmor || entity.inventory.get(i)==entity.currentLightSource){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,gp.TILE_SIZE,gp.TILE_SIZE,10,10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            if(entity==gp.player && entity.inventory.get(i).amount>1){
                g2.setFont(fixedsys.deriveFont(32f));
                int amountX;
                int amountY;

                String s = String.valueOf(entity.inventory.get(i).amount);
                amountX = getXForRightAlignedText(s,slotX+gp.TILE_SIZE);
                amountY = slotY+gp.TILE_SIZE;

                g2.setColor(new Color(60,60,60));
                g2.drawString(s,amountX,amountY);

                g2.setColor(Color.white);
                g2.drawString(s,amountX-2,amountY-2);
            }

            slotX+=slotSize;
            if(i==4 || i==9 || i==14){
                slotX=slotXStart;
                slotY+=slotSize;
            }
        }

        if(cursorIsVisible){

            //Cursor
            int cursorX = slotXStart + slotCol*slotSize;
            int cursorY = slotYStart + slotRow*slotSize;
            int cursorWidth = gp.TILE_SIZE;
            int cursorHeight = gp.TILE_SIZE;
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));

            //Draw Cursor
            g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);

            //DescriptionWindow
            int dWindowX = windowX;
            int dWindowY = windowY+windowHeight;
            int dWindowWidth = windowWidth;
            int dWindowHeight = gp.TILE_SIZE *4;

            //Description
            int textX = dWindowX + 20;
            int textY = dWindowY + 37;
            g2.setFont(fixedsys.deriveFont(20f));

            int itemIndex = getItemIndex(entity);

            if(itemIndex<entity.inventory.size()){
                drawSubWindow(dWindowX,dWindowY,dWindowWidth,dWindowHeight);

                g2.setColor(Color.yellow);
                if(entity.inventory.get(itemIndex)!=null){
                    g2.drawString("["+entity.inventory.get(itemIndex).displayedName+"]",textX,textY);
                    textY+=24;
                    g2.setColor(Color.white);
                    for(String line:entity.inventory.get(itemIndex).description.split("\n")){
                        g2.drawString(line,textX,textY);
                        textY+=24;
                    }
                }else{
                    textY+=24;
                    g2.setColor(Color.white);
                    for(String line:entity.inventory.get(itemIndex-1).description.split("\n")){
                        g2.drawString(line,textX,textY);
                        textY+=24;
                    }
                    g2.drawString("["+entity.inventory.get(itemIndex-1).displayedName+"]",textX,textY);
                }
            }
        }
    }

    public int getItemIndex(Entity entity){
        if(entity==gp.player)
            return playerSlotCol + playerSlotRow*5;
        else
            return npcSlotCol + npcSlotRow*5;
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

        return gp.SCREEN_WIDTH /2-textLength/2;
    }

    public int getXForRightAlignedText(String text,int endOfRect){
        return endOfRect-(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
    }

}
