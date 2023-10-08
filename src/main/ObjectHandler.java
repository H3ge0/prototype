package main;

import entity.Ralph;

public class ObjectHandler {

    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {

    }

    public void setNPCs(){
        gp.npcs[0] = new Ralph(gp);
        gp.npcs[0].worldX = gp.tileSize*20;
        gp.npcs[0].worldY = gp.tileSize*21;
    }

}
