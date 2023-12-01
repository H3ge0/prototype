package tile;

import main.GamePanel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Map {

    GamePanel gp;
    int maxWorldCol, maxWorldRow;
    public int[][] mapData;
    String path;
    Random random;
    //Area
    public int area;
    public final static int OUTSIDE = 0;
    public final static int INSIDE = 1;
    public final static int DUNGEON = 2;

    //Music
    public int musicNum;

    public Map(GamePanel gp, int maxWorldCol, int maxWorldRow, int area,int musicNum, String path) {
        this.gp = gp;
        this.maxWorldCol = maxWorldCol;
        this.maxWorldRow = maxWorldRow;
        this.mapData = new int[maxWorldCol][maxWorldRow];
        this.area = area;
        this.musicNum = musicNum;
        this.path = path;

        random = new Random();

        loadMap(path);
    }

    public void loadMap(String path){
        try {
            InputStream is = getClass().getResourceAsStream(path);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<maxWorldCol && row<maxWorldRow){
                String line = br.readLine();

                while (col<maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapData[col][row] = num;
                    col++;
                }
                if(col==maxWorldCol){
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
        for(int i = 0; i<maxWorldCol; i++){
            for (int j = 0; j<maxWorldRow; j++){
                if(mapData[i][j]==1){
                    mapData[i][j] = random.nextInt(1,3);
                }
            }
        }
    }

    public void randomizeTrees(){
        for(int i = 0; i<maxWorldCol; i++){
            for (int j = 0; j<maxWorldRow; j++){
                if(mapData[i][j]==30){
                    mapData[i][j] = random.nextInt(30,33);
                }
            }
        }
    }

}
