package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class BurntTree extends InteractiveTile{
    public BurntTree(GamePanel gp, int col, int row) {
        super(gp,col,row);

        name="Burnt Tree";

        worldX=gp.TILE_SIZE *col;
        worldY=gp.TILE_SIZE *row;

        down1=setImage("/interactive_tiles/burnt_tree",gp.TILE_SIZE,gp.TILE_SIZE);
        canChange=true;
        destructible=false;
        collisionOn=false;

        hp=1;
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
            gp.interactiveTiles[gp.currentMapNum][i]=new DryTree(gp,worldX/gp.TILE_SIZE,worldY/gp.TILE_SIZE);

        collisionBox.x=collisionBoxDefaultX;
        collisionBox.y=collisionBoxDefaultY;
    }
}
