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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    public Tile[] tiles;
    Random random;
    public Map[] maps;
    boolean drawPath = false;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        //Read tile data file
        InputStream is  = getClass().getResourceAsStream("/maps/tiledata.txt");
        assert is != null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try{
            while((line = br.readLine()) != null){
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        tiles = new Tile[fileNames.size()];
        getTileImages();

        maps = new Map[gp.MAP_AMOUNT];
        random = new Random();

        setMaps();
    }

    public void setMaps(){
        maps[0] = new Map(gp,50,50,Map.OUTSIDE, 0,"/maps/world01.txt");
        maps[1] = new Map(gp,7,8,Map.INSIDE, 23,"/maps/house01.txt");
        maps[2] = new Map(gp,50,50,Map.DUNGEON,24,"/maps/dungeon01.txt");
        maps[3] = new Map(gp,50,50,Map.DUNGEON, 24,"/maps/dungeon02.txt");
    }

    public void getTileImages(){
        for(int i=0;i<fileNames.size();i++){
            String path;
            boolean collision;

            path = fileNames.get(i);

            collision = collisionStatus.get(i).equals("true");

            setTile(i,path,collision);
        }
    }

    public void setTile(int index, String name, boolean collision){
        UtilityTool utility = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/"+name)));
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
