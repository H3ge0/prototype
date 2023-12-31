package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rock extends Projectile {

    public static final String objName = "Rock";

    public Rock(GamePanel gp) {
        super(gp);

        name=objName;
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
        upidle = setImage("/projectiles/rock/01",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    public BufferedImage setDrawImage(){
        return upidle;
    }

    @Override
    public Color getParticleColor(){
        return new Color(102,102,102);
    }

    @Override
    public int getParticleSize(){
        return 6;
    }

    @Override
    public int getParticleSpeed(){
        return 1;
    }

    @Override
    public int getMaxHp(){
        return 20;
    }
}
