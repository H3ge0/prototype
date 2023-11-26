package main;

import entity.Entity;
import entity.PlayerDummy;
import monster.Ipog;
import object.IronDoor;

import java.awt.*;

public class CutsceneHandler {

    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    //Scene Number
    public final int NA = 0;
    public final int ipogScene = 1;

    public CutsceneHandler(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        switch(sceneNum){
            case 1 -> playIpogScene();
        }
    }

    public void playIpogScene(){
        if(scenePhase==0){
            gp.bossBattleOn=true;

            //Place the door
            for(int i=0; i<gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i]==null){
                    gp.obj[gp.currentMap][i] = new IronDoor(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSoundEffect(27);
                    break;
                }
            }

            //Place the dummy
            for(int i=0; i<gp.npcs[1].length; i++){
                if(gp.npcs[gp.currentMap][i]==null){
                    gp.npcs[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npcs[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npcs[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npcs[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }

            gp.player.drawing=false;

            scenePhase++;
        }

        if (scenePhase==1){
            gp.player.worldY -= 2;

            if (gp.player.worldY < gp.tileSize * 17) {
                scenePhase++;
            }
        }

        if (scenePhase==2){
            //Wake up ipog
            for(int i=0; i<gp.monsters[1].length; i++){
                if(gp.monsters[gp.currentMap][i]!=null && gp.monsters[gp.currentMap][i].name.equals(Ipog.monName)){
                    gp.monsters[gp.currentMap][i].sleep=false;
                    gp.uiH.npc = gp.monsters[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }

        if (scenePhase==3){
            gp.uiH.drawDialogueScreen();
        }

        if (scenePhase==4){
            //Place the dummy
            for(int i=0; i<gp.npcs[1].length; i++){
                if(gp.npcs[gp.currentMap][i]!=null && gp.npcs[gp.currentMap][i].name.equals(PlayerDummy.npcName)){
                    gp.player.worldY = gp.npcs[gp.currentMap][i].worldY;

                    gp.npcs[gp.currentMap][i]=null;
                    break;
                }
            }

            gp.player.drawing=true;

            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;
            gp.stopMusic();
            gp.playMusic(25);
        }
    }

}
