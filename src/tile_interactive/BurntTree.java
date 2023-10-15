package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class BurntTree extends InteractiveTile{
    public BurntTree(GamePanel gp, int col, int row) {
        super(gp,col,row);

        name="Burnt Tree";

        worldX=gp.tileSize*col;
        worldY=gp.tileSize*row;

        down1=setImage("/interactive_tiles/burnt_tree0",gp.tileSize,gp.tileSize);
        canChange=true;
        collisionOn=false;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        return entity.currentFireball.name.equals("Purple Fireball");
    }

    @Override
    public void attack(int i) {
        collisionBox.x+=worldX;
        collisionBox.y+=worldY;

        if(!collisionBox.intersects(gp.player.collisionBox))
            gp.iTiles[i]=new DryTree0(gp,worldX/gp.tileSize,worldY/gp.tileSize);

        collisionBox.x=collisionBoxDefaultX;
        collisionBox.y=collisionBoxDefaultY;
    }
}
