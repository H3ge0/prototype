package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class DungeonPlate extends InteractiveTile{

    public static final String itName = "Dungeon Plate";

    public DungeonPlate(GamePanel gp, int col, int row) {
        super(gp,col,row);

        name= itName;

        worldX=gp.tileSize*col;
        worldY=gp.tileSize*row;

        down1=setImage("/interactive_tiles/dungeon_plate",gp.tileSize,gp.tileSize);
        canChange=false;
        destructible=false;
        collisionOn=false;
    }

}
