package monster;

import entity.Entity;
import loot.LootHandler;
import main.GamePanel;
import object.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Afag extends Entity {
    public Afag(GamePanel gp) {
        super(gp);

        collisionBox.x=6;
        collisionBox.y=3;
        collisionBox.width=36;
        collisionBox.height=39;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;

        type = typeMonster;
        name = "Afag";
        displayedName = name;
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxHp = 7;
        hp = maxHp;
        attack = 7;
        defense = 0;
        exp = 7;

        getImage();
        spriteNum = new Random().nextInt(1,2);
        actionLockCounter = new Random().nextInt(1,10);
    }

    public void getImage(){
        down1 = setImage("/monsters/afag/afag_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setImage("/monsters/afag/afag_2",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    @Override
    public void setAction() {
        if (!onPath) {
            pickARandomDirection(10);
        }
    }

    @Override
    public void increaseSpriteCounter() {
        spriteCounter++;
        if (spriteCounter>20){
            if (spriteNum==2){
                spriteNum=1;
            } else {
                spriteNum=2;
            }
            spriteCounter=0;
        }
    }

    @Override
    public BufferedImage setDrawImage(){
        BufferedImage image=null;
        switch(spriteNum){
            case 1 -> image = down1;
            case 2 -> image = down2;
        }
        return image;
    }

    @Override
    public void damageReaction() {
        actionLockCounter=0;
    }
}
