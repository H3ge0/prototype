package main;

import entity.BigRock;
import monster.*;
import entity.Bobo;
import entity.Budi;
import object.*;
import tile_interactive.DryTree;
import tile_interactive.DungeonPlate;
import tile_interactive.WeakBrick;

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
        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new SleepPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*6;
        /*
        i++;
        gp.obj[mapNum][i] = new Carrot(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new Carrot(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new FireballRed(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        */

        i=0;
        mapNum=2;

        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new FireballBlack(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*40;
        gp.obj[mapNum][i].worldY = gp.tileSize*41;
        i++;
        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new SleepPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*16;
        i++;
        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new SleepPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*34;
        i++;
        gp.obj[mapNum][i] = new Chest(gp);
        gp.obj[mapNum][i].setLoot(new SleepPotion(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*27;
        gp.obj[mapNum][i].worldY = gp.tileSize*15;
        i++;
        gp.obj[mapNum][i] = new IronDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*18;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;

        i=0;
        mapNum=3;

        gp.obj[mapNum][i] = new IronDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*15;
    }

    public void setNPCs(){
        int mapNum=0;
        int i=0;

        gp.npcs[mapNum][i] = new Budi(gp);
        gp.npcs[mapNum][i].worldX = gp.tileSize*20;
        gp.npcs[mapNum][i].worldY = gp.tileSize*21;

        mapNum=1;

        gp.npcs[mapNum][i] = new Bobo(gp);
        gp.npcs[mapNum][i].worldX = gp.tileSize*12;
        gp.npcs[mapNum][i].worldY = gp.tileSize*7;

        setDungeonNpcs();
    }

    public void setDungeonNpcs(){
        int i=0;

        gp.npcs[2][i] = new BigRock(gp);
        gp.npcs[2][i].worldX = gp.tileSize*20;
        gp.npcs[2][i].worldY = gp.tileSize*25;
        i++;
        gp.npcs[2][i] = new BigRock(gp);
        gp.npcs[2][i].worldX = gp.tileSize*11;
        gp.npcs[2][i].worldY = gp.tileSize*18;
        i++;
        gp.npcs[2][i] = new BigRock(gp);
        gp.npcs[2][i].worldX = gp.tileSize*23;
        gp.npcs[2][i].worldY = gp.tileSize*14;
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

        i=0;
        mapNum=2;

        gp.monsters[mapNum][i] = new Afag(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*34;
        gp.monsters[mapNum][i].worldY = gp.tileSize*39;
        i++;
        gp.monsters[mapNum][i] = new Afag(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*36;
        gp.monsters[mapNum][i].worldY = gp.tileSize*25;
        i++;
        gp.monsters[mapNum][i] = new Afag(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*39;
        gp.monsters[mapNum][i].worldY = gp.tileSize*26;
        i++;
        gp.monsters[mapNum][i] = new Afag(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*28;
        gp.monsters[mapNum][i].worldY = gp.tileSize*11;
        i++;
        gp.monsters[mapNum][i] = new Afag(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*10;
        gp.monsters[mapNum][i].worldY = gp.tileSize*19;

        i=0;
        mapNum=3;

        gp.monsters[mapNum][i] = new Ipog(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*24;
        gp.monsters[mapNum][i].worldY = gp.tileSize*16;

        i++;
        gp.monsters[mapNum][i] = new Obob(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*28;
        gp.monsters[mapNum][i].worldY = gp.tileSize*17;

        i++;
        gp.monsters[mapNum][i] = new Idub(gp);
        gp.monsters[mapNum][i].connectedEntity = gp.monsters[mapNum][i-1];
        gp.monsters[mapNum][i].worldX = gp.tileSize*21;
        gp.monsters[mapNum][i].worldY = gp.tileSize*17;
    }

    public void setInteractiveTiles() {
        int i=0;
        int mapNum=0;

        gp.iTiles[mapNum][i] = new DryTree(gp,30,22);

        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,11,25);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,10,25);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,9,25);

        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,13,40);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,13,41);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,14,41);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,15,41);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,15,40);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,16,40);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,17,40);

        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,14,6);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,15,6);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,16,6);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,17,6);
        i++;
        gp.iTiles[mapNum][i] = new DryTree(gp,18,6);

        i=0;
        mapNum=2;

        gp.iTiles[mapNum][i] = new WeakBrick(gp,18,30);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,17,31);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,17,32);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,17,34);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,18,34);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,18,33);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,10,22);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,10,24);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,38,18);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,38,19);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,38,20);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,38,21);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,18,13);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,18,14);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,22,28);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,30,28);
        i++;
        gp.iTiles[mapNum][i] = new WeakBrick(gp,32,28);


        i++;
        gp.iTiles[mapNum][i] = new DungeonPlate(gp,20,22);
        i++;
        gp.iTiles[mapNum][i] = new DungeonPlate(gp,8,17);
        i++;
        gp.iTiles[mapNum][i] = new DungeonPlate(gp,39,31);
    }

}
