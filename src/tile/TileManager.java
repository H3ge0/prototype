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

        getTileImages();
        loadMap("/maps/world01.txt");
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
        setTile(45,"fake_water1",false);
        setTile(46,"fake_water7",false);
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
                if(map[i][j]==10){
                    map[i][j] = random.nextInt(10,12);
                }
            }
        }
    }

    public void randomizeTrees(){
        for(int i=0;i<gp.maxWorldCol;i++){
            for (int j=0;j<gp.maxWorldRow;j++){
                if(map[i][j]==16){
                    map[i][j] = random.nextInt(16,19);
                }
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
