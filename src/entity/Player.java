package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOError;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        setValues();
        getPlayerImage();
    }

    public void setValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){

        try {
            upidle = ImageIO.read(getClass().getResourceAsStream("/player/gopi_up_idle.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_up_2.png"));
            downidle = ImageIO.read(getClass().getResourceAsStream("/player/gopi_down_idle.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_down_2.png"));
            leftidle = ImageIO.read(getClass().getResourceAsStream("/player/gopi_left_idle.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_left_2.png"));
            rightidle = ImageIO.read(getClass().getResourceAsStream("/player/gopi_right_idle.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/gopi_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void update(){

        keyH.keyPressed=keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed;

        if(keyH.keyPressed){
            if(keyH.upPressed){
                direction="up";
                y-=speed;
            }else if(keyH.downPressed){
                direction="down";
                y+=speed;
            }
            if(keyH.leftPressed){
                direction="left";
                x-=speed;
            }else if(keyH.rightPressed){
                direction="right";
                x+=speed;
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

        g2.drawImage(image,x,y,gp.tileSize,gp.tileSize, null);
    }

}
