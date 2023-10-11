package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
        name="prj";
    }

    public void setProjectile(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX=worldX;
        this.worldY=worldY;
        this.direction=direction;
        this.alive=alive;
        this.user=user;
        this.hp=this.maxHp;
    }

    @Override
    public void update(){
        if(user==gp.player){
            int monsterIndex = gp.collisionH.checkEntity(this,gp.monsters);
            if(monsterIndex!=999){
                gp.player.attackMonster(monsterIndex,attack);
                alive=false;
            }
        } else{
            gp.collisionH.checkPlayer(user);
        }

        switch (direction){
            case "up" -> worldY-=speed;
            case "down" -> worldY+=speed;
            case "left" -> worldX-=speed;
            case "right" -> worldX+=speed;
        }

        hp--;
        if(hp<=0){
            alive=false;
        }

        spriteCounter++;
        if (spriteCounter>2){
            if (spriteNum==24){
                spriteNum=1;
            } else {
                spriteNum++;
            }
            spriteCounter=0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize) {

            switch(spriteNum){
                case 1 -> image = upidle;
                case 2 -> image = up1;
                case 3 -> image = up2;
                case 4 -> image = downidle;
                case 5 -> image = down1;
                case 6 -> image = down2;
                case 7 -> image = leftidle;
                case 8 -> image = left1;
                case 9 -> image = left2;
                case 10 -> image = rightidle;
                case 11 -> image = right1;
                case 12 -> image = right2;
                case 13 -> image = upidlev2;
                case 14 -> image = up1v2;
                case 15 -> image = up2v2;
                case 16 -> image = downidlev2;
                case 17 -> image = down1v2;
                case 18 -> image = down2v2;
                case 19 -> image = leftidlev2;
                case 20 -> image = left1v2;
                case 21 -> image = left2v2;
                case 22 -> image = rightidlev2;
                case 23 -> image = right1v2;
                case 24 -> image = right2v2;
            }

            if(dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image,screenX+8,screenY+8,null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }

}
