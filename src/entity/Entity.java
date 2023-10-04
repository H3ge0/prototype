package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage upidle,up1,up2,downidle,down1,down2,leftidle,left1,left2,rightidle,right1,right2;
    public String direction;
    public String directionX;
    public String directionY;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionBox;
    public boolean collision = false;

}
