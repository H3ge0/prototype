package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed,downPressed,leftPressed,rightPressed,keyPressed=false,xKeyPressed;

    //Debug
    boolean debugMode=false;

    public KeyHandler(GamePanel gp){
        this.gp =gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

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
            if(keyCode==KeyEvent.VK_X){
                xKeyPressed = true;
            }
            //Other
            if(keyCode==KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            }
            //Debug
            if(keyCode==KeyEvent.VK_D){
                if(!debugMode)
                    gp.uiH.showMessage("Debug Mode:On");
                else
                    gp.uiH.showMessage("Debug Mode:Off");
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
            if(keyCode==KeyEvent.VK_X){
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

}
