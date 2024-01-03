package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean keyPressed;
    public boolean upPressed,downPressed,leftPressed,rightPressed,xKeyPressed,zKeyPressed,sKeyPressed;

    //Debug
    boolean debugMode=false;

    public KeyHandler(GamePanel gp){
        this.gp =gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //TitleState
        if(gp.gameState==gp.TITLE_STATE){
            titleState(keyCode);
        }

        //PlayState
        else if(gp.gameState==gp.PLAY_STATE){
            playState(keyCode);
        }

        //PauseState
        else if (gp.gameState==gp.PAUSE_STATE){
            pauseState(keyCode);
        }

        //DialogueState
        else if (gp.gameState==gp.DIALOGUE_STATE || gp.gameState==gp.CUTSCENE_STATE){
            dialogueState(keyCode);
        }

        //CharInfoState
        else if (gp.gameState==gp.CHAR_INFO_STATE){
            charInfoState(keyCode);
        }

        //SettingsState
        else if (gp.gameState==gp.SETTINGS_STATE){
            settingsState(keyCode);
        }

        //DeadState
        else if (gp.gameState==gp.DEAD_STATE){
            deadState(keyCode);
        }

        //TradeState
        else if (gp.gameState==gp.TRADE_STATE){
            tradeState(keyCode);
        }
    }

    void titleState(int keyCode){
        if(keyCode==KeyEvent.VK_UP){
            gp.uiHandler.commandNum--;
            if(gp.uiHandler.commandNum<0)
                gp.uiHandler.commandNum=2;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            gp.uiHandler.commandNum++;
            if(gp.uiHandler.commandNum>2)
                gp.uiHandler.commandNum=0;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_X){
            gp.playSoundEffect(4);
            if(gp.uiHandler.commandNum==0){
                gp.resetGame(true);
                gp.gameState=gp.PLAY_STATE;
                gp.playMusic(0);
            }else if(gp.uiHandler.commandNum==1){
                gp.saveLoad.load();
                gp.gameState=gp.PLAY_STATE;
                gp.playMusic(0);
            }else if(gp.uiHandler.commandNum==2){
                System.exit(0);
            }
            gp.uiHandler.commandNum=0;
        }
    }

    void playState(int keyCode){
        //Movement
        if(keyCode==KeyEvent.VK_UP){
            upPressed = true;
        }
        if(keyCode==KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(keyCode==KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed = true;
        }
        if(keyCode==KeyEvent.VK_Z){
            zKeyPressed = true;
        }
        if(keyCode==KeyEvent.VK_S){
            sKeyPressed = true;
        }

        //Other
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.PAUSE_STATE;
            gp.uiHandler.commandNum=0;
        }
        if(keyCode==KeyEvent.VK_C){
            gp.gameState = gp.CHAR_INFO_STATE;
            gp.uiHandler.playerSlotRow =0;
            gp.uiHandler.playerSlotCol =0;
        }
        if(keyCode==KeyEvent.VK_M){
            gp.miniMap.miniMapOn=!gp.miniMap.miniMapOn;
        }

        //Debug
        if(keyCode==KeyEvent.VK_D){
            debugMode = !debugMode;
        }
    }

    void pauseState(int keyCode){
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.PLAY_STATE;
            gp.music.play();
            gp.music.loop();
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        if(keyCode==KeyEvent.VK_UP){
            if(gp.uiHandler.commandNum==0)
                gp.uiHandler.commandNum=3;
            else
                gp.uiHandler.commandNum--;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            if(gp.uiHandler.commandNum==3)
                gp.uiHandler.commandNum=0;
            else
                gp.uiHandler.commandNum++;
            gp.playSoundEffect(3);
        }
    }

    void dialogueState(int keyCode){
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }
    }

    void charInfoState(int keyCode){
        playerInvControls(keyCode);

        if (keyCode==KeyEvent.VK_X){
            gp.player.useItem();
        }
        if (keyCode==KeyEvent.VK_C){
            gp.gameState = gp.PLAY_STATE;
        }
        if (keyCode==KeyEvent.VK_Z){
            gp.gameState = gp.PLAY_STATE;
        }
    }

    void settingsState(int keyCode){
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.PLAY_STATE;
            gp.music.play();
            gp.music.loop();
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        int maxCommandNum = 0;
        switch(gp.uiHandler.subState){
            case 0 -> maxCommandNum=4;
            case 3 -> maxCommandNum=1;
        }

        if(keyCode==KeyEvent.VK_UP){
            gp.uiHandler.commandNum--;
            if(gp.uiHandler.commandNum<0)
                gp.uiHandler.commandNum=maxCommandNum;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            gp.uiHandler.commandNum++;
            if(gp.uiHandler.commandNum>maxCommandNum)
                gp.uiHandler.commandNum=0;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_LEFT){
            if(gp.uiHandler.subState==0)
                if(gp.uiHandler.commandNum==1 && gp.music.volumeScale>0){
                    gp.playSoundEffect(4);
                    gp.music.volumeScale--;
                    gp.music.changeVolume();
                }else if(gp.uiHandler.commandNum==2 && gp.soundEffect.volumeScale>0){
                    gp.playSoundEffect(4);
                    gp.soundEffect.volumeScale--;
                }
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if(gp.uiHandler.subState==0)
                if(gp.uiHandler.commandNum==1 && gp.music.volumeScale<6){
                    gp.playSoundEffect(4);
                    gp.music.volumeScale++;
                    gp.music.changeVolume();
                }else if(gp.uiHandler.commandNum==2 && gp.soundEffect.volumeScale<6){
                    gp.playSoundEffect(4);
                    gp.soundEffect.volumeScale++;
                }
        }
    }

    void deadState(int keyCode){
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        if(keyCode==KeyEvent.VK_UP){
            gp.uiHandler.commandNum--;
            if(gp.uiHandler.commandNum<0)
                gp.uiHandler.commandNum=1;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            gp.uiHandler.commandNum++;
            if(gp.uiHandler.commandNum>1)
                gp.uiHandler.commandNum=0;
            gp.playSoundEffect(3);
        }
    }

    void tradeState(int keyCode){
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.PLAY_STATE;
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        if(gp.uiHandler.subState==0){
            if(keyCode==KeyEvent.VK_UP){
                gp.uiHandler.commandNum--;
                if(gp.uiHandler.commandNum<0)
                    gp.uiHandler.commandNum=2;
                gp.playSoundEffect(3);
            }
            if(keyCode==KeyEvent.VK_DOWN){
                gp.uiHandler.commandNum++;
                if(gp.uiHandler.commandNum>2)
                    gp.uiHandler.commandNum=0;
                gp.playSoundEffect(3);
            }
            if(keyCode==KeyEvent.VK_Z){
                gp.gameState = gp.PLAY_STATE;
            }
        } else if(gp.uiHandler.subState==1){
            tradeNpcInvControls(keyCode);
            if(keyCode==KeyEvent.VK_Z){
                gp.uiHandler.subState=0;
                gp.uiHandler.commandNum=0;
                gp.uiHandler.npcSlotCol=0;
                gp.uiHandler.npcSlotRow=0;
            }
        } else if(gp.uiHandler.subState==2){
            playerInvControls(keyCode);
            if(keyCode==KeyEvent.VK_Z){
                gp.uiHandler.subState=0;
                gp.uiHandler.commandNum=1;
                gp.uiHandler.playerSlotCol=0;
                gp.uiHandler.playerSlotRow=0;
            }
        }

    }

    public void playerInvControls(int keyCode){
        if(keyCode==KeyEvent.VK_UP){
            if(gp.uiHandler.playerSlotRow !=0){
                gp.uiHandler.playerSlotRow--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_DOWN){
            if(gp.uiHandler.playerSlotRow !=3){
                gp.uiHandler.playerSlotRow++;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_LEFT){
            if(gp.uiHandler.playerSlotCol ==0 && gp.uiHandler.playerSlotRow >0){
                gp.uiHandler.playerSlotRow--;
                gp.uiHandler.playerSlotCol =4;
                gp.playSoundEffect(3);
            }else if(!(gp.uiHandler.playerSlotCol ==0 && gp.uiHandler.playerSlotRow ==0)){
                gp.uiHandler.playerSlotCol--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if(gp.uiHandler.playerSlotCol ==4 && gp.uiHandler.playerSlotRow <3){
                gp.uiHandler.playerSlotRow++;
                gp.uiHandler.playerSlotCol =0;
                gp.playSoundEffect(3);
            }else if(!(gp.uiHandler.playerSlotCol ==4 && gp.uiHandler.playerSlotRow ==3)){
                gp.uiHandler.playerSlotCol++;
                gp.playSoundEffect(3);
            }
        }
    }

    public void tradeNpcInvControls(int keyCode){
        if(keyCode==KeyEvent.VK_UP){
            if(gp.uiHandler.npcSlotRow !=0){
                gp.uiHandler.npcSlotRow--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_DOWN){
            if(gp.uiHandler.npcSlotRow !=3){
                gp.uiHandler.npcSlotRow++;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_LEFT){
            if(gp.uiHandler.npcSlotCol ==0 && gp.uiHandler.npcSlotRow >0){
                gp.uiHandler.npcSlotRow--;
                gp.uiHandler.npcSlotCol =4;
                gp.playSoundEffect(3);
            }else if(!(gp.uiHandler.npcSlotCol ==0 && gp.uiHandler.npcSlotRow ==0)){
                gp.uiHandler.npcSlotCol--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if(gp.uiHandler.npcSlotCol ==4 && gp.uiHandler.npcSlotRow <3){
                gp.uiHandler.npcSlotRow++;
                gp.uiHandler.npcSlotCol =0;
                gp.playSoundEffect(3);
            }else if(!(gp.uiHandler.npcSlotCol ==4 && gp.uiHandler.npcSlotRow ==3)){
                gp.uiHandler.npcSlotCol++;
                gp.playSoundEffect(3);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //Movement
        if (keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed = false;
        }
        if(keyCode==KeyEvent.VK_Z){
            zKeyPressed = false;
        }
        if(keyCode==KeyEvent.VK_S){
            sKeyPressed = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
