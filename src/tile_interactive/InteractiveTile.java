package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {

    public boolean canChange =false;
    public boolean destructible =false;

    public InteractiveTile(GamePanel gp,int col,int row) {
        super(gp);
        collisionOn=true;
    }

    public boolean isCorrectItem(Entity entity){
        return false;
    }

    @Override
    public void update() {
        if(invincible){
            invincibleCounter++;
            if (invincibleCounter>20){
                invincible=false;
                invincibleCounter=0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize){
            drawTheImage(g2,down1,screenX,screenY);
        }
    }

    public void attack(int i){}
    public void generateITileParticle(int i){}
}
