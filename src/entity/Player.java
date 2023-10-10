package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends Entity{

    public boolean canDrink=true, canFall=true;
    KeyHandler keyH;
    Random random;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    int idleSoundCounter=0;

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH=keyH;

        random = new Random();

        screenX = gp.screenWidth/2-gp.tileSize/2;
        screenY = gp.screenHeight/2-gp.tileSize/2;

        collisionBox = new Rectangle(3*gp.scale,7*gp.scale,10*gp.scale,8*gp.scale);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;

        setValues();
        getImages();
    }

    public void setValues() {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
        maxHp = 6;
        hp = 6;
    }

    public void getImages(){

        upidle = setImage("/player/gopi_up_idle");
        up1 = setImage("/player/gopi_up_1");
        up2 = setImage("/player/gopi_up_2");
        downidle = setImage("/player/gopi_down_idle");
        down1 = setImage("/player/gopi_down_1");
        down2 = setImage("/player/gopi_down_2");
        leftidle = setImage("/player/gopi_left_idle");
        left1 = setImage("/player/gopi_left_1");
        left2 = setImage("/player/gopi_left_2");
        rightidle = setImage("/player/gopi_right_idle");
        right1 = setImage("/player/gopi_right_1");
        right2 = setImage("/player/gopi_right_2");

    }

    public void update() {

        if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed||keyH.xKeyPressed){

            if (keyH.upPressed){
                direction="up";
            }else if (keyH.downPressed){
                direction="down";
            }else if (keyH.leftPressed){
                direction="left";
            }else if (keyH.rightPressed){
                direction="right";
            }

            collision = false;
            gp.collisionH.checkTile(this);

            int objIndex = gp.collisionH.checkObject(this,true);
            interactWithObj(objIndex);

            int npcIndex = gp.collisionH.checkEntity(this,gp.npcs);
            interactWithNPC(npcIndex);

            int monsterIndex = gp.collisionH.checkEntity(this,gp.monsters);
            interactWithMonster(monsterIndex);

            gp.eventH.checkEvent();

            if(!collision&&!keyH.xKeyPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            keyH.xKeyPressed =false;

            spriteCounter++;
            if (spriteCounter>10){
                if (spriteNum==4){
                    spriteNum=1;
                } else {
                    spriteNum++;
                }
                spriteCounter=0;
            }
        }

        if(invincible){
            invincibleCounter++;
            if (invincibleCounter>90){
                invincible=false;
                invincibleCounter=0;
            }
        }

    }

    public void interactWithObj(int index){
        if(index!=999){

        }
    }

    public void interactWithNPC(int index){
        if(index!=999){
            if(gp.keyH.xKeyPressed){
                gp.gameState=gp.dialogueState;
                gp.npcs[index].speak();
            }
        }
    }

    public void interactWithMonster(int index){
        if(index!=999){
            if(hp>0 && !invincible){
                hp--;
                invincible=true;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed){
            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    if (spriteNum == 3)
                        image = up1;
                    if (spriteNum == 4)
                        image = up2;
                }
                case "down" -> {
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    if (spriteNum == 3)
                        image = down1;
                    if (spriteNum == 4)
                        image = down2;
                }
                case "left" -> {
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = leftidle;
                    if (spriteNum == 3)
                        image = left2;
                    if (spriteNum == 4)
                        image = leftidle;
                }
                case "right" -> {
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = rightidle;
                    if (spriteNum == 3)
                        image = right2;
                    if (spriteNum == 4)
                        image = rightidle;
                }
            }
        } else {
            switch (direction) {
                case "up" -> image = upidle;
                case "down" -> image = downidle;
                case "left" -> image = leftidle;
                case "right" -> image = rightidle;
            }
            spriteNum=1;
            spriteCounter=0;
        }

        if (invincible){
            if (invincibleCounter<15){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }else if (invincibleCounter<30){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            }else if (invincibleCounter<45){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }else if (invincibleCounter<60){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            }else if (invincibleCounter<75){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }else if (invincibleCounter<90){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
        }

        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}
