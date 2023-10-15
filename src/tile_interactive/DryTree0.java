package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class DryTree0 extends InteractiveTile{
    public DryTree0(GamePanel gp,int col,int row) {
        super(gp,col,row);

        name="Dry Tree";

        worldX=gp.tileSize*col;
        worldY=gp.tileSize*row;

        down1=setImage("/interactive_tiles/dry_tree0",gp.tileSize,gp.tileSize);
        canChange=true;
        collisionOn=true;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        return entity.currentFireball.name.equals("Red Fireball");
    }

    @Override
    public void attack(int i) {
        gp.iTiles[i]=new BurntTree(gp,worldX/gp.tileSize,worldY/gp.tileSize);
    }

    @Override
    public void generateITileParticle(int i) {
        generateParticle(this,this);
    }

    @Override
    public Color getParticleColor(){
        return new Color(55,40,20);
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
