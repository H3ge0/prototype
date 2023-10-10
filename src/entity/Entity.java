package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    public GamePanel gp;
    public BufferedImage upidle,up1,up2,downidle,down1,down2,leftidle,left1,left2,rightidle,right1,right2;
    public BufferedImage attackUp1,attackUp2,attackUp3,attackDown1,attackDown2,attackDown3,attackLeft1,attackLeft2,attackLeft3,attackRight1,attackRight2,attackRight3;
    public BufferedImage image1,image2,image3;
    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int collisionBoxDefaultX=collisionBox.x, collisionBoxDefaultY=collisionBox.y;
    public boolean collisionOn = false;
    String[] dialogues = new String[20];

    //State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex=0;
    public boolean collision = false;
    public boolean invincible = false;
    boolean attacking = false;

    //Counters
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;

    //Character
    public int type; // 0->player   1->npc   2->monster   3->obj
    public String name;
    public int speed;
    public int maxHp;
    public int hp;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){}

    public void speak(){
        if(dialogues[dialogueIndex]==null)
            dialogueIndex--;
        gp.uiH.currentDialogueText = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void update(){
        setAction();
        collision=false;
        gp.collisionH.checkTile(this);
        gp.collisionH.checkObject(this,false);
        gp.collisionH.checkEntity(this,gp.npcs);
        gp.collisionH.checkEntity(this,gp.monsters);
        boolean contactPlayer = gp.collisionH.checkPlayer(this);

        if (this.type==2 && contactPlayer){
            if(gp.player.hp>0 && !gp.player.invincible){
                gp.player.hp--;
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
        if (spriteCounter>10){
            if (spriteNum==4){
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

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize){

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

            if (invincible){
                if (invincibleCounter<15){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }else if (invincibleCounter<30){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
                }else if (invincibleCounter<45){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }else if (invincibleCounter<60){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
                }else if (invincibleCounter<75){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }else if (invincibleCounter<90){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }
            }

            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public BufferedImage setImage(String path,int width,int height){
        UtilityTool utility = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path+".png")));
            image = utility.scaleImage(image,width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
