package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Obob extends Entity {

    public static final String monName = "Obob";
    int radius = gp.TILE_SIZE *4;
    double angle = 0;

    public Obob(GamePanel gp) {
        super(gp);

        connectedEntity = gp.monsters[3][0];

        collisionBox.x=4;
        collisionBox.y=4;
        collisionBox.width=88;
        collisionBox.height=88;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;

        type = typeMonster;
        boss=true;
        bossType=gen2;
        name = monName;
        displayedName = name;
        defaultSpeed = 2;
        speed = defaultSpeed;
        direction="any";
        maxHp = 30;
        hp = maxHp;
        attack = 10;
        defense = 3;
        exp = 20;
        knockBackPower = 5;
        sleep=true;

        resistant = true;

        getImage();
    }

    public void getImage(){
        down1 = setImage("/monsters/boss/boss_part1",gp.TILE_SIZE *2,gp.TILE_SIZE *2);
    }

    @Override
    public BufferedImage setDrawImage() {
        return down1;
    }

    @Override
    public void update() {
        if(!sleep){
            collision=false;
            boolean contactPlayer = gp.collisionHandler.checkPlayer(this);

            if (type==typeMonster && contactPlayer){
                attackPlayer(attack);
            }

            connectedEntity = gp.monsters[3][0];

            if(connectedEntity==null){
                connectedEntity = gp.player;
            }

            speed=defaultSpeed;

            increaseAngleAndMove();

            worldX += connectedEntity.getCenterX()-gp.TILE_SIZE *3/2;
            worldY += connectedEntity.getCenterY()-gp.TILE_SIZE *3/2;

            if(invincible){
                invincibleCounter++;
                if (invincibleCounter>35){
                    invincible=false;
                    invincibleCounter=0;
                }
            }

            if(weak){
                weak=false;
            }

            if(angle>Math.toRadians(360)){
                angle=0;
            }
        }else{
            if(!connectedEntity.sleep){
                sleep=false;
            }
        }
    }

    public void increaseAngleAndMove(){
        angle += Math.toRadians(speed);

        worldX = (int)(radius*Math.cos(angle));
        worldY = (int)(radius*Math.sin(angle));
    }

    @Override
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 4;

        if(dyingCounter<=i) setG2Alpha(g2, 0.9f);
        else if(dyingCounter<=i*2) setG2Alpha(g2, 0.8f);
        else if(dyingCounter<=i*3) setG2Alpha(g2, 0.7f);
        else if(dyingCounter<=i*4) setG2Alpha(g2, 0.6f);
        else if(dyingCounter<=i*5) setG2Alpha(g2, 0.5f);
        else if(dyingCounter<=i*6) setG2Alpha(g2, 0.4f);
        else if(dyingCounter<=i*7) setG2Alpha(g2, 0.3f);
        else if(dyingCounter<=i*8) setG2Alpha(g2, 0.2f);
        else if(dyingCounter<=i*9) setG2Alpha(g2, 0.1f);
        else {
            alive=false;
            connectedEntity.resistant=false;
            connectedEntity.reGetImages();
            connectedEntity.reGetAttackImages();
            connectedEntity.defaultSpeed++;
            connectedEntity.speed=connectedEntity.defaultSpeed;
            connectedEntity.attack *= 2;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter=0;
    }

    @Override
    public void checkDrop() {
        dropItem(new HealthPotion(gp));
    }
}
