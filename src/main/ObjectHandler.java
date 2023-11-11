package main;

import monster.Apol;
import entity.Bobo;
import entity.Budi;
import monster.Ogim;
import object.*;
import tile_interactive.DryTree0;

public class ObjectHandler {

    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        int i=0;
        int mapNum=0;

        gp.obj[mapNum][i] = new Rabbit(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*10;
        gp.obj[mapNum][i].worldY = gp.tileSize*11;
        i++;
        gp.obj[mapNum][i] = new Rabbit(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*12;
        gp.obj[mapNum][i].worldY = gp.tileSize*24;
        i++;
        gp.obj[mapNum][i] = new Chest(gp,new SleepPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*6;
        /*
        i++;
        gp.obj[mapNum][i] = new FireballRed(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*24;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        */
    }

    public void setNPCs(){
        int mapNum=0;
        int i=0;

        gp.npcs[mapNum][i] = new Budi(gp);
        gp.npcs[mapNum][i].worldX = gp.tileSize*20;
        gp.npcs[mapNum][i].worldY = gp.tileSize*21;

        mapNum=1;
        i=0;
        gp.npcs[mapNum][i] = new Bobo(gp);
        gp.npcs[mapNum][i].worldX = gp.tileSize*12;
        gp.npcs[mapNum][i].worldY = gp.tileSize*7;
    }

    public void setMonsters(){
        int i=0;
        int mapNum=0;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*23;
        gp.monsters[mapNum][i].worldY = gp.tileSize*36;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*25;
        gp.monsters[mapNum][i].worldY = gp.tileSize*37;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*35;
        gp.monsters[mapNum][i].worldY = gp.tileSize*38;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*38;
        gp.monsters[mapNum][i].worldY = gp.tileSize*42;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*35;
        gp.monsters[mapNum][i].worldY = gp.tileSize*9;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*39;
        gp.monsters[mapNum][i].worldY = gp.tileSize*11;
        i++;

        gp.monsters[mapNum][i] = new Apol(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*11;
        gp.monsters[mapNum][i].worldY = gp.tileSize*32;
    }

    public void setInteractiveTiles() {
        int i=0;
        int mapNum=0;

        gp.iTiles[mapNum][i] = new DryTree0(gp,30,22); i++;

        gp.iTiles[mapNum][i] = new DryTree0(gp,11,25); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,10,25); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,9,25); i++;

        gp.iTiles[mapNum][i] = new DryTree0(gp,13,40); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,13,41); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,14,41); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,15,41); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,15,40); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,16,40); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,17,40); i++;

        gp.iTiles[mapNum][i] = new DryTree0(gp,14,6); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,15,6); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,16,6); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,17,6); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,18,6); i++;
    }

}
