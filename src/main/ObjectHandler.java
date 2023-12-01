package main;

import data.Progress;
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

    public void setEverything(){
        setAllObjects();
        setAllNPCs();
        setAllMonsters();
        setAllInteractiveTiles();
    }

    public void setAllObjects(){
        for(int mapNum=0;mapNum<gp.MAP_AMOUNT;mapNum++){
            setObjects(mapNum);
        }
    }

    public void setAllNPCs(){
        for(int mapNum=0;mapNum<gp.MAP_AMOUNT;mapNum++){
            setNPCs(mapNum);
        }
    }

    public void setAllMonsters(){
        for(int mapNum=0;mapNum<gp.MAP_AMOUNT;mapNum++){
            setMonsters(mapNum);
        }
    }

    public void setAllInteractiveTiles(){
        for(int mapNum=0;mapNum<gp.MAP_AMOUNT;mapNum++){
            setInteractiveTiles(mapNum);
        }
    }

    //Sets objects of an individual map
    public void setObjects(int mapNum) {
        int i=0;

        switch(mapNum) {
            case 0 -> {
                gp.objects[0][i] = new Rabbit(gp);
                gp.objects[0][i].worldX = gp.TILE_SIZE * 10;
                gp.objects[0][i].worldY = gp.TILE_SIZE * 11;
                i++;
                gp.objects[0][i] = new Rabbit(gp);
                gp.objects[0][i].worldX = gp.TILE_SIZE * 12;
                gp.objects[0][i].worldY = gp.TILE_SIZE * 24;
                i++;
                gp.objects[0][i] = new Chest(gp);
                gp.objects[0][i].setLoot(new SleepPotion(gp));
                gp.objects[0][i].worldX = gp.TILE_SIZE * 13;
                gp.objects[0][i].worldY = gp.TILE_SIZE * 6;
            }
            case 2 -> {
                gp.objects[2][i] = new Chest(gp);
                gp.objects[2][i].setLoot(new FireballBlack(gp));
                gp.objects[2][i].worldX = gp.TILE_SIZE *40;
                gp.objects[2][i].worldY = gp.TILE_SIZE *41;
                i++;
                gp.objects[2][i] = new Chest(gp);
                gp.objects[2][i].setLoot(new HealthPotion(gp));
                gp.objects[2][i].worldX = gp.TILE_SIZE *13;
                gp.objects[2][i].worldY = gp.TILE_SIZE *16;
                i++;
                gp.objects[2][i] = new Chest(gp);
                gp.objects[2][i].setLoot(new HealthPotion(gp));
                gp.objects[2][i].worldX = gp.TILE_SIZE *26;
                gp.objects[2][i].worldY = gp.TILE_SIZE *34;
                i++;
                gp.objects[2][i] = new Chest(gp);
                gp.objects[2][i].setLoot(new HealthPotion(gp));
                gp.objects[2][i].worldX = gp.TILE_SIZE *27;
                gp.objects[2][i].worldY = gp.TILE_SIZE *15;
                i++;
                gp.objects[2][i] = new IronDoor(gp);
                gp.objects[2][i].worldX = gp.TILE_SIZE *18;
                gp.objects[2][i].worldY = gp.TILE_SIZE *23;
            }
            case 3 -> {
                gp.objects[3][i] = new IronDoor(gp);
                gp.objects[3][i].worldX = gp.TILE_SIZE *25;
                gp.objects[3][i].worldY = gp.TILE_SIZE *15;
            }
        }
    }

    //Sets npcs of an individual map
    public void setNPCs(int mapNum){
        int i=0;

        switch(mapNum) {
            case 0 -> {
                gp.npcs[0][i] = new Budi(gp);
                gp.npcs[0][i].worldX = gp.TILE_SIZE *20;
                gp.npcs[0][i].worldY = gp.TILE_SIZE *21;
            }
            case 1 -> {
                gp.npcs[1][i] = new Bobo(gp);
                gp.npcs[1][i].worldX = gp.TILE_SIZE *12;
                gp.npcs[1][i].worldY = gp.TILE_SIZE *7;
            }
            case 2 -> {
                gp.npcs[2][i] = new BigRock(gp);
                gp.npcs[2][i].worldX = gp.TILE_SIZE *20;
                gp.npcs[2][i].worldY = gp.TILE_SIZE *25;
                i++;
                gp.npcs[2][i] = new BigRock(gp);
                gp.npcs[2][i].worldX = gp.TILE_SIZE *11;
                gp.npcs[2][i].worldY = gp.TILE_SIZE *18;
                i++;
                gp.npcs[2][i] = new BigRock(gp);
                gp.npcs[2][i].worldX = gp.TILE_SIZE *23;
                gp.npcs[2][i].worldY = gp.TILE_SIZE *14;
            }
        }
    }

    //Sets monsters of an individual map
    public void setMonsters(int mapNum){
        int i=0;

        switch(mapNum) {
            case 0 -> {
                gp.monsters[0][i] = new Ogim(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *23;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *36;

                i++;
                gp.monsters[0][i] = new Ogim(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *25;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *37;

                i++;
                gp.monsters[0][i] = new Ogim(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *35;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *38;

                i++;
                gp.monsters[0][i] = new Ogim(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *38;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *42;

                i++;
                gp.monsters[0][i] = new Ogim(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *35;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *9;

                i++;
                gp.monsters[0][i] = new Ogim(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *39;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *11;

                i++;
                gp.monsters[0][i] = new Apol(gp);
                gp.monsters[0][i].worldX = gp.TILE_SIZE *11;
                gp.monsters[0][i].worldY = gp.TILE_SIZE *32;
            }
            case 2 -> {
                gp.monsters[2][i] = new Afag(gp);
                gp.monsters[2][i].worldX = gp.TILE_SIZE *34;
                gp.monsters[2][i].worldY = gp.TILE_SIZE *39;
                i++;
                gp.monsters[2][i] = new Afag(gp);
                gp.monsters[2][i].worldX = gp.TILE_SIZE *36;
                gp.monsters[2][i].worldY = gp.TILE_SIZE *25;
                i++;
                gp.monsters[2][i] = new Afag(gp);
                gp.monsters[2][i].worldX = gp.TILE_SIZE *39;
                gp.monsters[2][i].worldY = gp.TILE_SIZE *26;
                i++;
                gp.monsters[2][i] = new Afag(gp);
                gp.monsters[2][i].worldX = gp.TILE_SIZE *28;
                gp.monsters[2][i].worldY = gp.TILE_SIZE *11;
                i++;
                gp.monsters[2][i] = new Afag(gp);
                gp.monsters[2][i].worldX = gp.TILE_SIZE *10;
                gp.monsters[2][i].worldY = gp.TILE_SIZE *19;
            }
            case 3 -> {
                if(!Progress.ipogDefeated){
                    gp.monsters[3][i] = new Ipog(gp);
                    gp.monsters[3][i].worldX = gp.TILE_SIZE *24;
                    gp.monsters[3][i].worldY = gp.TILE_SIZE *16;

                    i++;
                    gp.monsters[3][i] = new Obob(gp);
                    gp.monsters[3][i].worldX = gp.TILE_SIZE *28;
                    gp.monsters[3][i].worldY = gp.TILE_SIZE *17;

                    i++;
                    gp.monsters[3][i] = new Idub(gp);
                    gp.monsters[3][i].connectedEntity = gp.monsters[3][i-1];
                    gp.monsters[3][i].worldX = gp.TILE_SIZE *21;
                    gp.monsters[3][i].worldY = gp.TILE_SIZE *17;
                }
            }
        }
    }

    //Sets interactive tiles of an individual map
    public void setInteractiveTiles(int mapNum) {
        int i=0;

        switch(mapNum) {
            case 0 -> {
                gp.interactiveTiles[0][i] = new DryTree(gp,30,22);

                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,11,25);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,10,25);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,9,25);

                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,13,40);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,13,41);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,14,41);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,15,41);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,15,40);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,16,40);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,17,40);

                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,14,6);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,15,6);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,16,6);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,17,6);
                i++;
                gp.interactiveTiles[0][i] = new DryTree(gp,18,6);
            }
            case 2 -> {
                gp.interactiveTiles[2][i] = new WeakBrick(gp,18,30);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,17,31);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,17,32);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,17,34);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,18,34);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,18,33);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,10,22);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,10,24);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,38,18);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,38,19);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,38,20);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,38,21);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,18,13);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,18,14);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,22,28);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,30,28);
                i++;
                gp.interactiveTiles[2][i] = new WeakBrick(gp,32,28);


                i++;
                gp.interactiveTiles[2][i] = new DungeonPlate(gp,20,22);
                i++;
                gp.interactiveTiles[2][i] = new DungeonPlate(gp,8,17);
                i++;
                gp.interactiveTiles[2][i] = new DungeonPlate(gp,39,31);
            }
        }
    }

    public void removeTempEntities(){
        for(int mapNum = 0; mapNum<gp.MAP_AMOUNT; mapNum++){
            for(int i = 0; i<gp.objects[gp.currentMapNum].length; i++){
                if(gp.objects[mapNum][i]!=null && gp.objects[mapNum][i].temp){
                    gp.objects[mapNum][i]=null;
                }
            }
        }
    }

    public void removeNpcsAfterDefeatingIpog(){
        for(int i=0;i<gp.npcs[1].length;i++){
            if(gp.npcs[0][i]!=null && gp.npcs[0][i].name.equals(Budi.npcName)){
                gp.npcs[0][i]=null;
            }
            if(gp.npcs[1][i]!=null && gp.npcs[1][i].name.equals(Bobo.npcName)){
                gp.npcs[1][i]=null;
            }
        }
    }

}
