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

        map= new int[gp.maxScreenCol][gp.maxScreenRow];
        random = new Random();

        getTileImage();
        loadMap("/maps/map01.txt");
        randomizeGrass();
        //generateMap();
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

            while(col<gp.maxScreenCol&&row<gp.maxScreenRow){
                String line = br.readLine();

                while (col<gp.maxScreenCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    map[col][row] = num;
                    col++;
                }
                if(col==gp.maxScreenCol){
                    col=0;
                    row++;
                }
            }

        } catch (Exception e){

        }
    }

    public void randomizeGrass(){
        for(int i=0;i<gp.maxScreenCol;i++){
            for (int j=0;j<gp.maxScreenRow;j++){
                if(map[i][j]==0){
                    map[i][j] = random.nextInt(0,2);
                }
            }
        }
    }

    //Generates a completely random map
    public void generateMap(){
        for(int i=0;i<gp.maxScreenCol;i++){
            for (int j=0;j<gp.maxScreenRow;j++){
                map[i][j] = random.nextInt(0,4);
            }
        }
    }

    public void draw(Graphics2D g2){

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col<gp.maxScreenCol&&row<gp.maxScreenRow){

            int tileNum = map[col][row];

            g2.drawImage(tiles[tileNum].image,x,y,gp.tileSize,gp.tileSize,null);
            col++;
            x+=gp.tileSize;
            if(col==gp.maxScreenCol){
                col=0;
                x=0;
                row++;
                y+=gp.tileSize;
            }
        }

    }

}
