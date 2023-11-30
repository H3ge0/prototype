package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity target;
    Color color;
    int size,xd, yd;

    public Particle(GamePanel gp, Entity target, Color color, int size, int speed, int maxHp, int xd, int yd) {
        super(gp);

        this.target=target;
        this.color=color;
        this.size=size;
        this.speed=speed;
        this.maxHp=maxHp;
        this.xd=xd;
        this.yd=yd;

        hp=maxHp;
        int offset = gp.TILE_SIZE /2 - size/2;
        worldX=target.worldX+offset;
        worldY=target.worldY+offset;
    }

    @Override
    public void update(){
        hp--;

        if(hp<maxHp/3){
            yd++;
        }

        worldX+=xd*speed;
        worldY+=yd*speed;

        if(hp==0){
            alive=false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX-gp.player.worldX+gp.player.screenX;
        int screenY = worldY-gp.player.worldY+gp.player.screenY;
        g2.setColor(color);

        g2.fillRect(screenX,screenY,size,size);
    }
}
