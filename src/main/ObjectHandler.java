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

        gp.obj[i] = new Carrot(gp);
        gp.obj[i].worldX = gp.tileSize*25;
        gp.obj[i].worldY = gp.tileSize*23;
        i++;
        gp.obj[i] = new Candy(gp);
        gp.obj[i].worldX = gp.tileSize*21;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new FireballRed(gp);
        gp.obj[i].worldX = gp.tileSize*22;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        gp.obj[i] = new FireballPurple(gp);
        gp.obj[i].worldX = gp.tileSize*23;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
    }

    public void setNPCs(){
        gp.npcs[0] = new Budi(gp);
        gp.npcs[0].worldX = gp.tileSize*20;
        gp.npcs[0].worldY = gp.tileSize*21;
    }

    public void setMonsters(){
        int i=0;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*23;
        gp.monsters[i].worldY = gp.tileSize*36;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*25;
        gp.monsters[i].worldY = gp.tileSize*37;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*35;
        gp.monsters[i].worldY = gp.tileSize*38;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*38;
        gp.monsters[i].worldY = gp.tileSize*42;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*35;
        gp.monsters[i].worldY = gp.tileSize*9;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*39;
        gp.monsters[i].worldY = gp.tileSize*11;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*10;
        gp.monsters[i].worldY = gp.tileSize*32;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*9;
        gp.monsters[i].worldY = gp.tileSize*7;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*10;
        gp.monsters[i].worldY = gp.tileSize*7;
        i++;

        gp.monsters[i] = new Ogim(gp);
        gp.monsters[i].worldX = gp.tileSize*11;
        gp.monsters[i].worldY = gp.tileSize*7;
        i++;
    }

    public void setInteractiveTiles() {
        int i=0;

        gp.iTiles[i] = new DryTree0(gp,25,10); i++;
        gp.iTiles[i] = new DryTree0(gp,26,10); i++;
        gp.iTiles[i] = new DryTree0(gp,27,10); i++;
        gp.iTiles[i] = new DryTree0(gp,28,10); i++;
        gp.iTiles[i] = new DryTree0(gp,29,10); i++;
        gp.iTiles[i] = new DryTree0(gp,30,10); i++;
        gp.iTiles[i] = new DryTree0(gp,31,10); i++;
        gp.iTiles[i] = new DryTree0(gp,32,10); i++;
    }

}
