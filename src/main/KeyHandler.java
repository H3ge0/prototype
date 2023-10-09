package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,cKeyPressed;

    //Debug
    boolean debugMode=false;

    public KeyHandler(GamePanel gp){
        this.gp =gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        //TitleState
        if(gp.gameState== gp.titleState){
            //Select
            if(keyCode==KeyEvent.VK_UP){
                if(gp.uiH.commandNum==0)
                    gp.uiH.commandNum=2;
                else
                    gp.uiH.commandNum=0;
            }
            if(keyCode==KeyEvent.VK_DOWN){
                if(gp.uiH.commandNum==2)
                    gp.uiH.commandNum=0;
                else
                    gp.uiH.commandNum=2;
            }
            if(keyCode==KeyEvent.VK_C){
                if(gp.uiH.commandNum==0){
                    gp.gameState=gp.playState;
                    gp.playMusic(0);
                }else if(gp.uiH.commandNum==1){
                    //
                }else if(gp.uiH.commandNum==2){
                    System.exit(0);
                }
            }
        }

        //PlayState
        if(gp.gameState== gp.playState){
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
            if(keyCode==KeyEvent.VK_C){
                cKeyPressed = true;
            }
            //Other
            if(keyCode==KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            }
            //Debug
            if(keyCode==KeyEvent.VK_D){
                debugMode = !debugMode;
            }
        }

        //PauseState
        else if (gp.gameState==gp.pauseState){
            //Other Controls
            if(keyCode==KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        }

        //DialogueState
        else if (gp.gameState==gp.dialogueState){
            //Other Controls
            if(keyCode==KeyEvent.VK_C){
                gp.gameState = gp.playState;
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
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
