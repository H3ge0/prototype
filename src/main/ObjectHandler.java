package main;

import object.*;

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

        gp.obj[7] = new Candy();
        gp.obj[7].worldX = 11*gp.tileSize;
        gp.obj[7].worldY = 33*gp.tileSize;

        gp.obj[8] = new Candy();
        gp.obj[8].worldX = 36*gp.tileSize;
        gp.obj[8].worldY = 41*gp.tileSize;

        gp.obj[9] = new Carrot();
        gp.obj[9].worldX = 35*gp.tileSize;
        gp.obj[9].worldY = 48*gp.tileSize;

        gp.obj[10] = new Carrot();
        gp.obj[10].worldX = 6*gp.tileSize;
        gp.obj[10].worldY = 48*gp.tileSize;

        gp.obj[11] = new Carrot();
        gp.obj[11].worldX = 7*gp.tileSize;
        gp.obj[11].worldY = 48*gp.tileSize;

        gp.obj[12] = new Rabbit();
        gp.obj[12].worldX = 8*gp.tileSize;
        gp.obj[12].worldY = 48*gp.tileSize;

        gp.obj[13] = new Rabbit();
        gp.obj[13].worldX = 3*gp.tileSize;
        gp.obj[13].worldY = gp.tileSize;

        gp.obj[14] = new Rabbit();
        gp.obj[14].worldX = 4*gp.tileSize;
        gp.obj[14].worldY = gp.tileSize;

        gp.obj[15] = new Chest();
        gp.obj[15].worldX = gp.tileSize;
        gp.obj[15].worldY = gp.tileSize;
    }

}
