package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MiniMap extends TileManager{
    GamePanel gp;
    BufferedImage[] worldMap;
    public boolean miniMapOn = true;
    public MiniMap(GamePanel gp) {
        super(gp);
        this.gp=gp;
        createWorldMap();
    }

    public void createWorldMap(){
        worldMap = new BufferedImage[gp.MAP_AMOUNT];
        int worldMapWidth = gp.TILE_SIZE *gp.MAX_WORLD_COL;
        int worldMapHeight = gp.TILE_SIZE *gp.MAX_WORLD_ROW;

        for(int i = 0; i<gp.MAP_AMOUNT; i++){
            worldMap[i] = new BufferedImage(worldMapWidth,worldMapHeight,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col=0, row=0;

            while(col<gp.MAX_WORLD_COL && row<gp.MAX_WORLD_ROW){
                int tileNum = map[i][col][row];
                int x = gp.TILE_SIZE *col;
                int y = gp.TILE_SIZE *row;
                g2.drawImage(tiles[tileNum].image,x,y,null);

                col++;
                if(col==gp.MAX_WORLD_COL){
                    col=0;
                    row++;
                }
            }

            g2.dispose();
        }
    }

    public void drawMiniMap(Graphics2D g2){
        if(miniMapOn){
            int width=200,height=200;
            int x = gp.SCREEN_WIDTH -width-25;
            int y = 25;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
            //Draw Minimap
            g2.setColor(Color.black);
            g2.drawImage(worldMap[gp.currentMap],x,y,width,height,null);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(x,y,width,height);

            double scale = (double)(gp.TILE_SIZE * gp.MAX_WORLD_COL)/width;
            int playerX = (int) (x + gp.player.worldX/scale);
            int playerY = (int) (y + gp.player.worldY/scale);
            g2.setColor(Color.red);
            g2.fillRect(playerX,playerY,5,5);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }

}
