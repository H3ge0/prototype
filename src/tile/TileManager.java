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

        tiles = new Tile[10];

        map= new int[gp.maxWorldCol][gp.maxWorldRow];
        random = new Random();

        getTileImage();
        generateRandomMap();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){
        setTile(0,"grass1",false);

        setTile(1,"grass2",false);

        setTile(2,"evil_brick",true);

        setTile(3,"water",true);

        setTile(4,"brick",true);

        setTile(5,"tree",true);

        setTile(6,"sand",false);

        setTile(7,"fake_tree",false);

        setTile(8,"fake_evil_brick",false);
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

    //Generates a completely random map
    public void generateRandomMap(){
        for(int i=0;i<gp.maxWorldCol;i++){
            for (int j=0;j<gp.maxWorldRow;j++){
                map[i][j] = random.nextInt(0,7);
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
