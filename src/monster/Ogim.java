package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Ogim extends Entity {

    public Ogim(GamePanel gp) {
        super(gp);

        collisionBox.x=3;
        collisionBox.y=3;
        collisionBox.width=42;
        collisionBox.height=42;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;

        type = typeMonster;
        name = "Ogim";
        displayedName = name;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxHp = 4;
        hp = maxHp;
        attack = 5;
        defense = 0;
        exp = 2;
        currentProjectile=new OgimRock(gp);

        getImage();
        spriteNum = new Random().nextInt(1,17);
        actionLockCounter = new Random().nextInt(1,120);
    }

    public void getImage(){
        upidle = setImage("/monsters/ogim/ogim_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up1 = setImage("/monsters/ogim/ogim_2",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setImage("/monsters/ogim/ogim_3",gp.TILE_SIZE,gp.TILE_SIZE);
        downidle = setImage("/monsters/ogim/ogim_4",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setImage("/monsters/ogim/ogim_5",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setImage("/monsters/ogim/ogim_6",gp.TILE_SIZE,gp.TILE_SIZE);
        leftidle = setImage("/monsters/ogim/ogim_7",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setImage("/monsters/ogim/ogim_8",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setImage("/monsters/ogim/ogim_9",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    @Override
    public void setAction() {
        //If onPath
        if(onPath){
            checkAndStopAggro(gp.player,15,100);

            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

            checkAndShootProjectile(200,60);
        }

        //If not onPath
        else{
            checkAndStartAggro(gp.player,5,100);

            pickARandomDirection(120);
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
        onPath=true;
    }
}
