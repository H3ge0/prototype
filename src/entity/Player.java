package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    Random random;
    int idleSoundCounter=0;

    public final int screenX, screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        random = new Random();

        screenX = gp.screenWidth/2-gp.tileSize/2;
        screenY = gp.screenHeight/2-gp.tileSize/2;

        collisionBox = new Rectangle(3*gp.scale,7*gp.scale,10*gp.scale,8*gp.scale);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;

        setValues();
        getPlayerImage();
    }

    public void setValues() {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
        directionX = "null";
        directionY = "down";
    }

    public void getPlayerImage(){

        upidle = setImage("gopi_up_idle");

        up1 = setImage("gopi_up_1");

        up2 = setImage("gopi_up_2");

        downidle = setImage("gopi_down_idle");

        down1 = setImage("gopi_down_1");

        down2 = setImage("gopi_down_2");

        leftidle = setImage("gopi_left_idle");

        left1 = setImage("gopi_left_1");

        left2 = setImage("gopi_left_2");

        rightidle = setImage("gopi_right_idle");

        right1 = setImage("gopi_right_1");

        right2 = setImage("gopi_right_2");

    }

    public BufferedImage setImage(String name){
        UtilityTool utility = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/"+name+".png")));
            image = utility.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void update() {

        //Idle SFX
        if (idleSoundCounter < 360) {
            idleSoundCounter++;
        } else if(idleSoundCounter ==1000) {
            gp.playSoundEffect(4);
            idleSoundCounter =0;
        } else
            idleSoundCounter = random.nextInt(361,1001);

        //Movement
        if(keyH.upPressed){
            direction="up";
            directionY="up";
            worldY -= speed;
        }else if(keyH.downPressed){
            direction="down";
            directionY="down";
            worldY += speed;
        }
        if(keyH.leftPressed){
            direction="left";
            directionX="left";
            worldX -= speed;
        }else if(keyH.rightPressed){
            direction="right";
            directionX="right";
            worldX += speed;
        }

        keyH.keyPressed=keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed;

        if(keyH.keyPressed){

            collision = false;
            gp.collisionH.checkTile(this);
            int objIndex = gp.collisionH.checkObject(this,true);

            interactWithObj(objIndex);

            if(collision){
                switch (directionX) {
                    case "left" -> {
                        switch (directionY){
                            case "up" -> {
                                worldX+=speed;
                                worldY+=speed;
                            } case "down" -> {
                                worldX+=speed;
                                worldY-=speed;
                            } case "null" -> worldX+=speed;
                        }
                    } case "right" -> {
                        switch (directionY){
                            case "up" -> {
                                worldX-=speed;
                                worldY+=speed;
                            } case "down" -> {
                                worldX-=speed;
                                worldY-=speed;
                            } case "null" -> worldX-=speed;
                        }
                    } case "null" -> {
                        switch (directionY){
                            case "up" -> worldY+=speed;
                            case "down" -> worldY-=speed;
                        }
                    }
                }
            }

            directionX="null";
            directionY="null";

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

    }

    public void interactWithObj(int index){
        if(index!=999){

        }
    }

    public void draw(Graphics2D g2){
        keyH.keyPressed=keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed;
        BufferedImage image = null;

        if(keyH.keyPressed){
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

        g2.drawImage(image, screenX, screenY, null);
    }

}
