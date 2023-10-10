package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity{

    public boolean canDrink=true, canFall=true;
    KeyHandler keyH;
    Random random;
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

        attackArea.width=36;
        attackArea.height=36;

        setValues();
        getImages();
        getAttackImages();
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
        upidle = setImage("/player/gopi_up_idle",gp.tileSize,gp.tileSize);
        up1 = setImage("/player/gopi_up_1",gp.tileSize,gp.tileSize);
        up2 = setImage("/player/gopi_up_2",gp.tileSize,gp.tileSize);
        downidle = setImage("/player/gopi_down_idle",gp.tileSize,gp.tileSize);
        down1 = setImage("/player/gopi_down_1",gp.tileSize,gp.tileSize);
        down2 = setImage("/player/gopi_down_2",gp.tileSize,gp.tileSize);
        leftidle = setImage("/player/gopi_left_idle",gp.tileSize,gp.tileSize);
        left1 = setImage("/player/gopi_left_1",gp.tileSize,gp.tileSize);
        left2 = setImage("/player/gopi_left_2",gp.tileSize,gp.tileSize);
        rightidle = setImage("/player/gopi_right_idle",gp.tileSize,gp.tileSize);
        right1 = setImage("/player/gopi_right_1",gp.tileSize,gp.tileSize);
        right2 = setImage("/player/gopi_right_2",gp.tileSize,gp.tileSize);
    }

    public void getAttackImages(){
        attackUp1 = setImage("/player/gopi_attack_up_1",gp.tileSize,gp.tileSize*2);
        attackUp2 = setImage("/player/gopi_attack_up_2",gp.tileSize,gp.tileSize*2);
        attackUp3 = setImage("/player/gopi_attack_up_3",gp.tileSize,gp.tileSize*2);
        attackDown1 = setImage("/player/gopi_attack_down_1",gp.tileSize,gp.tileSize*2);
        attackDown2 = setImage("/player/gopi_attack_down_2",gp.tileSize,gp.tileSize*2);
        attackDown3 = setImage("/player/gopi_attack_down_3",gp.tileSize,gp.tileSize*2);
        attackLeft1 = setImage("/player/gopi_attack_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setImage("/player/gopi_attack_left_2",gp.tileSize*2,gp.tileSize);
        attackLeft3 = setImage("/player/gopi_attack_left_3",gp.tileSize*2,gp.tileSize);
        attackRight1 = setImage("/player/gopi_attack_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setImage("/player/gopi_attack_right_2",gp.tileSize*2,gp.tileSize);
        attackRight3 = setImage("/player/gopi_attack_right_3",gp.tileSize*2,gp.tileSize);
    }

    public void update() {

        keyH.keyPressed = keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed||keyH.xKeyPressed||keyH.zKeyPressed;

        if(attacking){
            attackUpdate();
        }else if(keyH.keyPressed){

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

            if(keyH.zKeyPressed){
                attacking=true;
            }

            if(!collision&&!keyH.xKeyPressed&&!keyH.zKeyPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            keyH.xKeyPressed=false;
            keyH.zKeyPressed=false;

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

    public void attackUpdate(){
        spriteCounter++;

        //Attacking
        if(spriteCounter>5 && spriteCounter<=25){
            int currentWorldX=worldX;
            int currentWorldY=worldY;
            int collisionBoxWidth = collisionBox.width;
            int collisionBoxHeight = collisionBox.height;

            switch(direction){
                case "up" -> worldY-=attackArea.height;
                case "down" -> worldY+=attackArea.height;
                case "left" -> worldX-=attackArea.width;
                case "right" -> worldX+=attackArea.width;
            }

            collisionBox.width = attackArea.width;
            collisionBox.height = attackArea.height;

            int monsterIndex = gp.collisionH.checkEntity(this,gp.monsters);
            attackMonster(monsterIndex);

            worldX=currentWorldX;
            worldY=currentWorldY;
            collisionBox.width=collisionBoxWidth;
            collisionBox.height=collisionBoxHeight;
        }

        //Sprite
        if(spriteCounter<=5){
            spriteNum = 1;
        }
        if(spriteCounter>5 && spriteCounter<=12){
            spriteNum = 2;
        }
        if (spriteCounter>12 && spriteCounter<=25) {
            spriteNum = 3;
        }
        if(spriteCounter>25){
            attacking=false;
            spriteCounter=0;
        }
    }

    public void interactWithObj(int index){
        if(index!=999){

        }
    }

    public void interactWithNPC(int index){
        if(keyH.xKeyPressed){
            if(index!=999){
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

    public void attackMonster(int index){
        if(index!=999){
            if(gp.monsters[index].hp>0 && !gp.monsters[index].invincible){
                gp.monsters[index].hp--;
                gp.monsters[index].invincible=true;
            } else if (gp.monsters[index].hp<=0){
                gp.monsters[index] = null;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX=screenX;
        int tempScreenY=screenY;

        if (attacking){
            switch (direction) {
                case "up" -> {
                    tempScreenY-=gp.tileSize;
                    if (spriteNum == 1)
                        image = attackUp1;
                    if (spriteNum == 2)
                        image = attackUp2;
                    if (spriteNum == 3)
                        image = attackUp3;
                }
                case "down" -> {
                    if (spriteNum == 1)
                        image = attackDown1;
                    if (spriteNum == 2)
                        image = attackDown2;
                    if (spriteNum == 3)
                        image = attackDown3;
                }
                case "left" -> {
                    tempScreenX-=gp.tileSize;
                    if (spriteNum == 1)
                        image = attackLeft1;
                    if (spriteNum == 2)
                        image = attackLeft2;
                    if (spriteNum == 3)
                        image = attackLeft3;
                }
                case "right" -> {
                    if (spriteNum == 1)
                        image = attackRight1;
                    if (spriteNum == 2)
                        image = attackRight2;
                    if (spriteNum == 3)
                        image = attackRight3;
                }
            }
        } else if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed){
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

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}
