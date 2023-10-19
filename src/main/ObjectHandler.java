package main;

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

        gp.obj[mapNum][i] = new Carrot(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new Candy(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*21;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;
        gp.obj[mapNum][i] = new FireballRed(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*21;
        gp.obj[mapNum][i].worldY = gp.tileSize*18;
        i++;
    }

    public void setNPCs(){
        int mapNum=0;

        gp.npcs[mapNum][0] = new Budi(gp);
        gp.npcs[mapNum][0].worldX = gp.tileSize*20;
        gp.npcs[mapNum][0].worldY = gp.tileSize*21;
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

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*10;
        gp.monsters[mapNum][i].worldY = gp.tileSize*32;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*9;
        gp.monsters[mapNum][i].worldY = gp.tileSize*7;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*10;
        gp.monsters[mapNum][i].worldY = gp.tileSize*7;
        i++;

        gp.monsters[mapNum][i] = new Ogim(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*11;
        gp.monsters[mapNum][i].worldY = gp.tileSize*7;
        i++;
    }

    public void setInteractiveTiles() {
        int i=0;
        int mapNum=0;

        gp.iTiles[mapNum][i] = new DryTree0(gp,30,22); i++;

        gp.iTiles[mapNum][i] = new DryTree0(gp,10,22); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,9,22); i++;

        gp.iTiles[mapNum][i] = new DryTree0(gp,13,40); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,13,41); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,14,41); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,15,41); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,15,40); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,16,40); i++;
        gp.iTiles[mapNum][i] = new DryTree0(gp,17,40); i++;
    }

}
