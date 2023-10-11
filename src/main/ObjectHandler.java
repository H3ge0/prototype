package main;

import entity.Budi;
import monster.Ogim;

public class ObjectHandler {

    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {

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

}
