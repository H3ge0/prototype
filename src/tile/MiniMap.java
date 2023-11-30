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

        for(int i = 0; i<gp.MAP_AMOUNT; i++){
            int worldMapWidth = gp.TILE_SIZE *gp.tileManager.maps[i].maxWorldCol;
            int worldMapHeight = gp.TILE_SIZE *gp.tileManager.maps[i].maxWorldRow;

            worldMap[i] = new BufferedImage(worldMapWidth,worldMapHeight,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col=0, row=0;

            while(col<gp.tileManager.maps[i].maxWorldCol && row<gp.tileManager.maps[i].maxWorldRow){
                int tileNum = maps[i].mapData[col][row];
                int x = gp.TILE_SIZE *col;
                int y = gp.TILE_SIZE *row;
                g2.drawImage(tiles[tileNum].image,x,y,null);

                col++;
                if(col==gp.tileManager.maps[i].maxWorldCol){
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
            g2.setColor(Color.orange);
            g2.drawImage(worldMap[gp.currentMapNum],x,y,width,height,null);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(x-1,y-1,width+1,height+1);

            double scaleX = (double)(gp.TILE_SIZE * gp.tileManager.getCurrentMapMaxCol())/width;
            double scaleY = (double)(gp.TILE_SIZE * gp.tileManager.getCurrentMapMaxRow())/height;
            int playerX = (int) (x + gp.player.getCenterX()/scaleX);
            int playerY = (int) (y + gp.player.getCenterY()/scaleY);
            g2.setColor(Color.red);
            g2.fillRect(playerX,playerY,5,5);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }

}
