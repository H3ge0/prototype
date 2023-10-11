package monster;

import entity.Entity;
import main.GamePanel;

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
    }

    @Override
    public void update(){
        setAction();
        collision=false;
        gp.collisionH.checkTile(this);
        gp.collisionH.checkObject(this,false);
        gp.collisionH.checkEntity(this,gp.npcs);
        gp.collisionH.checkEntity(this,gp.monsters);
        boolean contactPlayer = gp.collisionH.checkPlayer(this);

        if (type==typeMonster && contactPlayer){
            if(gp.player.hp>0 && !gp.player.invincible && !dying){
                int damage = attack-gp.player.defense;
                gp.playSoundEffect(7);
                if(damage>0){
                    gp.player.hp-=damage;
                } else {
                    gp.player.hp--;
                }
                gp.player.invincible=true;
            }
        }

        if(!collision){
            switch(direction){
                case "up" -> worldY-=speed;
                case "down" -> worldY+=speed;
                case "left" -> worldX-=speed;
                case "right" -> worldX+=speed;
            }
        }

        spriteCounter++;
        if (spriteCounter>5){
            if (spriteNum==16){
                spriteNum=1;
            } else {
                spriteNum++;
            }
            spriteCounter=0;
        }

        if(invincible){
            invincibleCounter++;
            if (invincibleCounter>35){
                invincible=false;
                invincibleCounter=0;
            }
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize) {

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

            if(type==typeMonster && hpBarOn){
                double oneScale = (double)gp.tileSize/maxHp;
                double hpBarWidth = oneScale*hp;

                g2.setColor(new Color(135,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)hpBarWidth,10);

                hpBarCounter++;
                if(hpBarCounter>=600){
                    hpBarCounter=0;
                    hpBarOn=false;
                }
            }

            if (invincible){
                hpBarOn=true;
                hpBarCounter=0;
                if (invincibleCounter<15){
                    setG2Alpha(g2, 0.5f);
                }else if (invincibleCounter<30){
                    setG2Alpha(g2, 0.9f);
                }else if (invincibleCounter<45){
                    setG2Alpha(g2, 0.5f);
                }else if (invincibleCounter<60){
                    setG2Alpha(g2, 0.9f);
                }else if (invincibleCounter<75){
                    setG2Alpha(g2, 0.5f);
                }else if (invincibleCounter<90){
                    setG2Alpha(g2, 0.5f);
                }
            }

            if(dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
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
