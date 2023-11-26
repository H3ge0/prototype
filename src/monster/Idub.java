package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Idub extends Entity {

    public static final String monName = "Idub";
    int radius = gp.tileSize*4;
    double angle = 0;

    public Idub(GamePanel gp) {
        super(gp);

        connectedEntity = gp.monsters[3][1];

        collisionBox.x=4;
        collisionBox.y=4;
        collisionBox.width=88;
        collisionBox.height=88;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;

        type = typeMonster;
        boss=true;
        bossType=gen3;
        name = monName;
        displayedName = "İdub";
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction="any";
        maxHp = 30;
        hp = maxHp;
        attack = 10;
        defense = 3;
        exp = 20;
        knockBackPower = 5;
        sleep=true;

        resistant = false;

        getImage();
    }

    public void getImage(){
        down1 = setImage("/monsters/boss/boss_part2",gp.tileSize*2,gp.tileSize*2);
    }

    @Override
    public BufferedImage setDrawImage() {
        return down1;
    }

    @Override
    public void update() {
        if(!sleep){
            collision=false;
            boolean contactPlayer = gp.collisionH.checkPlayer(this);

            if (type==typeMonster && contactPlayer){
                attackPlayer(attack);
            }

            if(connectedEntity==null){
                connectedEntity = gp.player;
            }

            speed=defaultSpeed;

            increaseAngleAndMove();

            worldX += connectedEntity.getCenterX()-gp.tileSize*3/2;
            worldY += connectedEntity.getCenterY()-gp.tileSize*3/2;

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
            connectedEntity.defaultSpeed=4;
            connectedEntity.speed=connectedEntity.defaultSpeed;
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
