package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class Apol extends Entity {

    public Apol(GamePanel gp) {
        super(gp);

        collisionBox.x=4;
        collisionBox.y=4;
        collisionBox.width=40;
        collisionBox.height=44;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;
        attackArea.width = 48;
        attackArea.height = 48;

        type = typeMonster;
        name = "Apol";
        displayedName = name;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxHp = 10;
        hp = maxHp;
        attack = 8;
        defense = 2;
        exp = 10;
        knockBackPower = 5;

        getImage();
        getAttackImage();
        actionLockCounter = new Random().nextInt(1,120);
    }

    public void getImage(){
        upidle = setImage("/monsters/apol/apol_up_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        up1 = setImage("/monsters/apol/apol_up_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setImage("/monsters/apol/apol_up_2",gp.TILE_SIZE,gp.TILE_SIZE);
        downidle = setImage("/monsters/apol/apol_down_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setImage("/monsters/apol/apol_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setImage("/monsters/apol/apol_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        leftidle = setImage("/monsters/apol/apol_left_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setImage("/monsters/apol/apol_left_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setImage("/monsters/apol/apol_left_2",gp.TILE_SIZE,gp.TILE_SIZE);
        rightidle = setImage("/monsters/apol/apol_right_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setImage("/monsters/apol/apol_right_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setImage("/monsters/apol/apol_right_2",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    public void getAttackImage(){
        attackUp1 = setImage("/monsters/apol/apol_attack_up_1",gp.TILE_SIZE,gp.TILE_SIZE *2);
        attackUp2 = setImage("/monsters/apol/apol_attack_up_2",gp.TILE_SIZE,gp.TILE_SIZE *2);
        attackUp3 = setImage("/monsters/apol/apol_attack_up_2",gp.TILE_SIZE,gp.TILE_SIZE *2);
        attackDown1 = setImage("/monsters/apol/apol_attack_down_1",gp.TILE_SIZE,gp.TILE_SIZE *2);
        attackDown2 = setImage("/monsters/apol/apol_attack_down_2",gp.TILE_SIZE,gp.TILE_SIZE *2);
        attackDown3 = setImage("/monsters/apol/apol_attack_down_2",gp.TILE_SIZE,gp.TILE_SIZE *2);
        attackLeft1 = setImage("/monsters/apol/apol_attack_left_1",gp.TILE_SIZE *2,gp.TILE_SIZE);
        attackLeft2 = setImage("/monsters/apol/apol_attack_left_2",gp.TILE_SIZE *2,gp.TILE_SIZE);
        attackLeft3 = setImage("/monsters/apol/apol_attack_left_2",gp.TILE_SIZE *2,gp.TILE_SIZE);
        attackRight1 = setImage("/monsters/apol/apol_attack_right_1",gp.TILE_SIZE *2,gp.TILE_SIZE);
        attackRight2 = setImage("/monsters/apol/apol_attack_right_2",gp.TILE_SIZE *2,gp.TILE_SIZE);
        attackRight3 = setImage("/monsters/apol/apol_attack_right_2",gp.TILE_SIZE *2,gp.TILE_SIZE);
    }

    @Override
    public void attackUpdate() {
        spriteCounter++;

        //Attacking
        if(spriteCounter>40 && spriteCounter<=85){
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

            if(type == typeMonster){
                if(gp.collisionHandler.checkPlayer(this)){
                    attackPlayer(attack);
                }
            }else{
                int monsterIndex = gp.collisionHandler.checkEntity(this,gp.monsters);
                gp.player.attackMonster(monsterIndex,this,attack,currentFireball.knockBackPower);

                int iTileIndex = gp.collisionHandler.checkEntity(this,gp.interactiveTiles);
                gp.player.attackInteractiveTile(iTileIndex);

                int projectileIndex = gp.collisionHandler.checkEntity(this,gp.projectiles);
                gp.player.attackProjectile(projectileIndex);
            }

            worldX=currentWorldX;
            worldY=currentWorldY;
            collisionBox.width=collisionBoxWidth;
            collisionBox.height=collisionBoxHeight;
        }

        //SFX
        if(spriteCounter==3){
            gp.playSoundEffect(16);
        }
        if(spriteCounter==40){
            gp.playSoundEffect(17);
        }

        //Sprite
        if(spriteCounter<=40){
            spriteNum = 1;
        }
        if(spriteCounter>40 && spriteCounter<=85){
            spriteNum = 2;
        }
        if(spriteCounter>85){
            attacking=false;
            spriteCounter=0;
        }
    }

    @Override
    public void setAction() {
        //If onPath
        if(onPath){
            checkAndStopAggro(gp.player,15,100);

            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        }

        //If not onPath
        else{
            checkAndStartAggro(gp.player,5,100);

            pickARandomDirection(120);
        }

        //Attack
        if(!attacking){
            checkAndAttack(30,gp.TILE_SIZE *4,gp.TILE_SIZE);
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter=0;
        onPath=true;
    }

    @Override
    public void checkDrop() {
        int i=new Random().nextInt(100)+1;

        if(i<=10){
            boolean hasPurpleFireball=false;
            for(Entity e:gp.player.inventory){
                if(e!=null){
                    if (e.name.equals("Purple Fireball")){
                        hasPurpleFireball=true;
                        break;
                    }
                }
            }
            if(!hasPurpleFireball)
                dropItem(new FireballPurple(gp));
            else
                dropItem(new SilverCoin(gp));
        }else if(i<=25){
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
                dropItem(new SilverCoin(gp));
        }else {
            if(i<=85){
                dropItem(new SilverCoin(gp));
            }else{
                dropItem(new SleepPotion(gp));
            }

        }
    }
}
