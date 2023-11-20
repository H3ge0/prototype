package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class WeakBrick extends InteractiveTile{
    public WeakBrick(GamePanel gp, int col, int row) {
        super(gp,col,row);

        name="Weak Brick";

        worldX=gp.tileSize*col;
        worldY=gp.tileSize*row;

        down1=setImage("/interactive_tiles/weak_brick",gp.tileSize,gp.tileSize);
        canChange=true;
        destructible=true;
        collisionOn=true;

        hp=3;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        return entity.currentFireball.name.equals("Black Fireball");
    }

    @Override
    public void attack(int i) {
        gp.iTiles[gp.currentMap][i]=null;
    }

    @Override
    public void generateITileParticle(int i) {
        generateParticle(this,this);
    }

    @Override
    public Color getParticleColor(){
        return new Color(65,65,65);
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
