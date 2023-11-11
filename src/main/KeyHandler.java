package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        if(gp.gameState==gp.titleState){
            titleState(keyCode);
        }

        //PlayState
        else if(gp.gameState==gp.playState){
            playState(keyCode);
        }

        //PauseState
        else if (gp.gameState==gp.pauseState){
            pauseState(keyCode);
        }

        //DialogueState
        else if (gp.gameState==gp.dialogueState){
            dialogueState(keyCode);
        }

        //CharInfoState
        else if (gp.gameState==gp.charInfoState){
            charInfoState(keyCode);
        }

        //SettingsState
        else if (gp.gameState==gp.settingsState){
            settingsState(keyCode);
        }

        //DeadState
        else if (gp.gameState==gp.deadState){
            deadState(keyCode);
        }

        //TradeState
        else if (gp.gameState==gp.tradeState){
            tradeState(keyCode);
        }
    }

    void titleState(int keyCode){
        if(keyCode==KeyEvent.VK_UP){
            gp.uiH.commandNum--;
            if(gp.uiH.commandNum<0)
                gp.uiH.commandNum=2;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            gp.uiH.commandNum++;
            if(gp.uiH.commandNum>2)
                gp.uiH.commandNum=0;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_X){
            gp.playSoundEffect(4);
            if(gp.uiH.commandNum==0){
                gp.resetGame(true);
                gp.gameState=gp.playState;
                gp.playMusic(0);
            }else if(gp.uiH.commandNum==1){
                gp.resetGame(false);
                gp.gameState=gp.playState;
                gp.playMusic(0);
            }else if(gp.uiH.commandNum==2){
                System.exit(0);
            }
            gp.uiH.commandNum=0;
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
            gp.gameState = gp.pauseState;
            gp.uiH.commandNum=0;
        }
        if(keyCode==KeyEvent.VK_C){
            gp.gameState = gp.charInfoState;
            gp.uiH.playerSlotRow =0;
            gp.uiH.playerSlotCol =0;
        }
        if(keyCode==KeyEvent.VK_M){
            gp.map.miniMapOn=!gp.map.miniMapOn;
        }

        //Debug
        if(keyCode==KeyEvent.VK_D){
            debugMode = !debugMode;
        }
    }

    void pauseState(int keyCode){
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
            gp.music.play();
            gp.music.loop();
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        if(keyCode==KeyEvent.VK_UP){
            if(gp.uiH.commandNum==3)
                gp.uiH.commandNum=1;
            else if(gp.uiH.commandNum==0)
                gp.uiH.commandNum=3;
            else
                gp.uiH.commandNum--;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            if(gp.uiH.commandNum==1)
                gp.uiH.commandNum=3;
            else if(gp.uiH.commandNum==3)
                gp.uiH.commandNum=0;
            else
                gp.uiH.commandNum++;
            gp.playSoundEffect(3);
        }
    }

    void dialogueState(int keyCode){
        if(keyCode==KeyEvent.VK_X){
            gp.gameState = gp.playState;
        }
    }

    void charInfoState(int keyCode){
        playerInvControls(keyCode);

        if (keyCode==KeyEvent.VK_X){
            gp.player.useItem();
        }
        if (keyCode==KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if (keyCode==KeyEvent.VK_Z){
            gp.gameState = gp.playState;
        }
    }

    void settingsState(int keyCode){
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
            gp.music.play();
            gp.music.loop();
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        int maxCommandNum = 0;
        switch(gp.uiH.subState){
            case 0 -> maxCommandNum=4;
            case 3 -> maxCommandNum=1;
        }

        if(keyCode==KeyEvent.VK_UP){
            gp.uiH.commandNum--;
            if(gp.uiH.commandNum<0)
                gp.uiH.commandNum=maxCommandNum;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            gp.uiH.commandNum++;
            if(gp.uiH.commandNum>maxCommandNum)
                gp.uiH.commandNum=0;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_LEFT){
            if(gp.uiH.subState==0)
                if(gp.uiH.commandNum==1 && gp.music.volumeScale>0){
                    gp.playSoundEffect(4);
                    gp.music.volumeScale--;
                    gp.music.changeVolume();
                }else if(gp.uiH.commandNum==2 && gp.soundEffect.volumeScale>0){
                    gp.playSoundEffect(4);
                    gp.soundEffect.volumeScale--;
                }
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if(gp.uiH.subState==0)
                if(gp.uiH.commandNum==1 && gp.music.volumeScale<6){
                    gp.playSoundEffect(4);
                    gp.music.volumeScale++;
                    gp.music.changeVolume();
                }else if(gp.uiH.commandNum==2 && gp.soundEffect.volumeScale<6){
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
            gp.uiH.commandNum--;
            if(gp.uiH.commandNum<0)
                gp.uiH.commandNum=1;
            gp.playSoundEffect(3);
        }
        if(keyCode==KeyEvent.VK_DOWN){
            gp.uiH.commandNum++;
            if(gp.uiH.commandNum>1)
                gp.uiH.commandNum=0;
            gp.playSoundEffect(3);
        }
    }

    void tradeState(int keyCode){
        if(keyCode==KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(keyCode==KeyEvent.VK_X){
            xKeyPressed=true;
        }

        if(gp.uiH.subState==0){
            if(keyCode==KeyEvent.VK_UP){
                gp.uiH.commandNum--;
                if(gp.uiH.commandNum<0)
                    gp.uiH.commandNum=2;
                gp.playSoundEffect(3);
            }
            if(keyCode==KeyEvent.VK_DOWN){
                gp.uiH.commandNum++;
                if(gp.uiH.commandNum>2)
                    gp.uiH.commandNum=0;
                gp.playSoundEffect(3);
            }
            if(keyCode==KeyEvent.VK_Z){
                gp.gameState = gp.playState;
            }
        } else if(gp.uiH.subState==1){
            tradeNpcInvControls(keyCode);
            if(keyCode==KeyEvent.VK_Z){
                gp.uiH.subState=0;
                gp.uiH.commandNum=0;
                gp.uiH.npcSlotCol=0;
                gp.uiH.npcSlotRow=0;
            }
        } else if(gp.uiH.subState==2){
            playerInvControls(keyCode);
            if(keyCode==KeyEvent.VK_Z){
                gp.uiH.subState=0;
                gp.uiH.commandNum=1;
                gp.uiH.playerSlotCol=0;
                gp.uiH.playerSlotRow=0;
            }
        }

    }

    public void playerInvControls(int keyCode){
        if(keyCode==KeyEvent.VK_UP){
            if(gp.uiH.playerSlotRow !=0){
                gp.uiH.playerSlotRow--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_DOWN){
            if(gp.uiH.playerSlotRow !=3){
                gp.uiH.playerSlotRow++;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_LEFT){
            if(gp.uiH.playerSlotCol ==0 && gp.uiH.playerSlotRow >0){
                gp.uiH.playerSlotRow--;
                gp.uiH.playerSlotCol =4;
                gp.playSoundEffect(3);
            }else if(!(gp.uiH.playerSlotCol ==0 && gp.uiH.playerSlotRow ==0)){
                gp.uiH.playerSlotCol--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if(gp.uiH.playerSlotCol ==4 && gp.uiH.playerSlotRow <3){
                gp.uiH.playerSlotRow++;
                gp.uiH.playerSlotCol =0;
                gp.playSoundEffect(3);
            }else if(!(gp.uiH.playerSlotCol ==4 && gp.uiH.playerSlotRow ==3)){
                gp.uiH.playerSlotCol++;
                gp.playSoundEffect(3);
            }
        }
    }

    public void tradeNpcInvControls(int keyCode){
        if(keyCode==KeyEvent.VK_UP){
            if(gp.uiH.npcSlotRow !=0){
                gp.uiH.npcSlotRow--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_DOWN){
            if(gp.uiH.npcSlotRow !=3){
                gp.uiH.npcSlotRow++;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_LEFT){
            if(gp.uiH.npcSlotCol ==0 && gp.uiH.npcSlotRow >0){
                gp.uiH.npcSlotRow--;
                gp.uiH.npcSlotCol =4;
                gp.playSoundEffect(3);
            }else if(!(gp.uiH.npcSlotCol ==0 && gp.uiH.npcSlotRow ==0)){
                gp.uiH.npcSlotCol--;
                gp.playSoundEffect(3);
            }
        }
        if(keyCode==KeyEvent.VK_RIGHT){
            if(gp.uiH.npcSlotCol ==4 && gp.uiH.npcSlotRow <3){
                gp.uiH.npcSlotRow++;
                gp.uiH.npcSlotCol =0;
                gp.playSoundEffect(3);
            }else if(!(gp.uiH.npcSlotCol ==4 && gp.uiH.npcSlotRow ==3)){
                gp.uiH.npcSlotCol++;
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
