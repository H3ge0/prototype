package tile_interactive;

import main.GamePanel;

public class DungeonPlate extends InteractiveTile{

    public static final String itName = "Dungeon Plate";

    public DungeonPlate(GamePanel gp, int col, int row) {
        super(gp,col,row);

        name= itName;

        worldX=gp.TILE_SIZE *col;
        worldY=gp.TILE_SIZE *row;

        down1=setImage("/interactive_tiles/dungeon_plate",gp.TILE_SIZE,gp.TILE_SIZE);
        canChange=false;
        destructible=false;
        collisionOn=false;
    }

}
