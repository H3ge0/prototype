package main;

import object.Carrot;
import object.Rabbit;
import object.TreasureChest;

public class ObjectHandler {

    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        gp.obj[0] = new Carrot();
        gp.obj[0].worldX = 23*gp.tileSize;
        gp.obj[0].worldY = 7*gp.tileSize;

        gp.obj[1] = new Carrot();
        gp.obj[1].worldX = 23*gp.tileSize;
        gp.obj[1].worldY = 39*gp.tileSize;

        gp.obj[2] = new Carrot();
        gp.obj[2].worldX = 38*gp.tileSize;
        gp.obj[2].worldY = 9*gp.tileSize;

        gp.obj[3] = new Rabbit();
        gp.obj[3].worldX = 12*gp.tileSize;
        gp.obj[3].worldY = 21*gp.tileSize;

        gp.obj[4] = new Rabbit();
        gp.obj[4].worldX = 8*gp.tileSize;
        gp.obj[4].worldY = 28*gp.tileSize;

        gp.obj[5] = new Rabbit();
        gp.obj[5].worldX = 10*gp.tileSize;
        gp.obj[5].worldY = 11*gp.tileSize;

        gp.obj[6] = new TreasureChest();
        gp.obj[6].worldX = 10*gp.tileSize;
        gp.obj[6].worldY = 7*gp.tileSize;
    }

}
