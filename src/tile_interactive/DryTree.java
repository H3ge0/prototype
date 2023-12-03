package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class DryTree extends InteractiveTile{
    public DryTree(GamePanel gp, int col, int row) {
        super(gp,col,row);

        name="Dry Tree";

        worldX=gp.TILE_SIZE *col;
        worldY=gp.TILE_SIZE *row;

        down1=setImage("/interactive_tiles/dry_tree",gp.TILE_SIZE,gp.TILE_SIZE);
        canChange=true;
        destructible=true;
        collisionOn=true;

        hp=1;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        return entity.currentFireball.name.equals("Red Fireball");
    }

    @Override
    public void attack(int i) {
        gp.interactiveTiles[gp.currentMapNum][i]=new BurntTree(gp,worldX/gp.TILE_SIZE,worldY/gp.TILE_SIZE);
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
