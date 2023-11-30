package tile;

import ai.Node;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    public Tile[] tiles;
    Random random;
    public Map[] maps;
    boolean drawPath = false;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[60];

        maps = new Map[gp.MAP_AMOUNT];
        random = new Random();

        getTileImages();

        maps[0] = new Map(gp,50,50,Map.WORLD1,"/maps/world01.txt");
        maps[1] = new Map(gp,7,8,Map.BOBO_HOUSE,"/maps/house01.txt");
        maps[2] = new Map(gp,50,50,Map.DUNGEON,"/maps/dungeon01.txt");
        maps[3] = new Map(gp,50,50,Map.DUNGEON,"/maps/dungeon02.txt");
    }

    public void getTileImages(){
        //Placeholder
        setTile(0,"grass0",false);
        setTile(1,"grass0",false);
        setTile(2,"grass0",false);
        setTile(3,"grass0",false);
        setTile(4,"grass0",false);
        setTile(5,"grass0",false);
        setTile(6,"grass0",false);
        setTile(7,"grass0",false);
        setTile(8,"grass0",false);
        setTile(9,"grass0",false);
        //Placeholder

        //real
        setTile(10,"grass0",false);
        setTile(11,"grass1",false);
        setTile(12,"evil_brick",true);
        setTile(13,"fake_evil_brick",false);
        setTile(14,"water4",true);
        setTile(15,"brick",true);
        setTile(16,"tree0",true);
        setTile(17,"tree1",true);
        setTile(18,"tree2",true);
        setTile(19,"sand4",false);
        setTile(20,"water0",true);
        setTile(21,"water1",true);
        setTile(22,"water2",true);
        setTile(23,"water3",true);
        setTile(24,"water5",true);
        setTile(25,"water6",true);
        setTile(26,"water7",true);
        setTile(27,"water8",true);
        setTile(28,"water9",true);
        setTile(29,"water10",true);
        setTile(30,"water11",true);
        setTile(31,"water12",true);
        setTile(32,"fake_water4",false);
        setTile(33,"sand0",false);
        setTile(34,"sand1",false);
        setTile(35,"sand2",false);
        setTile(36,"sand3",false);
        setTile(37,"sand5",false);
        setTile(38,"sand6",false);
        setTile(39,"sand7",false);
        setTile(40,"sand8",false);
        setTile(41,"sand9",false);
        setTile(42,"sand10",false);
        setTile(43,"sand11",false);
        setTile(44,"sand12",false);
        setTile(45,"house",false);
        setTile(46,"planks",false);
        setTile(47,"table",true);
        setTile(48,"dirt",false);
        setTile(49,"ladder_down",false);
        setTile(50,"ladder_up",false);
        setTile(51,"ladder_down_dirt",false);
    }

    public void setTile(int index, String name, boolean collision){
        UtilityTool utility = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/"+name+".png")));
            tiles[index].image = utility.scaleImage(tiles[index].image,gp.TILE_SIZE,gp.TILE_SIZE);
            tiles[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol<getCurrentMapMaxCol() && worldRow<getCurrentMapMaxRow()){

            int tileNum = maps[gp.currentMapNum].mapData[worldCol][worldRow];

            int worldX = worldCol * gp.TILE_SIZE;
            int worldY = worldRow * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX+gp.player.screenX;
            int screenY = worldY - gp.player.worldY+gp.player.screenY;

            if(Math.abs(gp.player.worldX-worldX)<gp.SCREEN_WIDTH /2+gp.TILE_SIZE && Math.abs(gp.player.worldY-worldY)<gp.SCREEN_HEIGHT /2+gp.TILE_SIZE)
                g2.drawImage(tiles[tileNum].image,screenX,screenY,null);

            worldCol++;

            if(worldCol==getCurrentMapMaxCol()){
                worldCol=0;
                worldRow++;
            }
        }

        if(drawPath){
            g2.setColor(new Color(255,0,0,70));
            for(Node node:gp.pathFinder.pathList){
                int worldX = node.col * gp.TILE_SIZE;
                int worldY = node.row * gp.TILE_SIZE;
                int screenX = worldX - gp.player.worldX+gp.player.screenX;
                int screenY = worldY - gp.player.worldY+gp.player.screenY;

                g2.fillRect(screenX,screenY,gp.TILE_SIZE,gp.TILE_SIZE);
            }
        }
    }

    public int getCurrentMapMaxCol(){
        return maps[gp.currentMapNum].maxWorldCol;
    }

    public int getCurrentMapMaxRow(){
        return maps[gp.currentMapNum].maxWorldRow;
    }

}
