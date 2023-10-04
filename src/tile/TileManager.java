package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    Tile[] tiles;
    Random random;
    int[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[10];

        map= new int[gp.maxWorldCol][gp.maxWorldRow];
        random = new Random();

        getTileImage();
        loadMap("/maps/world01.txt");
        //generateRandomMap();
    }

    public void getTileImage(){
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass1.png"));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass2.png"));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/evil_brick.png"));

            tiles[3] = new Tile();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

            tiles[4] = new Tile();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/brick.png"));

            tiles[5] = new Tile();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));

            tiles[6] = new Tile();
            tiles[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String path){
        try {

            InputStream is = getClass().getResourceAsStream(path);
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

        } catch (Exception e){

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
                g2.drawImage(tiles[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            worldCol++;
            if(worldCol==gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }

    }

}
