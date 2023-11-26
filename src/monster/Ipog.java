package monster;

import data.Progress;
import entity.Bobo;
import entity.Budi;
import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class Ipog extends Entity {

    public static final String monName = "Ipog";

    public Ipog(GamePanel gp) {
        super(gp);

        collisionBox.x=27;
        collisionBox.y=45;
        collisionBox.width=90;
        collisionBox.height=90;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;
        attackArea.width = 96;
        attackArea.height = 96;

        type = typeMonster;
        boss=true;
        bossType=gen1;
        name = monName;
        displayedName = "İpog";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxHp = 50;
        hp = maxHp;
        attack = 8;
        defense = 5;
        exp = 50;
        knockBackPower = 5;
        sleep=true;

        resistant = true;

        setDialogues();
        reGetImages();
        reGetAttackImages();
        actionLockCounter = new Random().nextInt(1,120);
    }

    public void setDialogues(){
        dialogues[0][0] = "Mrblr...";
        dialogues[0][1] = "Ben ipog. (Senin daha iyi versiyonun)";
        dialogues[0][2] = "Bugün en iyi arkadaşlarımla büyük planımızı\ngerçekleştireceğiz.";
        dialogues[0][3] = "Planımız ne mi?..";
        dialogues[0][4] = "BU ADADAKİ GEREKSİZ CANLILARI YOK ETMEK.";
    }

    @Override
    public void reGetImages() {
        int i = 3;

        if(resistant){
            upidle = setImage("/monsters/boss/ipog/ipog_up_idle",gp.tileSize*i,gp.tileSize*i);
            up1 = setImage("/monsters/boss/ipog/ipog_up_1",gp.tileSize*i,gp.tileSize*i);
            up2 = setImage("/monsters/boss/ipog/ipog_up_2",gp.tileSize*i,gp.tileSize*i);
            downidle = setImage("/monsters/boss/ipog/ipog_down_idle",gp.tileSize*i,gp.tileSize*i);
            down1 = setImage("/monsters/boss/ipog/ipog_down_1",gp.tileSize*i,gp.tileSize*i);
            down2 = setImage("/monsters/boss/ipog/ipog_down_2",gp.tileSize*i,gp.tileSize*i);
            leftidle = setImage("/monsters/boss/ipog/ipog_left_idle",gp.tileSize*i,gp.tileSize*i);
            left1 = setImage("/monsters/boss/ipog/ipog_left_1",gp.tileSize*i,gp.tileSize*i);
            left2 = setImage("/monsters/boss/ipog/ipog_left_2",gp.tileSize*i,gp.tileSize*i);
            rightidle = setImage("/monsters/boss/ipog/ipog_right_idle",gp.tileSize*i,gp.tileSize*i);
            right1 = setImage("/monsters/boss/ipog/ipog_right_1",gp.tileSize*i,gp.tileSize*i);
            right2 = setImage("/monsters/boss/ipog/ipog_right_2",gp.tileSize*i,gp.tileSize*i);
        }else {
            upidle = setImage("/monsters/boss/ipog/rage/ipog_up_idle",gp.tileSize*i,gp.tileSize*i);
            up1 = setImage("/monsters/boss/ipog/rage/ipog_up_1",gp.tileSize*i,gp.tileSize*i);
            up2 = setImage("/monsters/boss/ipog/rage/ipog_up_2",gp.tileSize*i,gp.tileSize*i);
            downidle = setImage("/monsters/boss/ipog/rage/ipog_down_idle",gp.tileSize*i,gp.tileSize*i);
            down1 = setImage("/monsters/boss/ipog/rage/ipog_down_1",gp.tileSize*i,gp.tileSize*i);
            down2 = setImage("/monsters/boss/ipog/rage/ipog_down_2",gp.tileSize*i,gp.tileSize*i);
            leftidle = setImage("/monsters/boss/ipog/rage/ipog_left_idle",gp.tileSize*i,gp.tileSize*i);
            left1 = setImage("/monsters/boss/ipog/rage/ipog_left_1",gp.tileSize*i,gp.tileSize*i);
            left2 = setImage("/monsters/boss/ipog/rage/ipog_left_2",gp.tileSize*i,gp.tileSize*i);
            rightidle = setImage("/monsters/boss/ipog/rage/ipog_right_idle",gp.tileSize*i,gp.tileSize*i);
            right1 = setImage("/monsters/boss/ipog/rage/ipog_right_1",gp.tileSize*i,gp.tileSize*i);
            right2 = setImage("/monsters/boss/ipog/rage/ipog_right_2",gp.tileSize*i,gp.tileSize*i);
        }
    }

    @Override
    public void reGetAttackImages() {
        int i = 3;

        if(resistant){
            attackUp1 = setImage("/monsters/boss/ipog/ipog_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
            attackUp2 = setImage("/monsters/boss/ipog/ipog_attack_up_2", gp.tileSize*i, gp.tileSize*i * 2);
            attackUp3 = setImage("/monsters/boss/ipog/ipog_attack_up_3", gp.tileSize*i, gp.tileSize*i * 2);
            attackDown1 = setImage("/monsters/boss/ipog/ipog_attack_down_1", gp.tileSize*i, gp.tileSize*i * 2);
            attackDown2 = setImage("/monsters/boss/ipog/ipog_attack_down_2", gp.tileSize*i, gp.tileSize*i * 2);
            attackDown3 = setImage("/monsters/boss/ipog/ipog_attack_down_3", gp.tileSize*i, gp.tileSize*i * 2);
            attackLeft1 = setImage("/monsters/boss/ipog/ipog_attack_left_1", gp.tileSize*i * 2, gp.tileSize*i);
            attackLeft2 = setImage("/monsters/boss/ipog/ipog_attack_left_2", gp.tileSize*i * 2, gp.tileSize*i);
            attackLeft3 = setImage("/monsters/boss/ipog/ipog_attack_left_3", gp.tileSize*i * 2, gp.tileSize*i);
            attackRight1 = setImage("/monsters/boss/ipog/ipog_attack_right_1", gp.tileSize*i * 2, gp.tileSize*i);
            attackRight2 = setImage("/monsters/boss/ipog/ipog_attack_right_2", gp.tileSize*i * 2, gp.tileSize*i);
            attackRight3 = setImage("/monsters/boss/ipog/ipog_attack_right_3", gp.tileSize*i * 2, gp.tileSize*i);
        }else{
            attackUp1 = setImage("/monsters/boss/ipog/rage/ipog_attack_up_1", gp.tileSize*i, gp.tileSize*i * 2);
            attackUp2 = setImage("/monsters/boss/ipog/rage/ipog_attack_up_2", gp.tileSize*i, gp.tileSize*i * 2);
            attackUp3 = setImage("/monsters/boss/ipog/rage/ipog_attack_up_3", gp.tileSize*i, gp.tileSize*i * 2);
            attackDown1 = setImage("/monsters/boss/ipog/rage/ipog_attack_down_1", gp.tileSize*i, gp.tileSize*i * 2);
            attackDown2 = setImage("/monsters/boss/ipog/rage/ipog_attack_down_2", gp.tileSize*i, gp.tileSize*i * 2);
            attackDown3 = setImage("/monsters/boss/ipog/rage/ipog_attack_down_3", gp.tileSize*i, gp.tileSize*i * 2);
            attackLeft1 = setImage("/monsters/boss/ipog/rage/ipog_attack_left_1", gp.tileSize*i * 2, gp.tileSize*i);
            attackLeft2 = setImage("/monsters/boss/ipog/rage/ipog_attack_left_2", gp.tileSize*i * 2, gp.tileSize*i);
            attackLeft3 = setImage("/monsters/boss/ipog/rage/ipog_attack_left_3", gp.tileSize*i * 2, gp.tileSize*i);
            attackRight1 = setImage("/monsters/boss/ipog/rage/ipog_attack_right_1", gp.tileSize*i * 2, gp.tileSize*i);
            attackRight2 = setImage("/monsters/boss/ipog/rage/ipog_attack_right_2", gp.tileSize*i * 2, gp.tileSize*i);
            attackRight3 = setImage("/monsters/boss/ipog/rage/ipog_attack_right_3", gp.tileSize*i * 2, gp.tileSize*i);
        }
    }

    @Override
    public void update() {
        if(!sleep){
            if(knockBack){
                checkCollision();
                if(collision){
                    knockBack=false;
                    knockBackCounter=0;
                    speed=defaultSpeed;
                }else{
                    switch(knockBackDirection){
                        case "up" -> worldY-=speed;
                        case "down" -> worldY+=speed;
                        case "left" -> worldX-=speed;
                        case "right" -> worldX+=speed;
                    }
                }

                knockBackCounter++;
                if(knockBackCounter==10){
                    knockBack=false;
                    knockBackCounter=0;
                    speed=defaultSpeed;
                }
            }else if(attacking){
                attackUpdate();
            }else{
                setAction();

                checkCollision();

                if(!collision){
                    switch(direction){
                        case "up" -> worldY-=speed;
                        case "down" -> worldY+=speed;
                        case "left" -> worldX-=speed;
                        case "right" -> worldX+=speed;
                    }
                }

                increaseSpriteCounter();
            }

            if (projectileCooldownCounter<60)
                projectileCooldownCounter++;

            if(invincible){
                invincibleCounter++;
                if (invincibleCounter>35){
                    invincible=false;
                    invincibleCounter=0;
                }
            }

            if(weak){
                weak=false;
            }

            if(spriteCounter==-1){
                attacking=false;
            }
        }
    }

    @Override
    public void attackUpdate() {
        spriteCounter++;

        //Attacking
        if(spriteCounter>30 && spriteCounter<=50){
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
                if(gp.collisionH.checkPlayer(this)){
                    attackPlayer(attack);
                }
            }else{
                int monsterIndex = gp.collisionH.checkEntity(this,gp.monsters);
                gp.player.attackMonster(monsterIndex,this,attack,currentFireball.knockBackPower);

                int iTileIndex = gp.collisionH.checkEntity(this,gp.iTiles);
                gp.player.attackInteractiveTile(iTileIndex);

                int projectileIndex = gp.collisionH.checkEntity(this,gp.projectiles);
                gp.player.attackProjectile(projectileIndex);
            }

            worldX=currentWorldX;
            worldY=currentWorldY;
            collisionBox.width=collisionBoxWidth;
            collisionBox.height=collisionBoxHeight;
        }

        //SFX
        if(spriteCounter==1){
            gp.playSoundEffect(28);
        }
        if(spriteCounter==30){
            gp.playSoundEffect(29);
        }

        //Sprite
        if(spriteCounter<=30){
            spriteNum = 1;
        }else if(spriteCounter<=40){
            spriteNum = 2;
        }else if(spriteCounter<=50){
            spriteNum = 3;
        }else {
            attacking=false;
            spriteCounter=0;
        }
    }

    @Override
    public void setAction() {
        if(getDistanceAsTile(gp.player) < 10){
            moveTowardPlayer(60);
        }else{
            pickARandomDirection(120);
        }

        //Attack
        if(!attacking){
            checkAndAttack(60,gp.tileSize*7,gp.tileSize*5);
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter=0;
    }

    @Override
    public void checkDrop() {
        gp.bossBattleOn=false;

        //Restore the previous music
        gp.stopMusic();
        gp.playMusic(24);

        //Remove the iron doors
        for(int i=0;i<gp.obj[1].length;i++){
            if(gp.obj[gp.currentMap][i]!=null && gp.obj[gp.currentMap][i].name.equals(IronDoor.objName)){
                gp.playSoundEffect(27);
                gp.obj[gp.currentMap][i]=null;
            }
        }

        //Remove budi & bobo
        for(int i=0;i<gp.npcs[1].length;i++){
            if(gp.npcs[0][i]!=null && gp.npcs[0][i].name.equals(Budi.npcName)){
                gp.npcs[0][i]=null;
            }
            if(gp.npcs[1][i]!=null && gp.npcs[1][i].name.equals(Bobo.npcName)){
                gp.npcs[1][i]=null;
            }
        }

        Progress.ipogDefeated=true;

        dropItem(new SilverCoin(gp));
    }
}
