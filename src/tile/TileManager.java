package tile;

import ai.Node;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    public Tile[] tiles;
    Random random;
    public int[][][] map;
    boolean drawPath = false;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[60];

        map = new int[gp.MAP_AMOUNT][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        random = new Random();

        getTileImages();

        loadMap("/maps/world01.txt",0);
        loadMap("/maps/house01.txt",1);
        loadMap("/maps/dungeon01.txt",2);
        loadMap("/maps/dungeon02.txt",3);
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

    public void loadMap(String path, int mapNum){
        try {
            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.MAX_WORLD_COL &&row<gp.MAX_WORLD_ROW){
                String line = br.readLine();

                while (col<gp.MAX_WORLD_COL){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    map[mapNum][col][row] = num;
                    col++;
                }
                if(col==gp.MAX_WORLD_ROW){
                    col=0;
                    row++;
                }
            }

        } catch (Exception ignored){

        }

        randomizeGrass(mapNum);
        randomizeTrees(mapNum);
    }

    public void randomizeGrass(int mapNum){
        for(int i = 0; i<gp.MAX_WORLD_COL; i++){
            for (int j = 0; j<gp.MAX_WORLD_ROW; j++){
                if(map[mapNum][i][j]==10){
                    map[mapNum][i][j] = random.nextInt(10,12);
                }
            }
        }
    }

    public void randomizeTrees(int mapNum){
        for(int i = 0; i<gp.MAX_WORLD_COL; i++){
            for (int j = 0; j<gp.MAX_WORLD_ROW; j++){
                if(map[mapNum][i][j]==16){
                    map[mapNum][i][j] = random.nextInt(16,19);
                }
            }
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol<gp.MAX_WORLD_ROW &&worldRow<gp.MAX_WORLD_ROW){

            int tileNum = map[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.TILE_SIZE;
            int worldY = worldRow * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX+gp.player.screenX;
            int screenY = worldY - gp.player.worldY+gp.player.screenY;

            if(Math.abs(gp.player.worldX-worldX)<gp.SCREEN_WIDTH /2+gp.TILE_SIZE && Math.abs(gp.player.worldY-worldY)<gp.SCREEN_HEIGHT /2+gp.TILE_SIZE)
                g2.drawImage(tiles[tileNum].image,screenX,screenY,null);

            worldCol++;

            if(worldCol==gp.MAX_WORLD_COL){
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

}
