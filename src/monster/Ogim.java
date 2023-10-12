package monster;

import entity.Entity;
import main.GamePanel;
import object.Rock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ogim extends Entity {

    Random random;

    public Ogim(GamePanel gp) {
        super(gp);
        random = new Random();

        collisionBox.x=3;
        collisionBox.y=3;
        collisionBox.width=gp.tileSize-6;
        collisionBox.height=gp.tileSize-6;

        type = typeMonster;
        name = "Ogim";
        speed = 1;
        maxHp = 3;
        hp = maxHp;
        attack = 3;
        defense = 0;
        exp = 2;
        currentProjectile=new Rock(gp);

        getImage();
        spriteNum = random.nextInt(1,17);
        actionLockCounter = random.nextInt(1,120);
    }

    public void getImage(){
        upidle = setImage("/monsters/ogim_1",gp.tileSize,gp.tileSize);
        up1 = setImage("/monsters/ogim_2",gp.tileSize,gp.tileSize);
        up2 = setImage("/monsters/ogim_3",gp.tileSize,gp.tileSize);
        downidle = setImage("/monsters/ogim_4",gp.tileSize,gp.tileSize);
        down1 = setImage("/monsters/ogim_5",gp.tileSize,gp.tileSize);
        down2 = setImage("/monsters/ogim_6",gp.tileSize,gp.tileSize);
        leftidle = setImage("/monsters/ogim_7",gp.tileSize,gp.tileSize);
        left1 = setImage("/monsters/ogim_8",gp.tileSize,gp.tileSize);
        left2 = setImage("/monsters/ogim_9",gp.tileSize,gp.tileSize);
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter==120){
            int i = random.nextInt(100)+1;

            if(i<=25){
                direction="up";
            } else if (i<=50){
                direction="down";
            } else if (i<=75){
                direction="left";
            } else {
                direction="right";
            }
            actionLockCounter=0;
        }

        int i = random.nextInt(100)+1;
        if(i==99 && !currentProjectile.alive && projectileCooldownCounter==60){
            currentProjectile.setProjectile(worldX,worldY,direction,true,this);
            gp.projectiles.add(currentProjectile);
            projectileCooldownCounter=0;
        }
    }

    @Override
    public void increaseSpriteCounter() {
        spriteCounter++;
        if (spriteCounter>5){
            if (spriteNum==16){
                spriteNum=1;
            } else {
                spriteNum++;
            }
            spriteCounter=0;
        }
    }

    @Override
     public BufferedImage setDrawImage(){
        BufferedImage image=null;
         switch(spriteNum){
             case 1, 9 -> image = upidle;
             case 2, 8 -> image = up1;
             case 3, 7 -> image = up2;
             case 4, 6 -> image = downidle;
             case 5 -> image = down1;
             case 10, 16 -> image = down2;
             case 11, 15 -> image = leftidle;
             case 12, 14 -> image = left1;
             case 13 -> image = left2;
         }
         return image;
     }

    @Override
    public void damageReaction() {
        actionLockCounter=0;
        switch(gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }
}
