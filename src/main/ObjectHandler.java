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
        gp.monsters[0] = new Ogim(gp);
        gp.monsters[0].worldX = gp.tileSize*23;
        gp.monsters[0].worldY = gp.tileSize*36;

        gp.monsters[1] = new Ogim(gp);
        gp.monsters[1].worldX = gp.tileSize*25;
        gp.monsters[1].worldY = gp.tileSize*37;
    }

}
