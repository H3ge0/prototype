package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage upidle,up1,up2,downidle,down1,down2,leftidle,left1,left2,rightidle,right1,right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    public int collisionBoxDefaultX, collisionBoxDefaultY;
    public boolean collision = false;
    public int actionLockCounter = 0;
    String[] dialogues = new String[20];
    int dialogueIndex=0;

    //Character
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
        gp.collisionH.checkPlayer(this);
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

            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
        }
    }

    public BufferedImage setImage(String path){
        UtilityTool utility = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path+".png")));
            image = utility.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
