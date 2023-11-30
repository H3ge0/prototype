package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.image.BufferedImage;
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

    @Override
    public void checkDrop() {
        int i=new Random().nextInt(100)+1;

        if(i<=15){
            boolean hasRedFireball=false;
            for(Entity e:gp.player.inventory){
                if(e!=null){
                    if (e.name.equals("Red Fireball")){
                        hasRedFireball=true;
                        break;
                    }
                }
            }
            if(!hasRedFireball)
                dropItem(new FireballRed(gp));
            else
                dropItem(new BronzeCoin(gp));
        }else if(i<=30){
            boolean hasIronArmor=false;
            for(Entity e:gp.player.inventory){
                if(e!=null){
                    if (e.name.equals("Iron Armor")){
                        hasIronArmor=true;
                        break;
                    }
                }
            }
            if(!hasIronArmor)
                dropItem(new ArmorIron(gp));
            else
                dropItem(new BronzeCoin(gp));
        }else {

            //Full hp and full energy
            if (gp.player.hp == gp.player.maxHp && gp.player.energy == gp.player.maxEnergy) {
                dropItem(new BronzeCoin(gp));
            }

            //Only full hp
            else if(gp.player.hp == gp.player.maxHp){
                if(i<=70){
                    dropItem(new BronzeCoin(gp));
                } else{
                    dropItem(new Energy(gp));
                }
            }

            //Only full energy
            else if(gp.player.energy == gp.player.maxEnergy){
                if(i<=70){
                    dropItem(new BronzeCoin(gp));
                } else{
                    dropItem(new Heart(gp));
                }
            }

            //None of them are full
            else{
                if(i<=70){
                    dropItem(new BronzeCoin(gp));
                }else if(i<=85){
                    dropItem(new Heart(gp));
                }else{
                    dropItem(new Energy(gp));
                }
            }

        }
    }
}
