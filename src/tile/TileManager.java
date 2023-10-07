package tile;

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
    public int[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[50];

        map= new int[gp.maxWorldCol][gp.maxWorldRow];
        random = new Random();

        getTileImage();
        generateRandomMap();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){
        setTile(0,"grass0",false);

        setTile(1,"grass1",false);

        setTile(2,"evil_brick",true);

        setTile(3,"fake_evil_brick",false);

        setTile(4,"water4",true);

        setTile(5,"brick",true);

        setTile(6,"tree0",true);

        setTile(7,"tree1",true);

        setTile(8,"tree2",true);

        setTile(9,"tree2",false);

        setTile(10,"sand4",false);

        setTile(11,"water0",true);

        setTile(12,"water1",true);

        setTile(13,"water2",true);

        setTile(14,"water3",true);

        setTile(15,"water5",true);

        setTile(16,"water6",true);

        setTile(17,"water7",true);

        setTile(18,"water8",true);

        setTile(19,"water9",true);

        setTile(20,"water10",true);

        setTile(21,"water11",true);

        setTile(22,"water12",true);

        setTile(23,"sand0",false);

        setTile(24,"sand1",false);

        setTile(25,"sand2",false);

        setTile(26,"sand3",false);

        setTile(27,"sand5",false);

        setTile(28,"sand6",false);

        setTile(29,"sand7",false);

        setTile(30,"sand8",false);

        setTile(31,"sand9",false);

        setTile(32,"sand10",false);

        setTile(33,"sand11",false);

        setTile(34,"sand12",false);
    }

    public void setTile(int index, String name, boolean collision){
        UtilityTool utility = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/"+name+".png")));
            tiles[index].image = utility.scaleImage(tiles[index].image,gp.tileSize,gp.tileSize);
            tiles[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String path){
        try {

            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.maxWorldCol&&row<gp.maxWorldRow){
                String line = br.readLine();

                while (col<gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    map[col][row] = num;
                    col++;
                }
                if(col==gp.maxWorldRow){
                    col=0;
                    row++;
                }
            }

        } catch (Exception ignored){

        }

        randomizeGrass();
        randomizeTrees();
    }

    public void randomizeGrass(){
        for(int i=0;i<gp.maxWorldCol;i++){
            for (int j=0;j<gp.maxWorldRow;j++){
                if(map[i][j]==0){
                    map[i][j] = random.nextInt(0,2);
                }
            }
        }
    }

    public void randomizeTrees(){
        for(int i=0;i<gp.maxWorldCol;i++){
            for (int j=0;j<gp.maxWorldRow;j++){
                if(map[i][j]==6){
                    map[i][j] = random.nextInt(6,9);
                }
            }
        }
    }

    //Generates a completely random map
    public void generateRandomMap(){
        for(int i=0;i<gp.maxWorldCol;i++){
            for (int j=0;j<gp.maxWorldRow;j++){
                map[i][j] = random.nextInt(0,11);
            }
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol<gp.maxWorldRow&&worldRow<gp.maxWorldRow){

            int tileNum = map[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX+gp.player.screenX;
            int screenY = worldY - gp.player.worldY+gp.player.screenY;

            if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize)
                g2.drawImage(tiles[tileNum].image,screenX,screenY,null);

            worldCol++;

            if(worldCol==gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }

    }

}
