package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Rock extends Projectile {
    public Rock(GamePanel gp) {
        super(gp);

        name="Rock";
        displayedName="Taş";
        speed=7;
        maxHp=80;
        hp=maxHp;
        attack=3;
        useCost=1;
        alive=false;
        getImages();
    }

    @Override
    public void increaseSpriteCounter() {

    }

    public void getImages(){
        upidle = setImage("/projectiles/rock/01",gp.tileSize,gp.tileSize);
    }

    public BufferedImage setDrawImage(){
        return upidle;
    }
}
