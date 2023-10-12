package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ThrowingKnife extends Projectile {
    public ThrowingKnife(GamePanel gp) {
        super(gp);

        name="Throwing Knife";
        displayedName="Fırlatma Bıçağı";
        speed=6;
        maxHp=80;
        hp=maxHp;
        attack=2;
        useCost=1;
        alive=false;
        getImages();
    }

    public void getImages(){
        upidle = setImage("/projectiles/throwing_knife/01",gp.tileSize*2/3,gp.tileSize*2/3);
        up1 = setImage("/projectiles/throwing_knife/02",gp.tileSize*2/3,gp.tileSize*2/3);
        up2 = setImage("/projectiles/throwing_knife/03",gp.tileSize*2/3,gp.tileSize*2/3);
        upidlev2 = setImage("/projectiles/throwing_knife/04",gp.tileSize*2/3,gp.tileSize*2/3);
        up1v2 = setImage("/projectiles/throwing_knife/05",gp.tileSize*2/3,gp.tileSize*2/3);
        up2v2 = setImage("/projectiles/throwing_knife/06",gp.tileSize*2/3,gp.tileSize*2/3);
        downidle = setImage("/projectiles/throwing_knife/07",gp.tileSize*2/3,gp.tileSize*2/3);
        down1 = setImage("/projectiles/throwing_knife/08",gp.tileSize*2/3,gp.tileSize*2/3);
        down2 = setImage("/projectiles/throwing_knife/09",gp.tileSize*2/3,gp.tileSize*2/3);
        downidlev2 = setImage("/projectiles/throwing_knife/10",gp.tileSize*2/3,gp.tileSize*2/3);
        down1v2 = setImage("/projectiles/throwing_knife/11",gp.tileSize*2/3,gp.tileSize*2/3);
        down2v2 = setImage("/projectiles/throwing_knife/12",gp.tileSize*2/3,gp.tileSize*2/3);
        leftidle = setImage("/projectiles/throwing_knife/13",gp.tileSize*2/3,gp.tileSize*2/3);
        left1 = setImage("/projectiles/throwing_knife/14",gp.tileSize*2/3,gp.tileSize*2/3);
        left2 = setImage("/projectiles/throwing_knife/15",gp.tileSize*2/3,gp.tileSize*2/3);
        leftidlev2 = setImage("/projectiles/throwing_knife/16",gp.tileSize*2/3,gp.tileSize*2/3);
        left1v2 = setImage("/projectiles/throwing_knife/17",gp.tileSize*2/3,gp.tileSize*2/3);
        left2v2 = setImage("/projectiles/throwing_knife/18",gp.tileSize*2/3,gp.tileSize*2/3);
        rightidle = setImage("/projectiles/throwing_knife/19",gp.tileSize*2/3,gp.tileSize*2/3);
        right1 = setImage("/projectiles/throwing_knife/20",gp.tileSize*2/3,gp.tileSize*2/3);
        right2 = setImage("/projectiles/throwing_knife/21",gp.tileSize*2/3,gp.tileSize*2/3);
        rightidlev2 = setImage("/projectiles/throwing_knife/22",gp.tileSize*2/3,gp.tileSize*2/3);
        right1v2 = setImage("/projectiles/throwing_knife/23",gp.tileSize*2/3,gp.tileSize*2/3);
        right2v2 = setImage("/projectiles/throwing_knife/24",gp.tileSize*2/3,gp.tileSize*2/3);
    }

    @Override
    public void increaseSpriteCounter() {
        spriteCounter++;
        if (spriteCounter>2){
            if (spriteNum==24){
                spriteNum=1;
            } else {
                spriteNum++;
            }
            spriteCounter=0;
        }
    }

    @Override
    public BufferedImage setDrawImage() {
        BufferedImage image=null;
        switch(spriteNum){
            case 1 -> image = upidle;
            case 2 -> image = up1;
            case 3 -> image = up2;
            case 4 -> image = downidle;
            case 5 -> image = down1;
            case 6 -> image = down2;
            case 7 -> image = leftidle;
            case 8 -> image = left1;
            case 9 -> image = left2;
            case 10 -> image = rightidle;
            case 11 -> image = right1;
            case 12 -> image = right2;
            case 13 -> image = upidlev2;
            case 14 -> image = up1v2;
            case 15 -> image = up2v2;
            case 16 -> image = downidlev2;
            case 17 -> image = down1v2;
            case 18 -> image = down2v2;
            case 19 -> image = leftidlev2;
            case 20 -> image = left1v2;
            case 21 -> image = left2v2;
            case 22 -> image = rightidlev2;
            case 23 -> image = right1v2;
            case 24 -> image = right2v2;
        }
        return image;
    }

    @Override
    public void drawTheImage(Graphics2D g2, BufferedImage image, int x, int y) {
        g2.drawImage(image,x+8,y+8,null);
    }
}
