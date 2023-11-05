package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
    GamePanel gp;
    BufferedImage[] worldMap;
    public boolean miniMapOn = true;
    public Map(GamePanel gp) {
        super(gp);
        this.gp=gp;
        createWorldMap();
    }

    public void createWorldMap(){
        worldMap = new BufferedImage[gp.mapAmount];
        int worldMapWidth = gp.tileSize*gp.maxWorldCol;
        int worldMapHeight = gp.tileSize*gp.maxWorldRow;

        for(int i=0;i<gp.mapAmount;i++){
            worldMap[i] = new BufferedImage(worldMapWidth,worldMapHeight,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics();

            int col=0, row=0;

            while(col<gp.maxWorldCol && row<gp.maxWorldRow){
                int tileNum = map[i][col][row];
                int x = gp.tileSize*col;
                int y = gp.tileSize*row;
                g2.drawImage(tiles[tileNum].image,x,y,null);

                col++;
                if(col==gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
        }
    }

    public void drawMiniMap(Graphics2D g2){
        if(miniMapOn){
            int width=200,height=200;
            int x = gp.screenWidth-width-25;
            int y = 25;

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
            //Draw Minimap
            g2.setColor(Color.black);
            g2.drawImage(worldMap[gp.currentMap],x,y,width,height,null);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(x,y,width,height);

            double scale = (double)(gp.tileSize * gp.maxWorldCol)/width;
            int playerX = (int) (x + gp.player.worldX/scale);
            int playerY = (int) (y + gp.player.worldY/scale);
            g2.setColor(Color.red);
            g2.fillRect(playerX,playerY,5,5);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }

}
