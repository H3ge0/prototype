package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity {

    public GamePanel gp;
    public BufferedImage upidle,up1,up2,downidle,down1,down2,leftidle,left1,left2,rightidle,right1,right2;
    public BufferedImage attackUp1,attackUp2,attackUp3,attackDown1,attackDown2,attackDown3,attackLeft1,attackLeft2,attackLeft3,attackRight1,attackRight2,attackRight3;
    public BufferedImage guardUp,guardDown,guardLeft,guardRight;
    public BufferedImage image1,image2,image3;
    public Rectangle collisionBox = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int collisionBoxDefaultX=collisionBox.x, collisionBoxDefaultY=collisionBox.y;
    public boolean collisionOn = false;
    public String[][] dialogues = new String[20][20];
    public Entity attacker;

    //Throwing Knife
    public BufferedImage upidlev2,up1v2,up2v2,downidlev2,down1v2,down2v2,leftidlev2,left1v2,left2v2,rightidlev2,right1v2,right2v2;

    //State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public int dialogueSet = 0;
    public int dialogueIndex=0;
    public boolean collision = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean weak = false;
    public Entity loot;
    public boolean opened=false;

    //Counters
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    public int projectileCooldownCounter = 0;
    public int knockBackCounter = 0;
    public int guardCounter = 0;
    public int weakCounter = 0;

    //Character
    public String name;
    public int defaultSpeed;
    public int speed;
    public int maxHp;
    public int hp;
    public int maxEnergy;
    public int energy;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentFireball;
    public Entity currentArmor;
    public Entity currentLightSource;
    public Projectile currentProjectile;

    //Item
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInvSize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String displayedName="";
    public String description="";
    public int useCost;
    public int knockBackPower=0;
    public boolean stackable=false;
    public int amount=1;
    public int lightRadius;

    //Type
    public int type;
    public final int typeMonster=2;
    public final int typeFireball=3;
    public final int typeArmor=4;
    public final int typeConsumable=5;
    public final int typePickUpOnly=6;
    public final int typeObstacle=7;
    public final int typeLightSource=8;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public int getLeftX(){
        return worldX+collisionBox.x;
    }

    public int getRightX(){
        return worldX+collisionBox.x+collisionBox.width;
    }

    public int getTopY(){
        return worldY+collisionBox.y;
    }

    public int getBottomY(){
        return worldY+collisionBox.y+collisionBox.height;
    }

    public int getCol(){
        return (worldX+collisionBox.x)/gp.tileSize;
    }

    public int getRow(){
        return (worldY+collisionBox.y)/gp.tileSize;
    }

    public int getXDistance(Entity target){
        return Math.abs(worldX-target.worldX);
    }

    public int getYDistance(Entity target){
        return Math.abs(worldY-target.worldY);
    }

    public int getDistanceAsTile(Entity target){
        return (getXDistance(target)+getYDistance(target))/gp.tileSize;
    }

    public int getGoalCol(Entity target){
        return (target.worldX+target.collisionBox.x)/gp.tileSize;
    }

    public int getGoalRow(Entity target){
        return (target.worldY+target.collisionBox.y)/gp.tileSize;
    }

    public void resetCounters(){
        actionLockCounter = 0;
        spriteCounter = 0;
        invincibleCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        projectileCooldownCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        weakCounter = 0;
    }

    public void setLoot(Entity loot){}

    public void setAction(){}

    public boolean use(Entity entity){return false;}

    public void checkDrop(){}

    public void damageReaction(){}

    public void interact(){}

    public void dropItem(Entity item){
        for(int i=0;i<gp.obj[gp.currentMap].length;i++){
            if (gp.obj[gp.currentMap][i]==null){
                gp.obj[gp.currentMap][i]=item;
                gp.obj[gp.currentMap][i].worldX=worldX;
                gp.obj[gp.currentMap][i].worldY=worldY;
                break;
            }
        }
    }

    public void startDialogue(Entity entity, int setNum){
        gp.gameState=gp.dialogueState;
        gp.uiH.npc = entity;
        dialogueSet = setNum;
    }

    public void speak(){

    }

    public void facePlayer(){
        switch(gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void checkCollision(){
        collision=false;
        gp.collisionH.checkEntity(this,gp.iTiles);
        gp.collisionH.checkTile(this);
        gp.collisionH.checkObject(this,false);
        gp.collisionH.checkEntity(this,gp.npcs);
        gp.collisionH.checkEntity(this,gp.monsters);
        boolean contactPlayer = gp.collisionH.checkPlayer(this);

        if (type==typeMonster && contactPlayer){
            attackPlayer(attack);
        }
    }

    public void update(){
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
            weakCounter++;
            if(weakCounter>60){
                weak=false;
                weakCounter=0;
            }
        }

        if(spriteCounter==-1){
            attacking=false;
        }

    }

    public void attackUpdate(){
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

    public String getOppositeDirection(String direction){
        String oppositeDirection = "";
        switch(direction){
            case "up" -> oppositeDirection="down";
            case "down" -> oppositeDirection="up";
            case "left" -> oppositeDirection="right";
            case "right" -> oppositeDirection="left";
        }
        return oppositeDirection;
    }

    public void checkAndAttack(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDis = getXDistance(gp.player);
        int yDis = getYDistance(gp.player);

        switch(direction){
            case "up" ->{
                if(gp.player.worldY<worldY && yDis<straight && xDis<horizontal){
                    targetInRange=true;
                }
            }
            case "down" ->{
                if(gp.player.worldY>worldY && yDis<straight && xDis<horizontal){
                    targetInRange=true;
                }
            }
            case "left" ->{
                if(gp.player.worldX<worldX && xDis<straight && yDis<horizontal){
                    targetInRange=true;
                }
            }
            case "right" ->{
                if(gp.player.worldX>worldX && xDis<straight && yDis<horizontal){
                    targetInRange=true;
                }
            }
        }

        if(targetInRange){
            int i = new Random().nextInt(rate);
            if(i==1){
                attacking=true;
                spriteCounter=0;
                spriteNum=1;
                projectileCooldownCounter=0;
            }
        }

    }

    public void checkAndStopAggro(Entity target, int distance, int rate){
        if(getDistanceAsTile(target)>distance){
            int i = new Random().nextInt(rate);
            if(i==0){
                onPath=false;
            }
        }
    }

    public void checkAndStartAggro(Entity target, int distance, int rate){
        if(getDistanceAsTile(target)<distance){
            int i = new Random().nextInt(rate);
            if(i==0){
                onPath=true;
            }
        }
    }

    public void checkAndShootProjectile(int rate, int shootDelay){
        int i = new Random().nextInt(rate);
        if(i==0 && !currentProjectile.alive && projectileCooldownCounter==shootDelay){
            currentProjectile.setProjectile(worldX,worldY,direction,true,this);
            //Check empty place in array
            for(int j=0;j<gp.projectiles[gp.currentMap].length;j++){
                if(gp.projectiles[gp.currentMap][j]==null){
                    gp.projectiles[gp.currentMap][j]=currentProjectile;
                    break;
                }
            }
            if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize)
                gp.playSoundEffect(12);
            projectileCooldownCounter=0;
        }
    }

    public void pickARandomDirection(){
        actionLockCounter++;

        if (actionLockCounter==120){
            int i = new Random().nextInt(100)+1;

            if(i<=25){
                direction="up";
            } else if (i<=50){
                direction="down";
            } else if (i<=75){
                direction="left";
            } else {
                direction="right";
            }
            actionLockCounter=0;
        }
    }

    public void attackPlayer(int attack){
        if(gp.player.hp>0 && !gp.player.invincible && !dying){
            int damage = attack-gp.player.defense;

            //Get opposite direction of player
            String neededGuardDirection = getOppositeDirection(direction);

            if(gp.player.guarding && gp.player.direction.equals(neededGuardDirection)){
                if(gp.player.guardCounter<15){
                    damage=0;
                    gp.playSoundEffect(19);
                    applyKnockBack(this,gp.player,5);
                    weak=true;
                    spriteCounter = -60;
                }else{
                    damage /= 3;
                }
            }else {
                if(damage<1)
                    damage=1;
            }

            if(damage!=0){
                gp.player.transparent=true;
                applyKnockBack(gp.player,this,knockBackPower);
            }

            gp.player.hp-=damage;

            if(gp.player.hp<=0)
                gp.playSoundEffect(13);
            else if(gp.player.guarding)
                gp.playSoundEffect(12);
            else
                gp.playSoundEffect(7);

            gp.player.invincible=true;
        }
    }

    public void applyKnockBack(Entity target,Entity attacker, int knockBackPower){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed+=knockBackPower;
        target.knockBack=true;
    }

    public void increaseSpriteCounter() {
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

        BufferedImage image;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(Math.abs(gp.player.worldX-worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-worldY)<gp.screenHeight/2+gp.tileSize){

            image = setDrawImage();

            int tempScreenX=screenX;
            int tempScreenY=screenY;

            if (attacking){
                switch (direction) {
                    case "up" -> {
                        tempScreenY-=gp.tileSize;
                        if (spriteNum == 1)
                            image = attackUp1;
                        if (spriteNum == 2)
                            image = attackUp2;
                        if (spriteNum == 3)
                            image = attackUp3;
                    }
                    case "down" -> {
                        if (spriteNum == 1)
                            image = attackDown1;
                        if (spriteNum == 2)
                            image = attackDown2;
                        if (spriteNum == 3)
                            image = attackDown3;
                    }
                    case "left" -> {
                        tempScreenX-=gp.tileSize;
                        if (spriteNum == 1)
                            image = attackLeft1;
                        if (spriteNum == 2)
                            image = attackLeft2;
                        if (spriteNum == 3)
                            image = attackLeft3;
                    }
                    case "right" -> {
                        if (spriteNum == 1)
                            image = attackRight1;
                        if (spriteNum == 2)
                            image = attackRight2;
                        if (spriteNum == 3)
                            image = attackRight3;
                    }
                }
            }

            if(type==typeMonster && hpBarOn){
                double oneScale = (double)gp.tileSize/maxHp;
                double hpBarWidth = oneScale*hp;

                g2.setColor(new Color(135,35,35));
                g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-15,(int)hpBarWidth,10);

                hpBarCounter++;
                if(hpBarCounter>=600){
                    hpBarCounter=0;
                    hpBarOn=false;
                }
            }

            if (invincible){
                hpBarOn=true;
                hpBarCounter=0;
                if (invincibleCounter<15){
                    setG2Alpha(g2, 0.5f);
                }else if (invincibleCounter<30){
                    setG2Alpha(g2, 0.9f);
                }else if (invincibleCounter<45){
                    setG2Alpha(g2, 0.5f);
                }else if (invincibleCounter<60){
                    setG2Alpha(g2, 0.9f);
                }else if (invincibleCounter<75){
                    setG2Alpha(g2, 0.5f);
                }else if (invincibleCounter<90){
                    setG2Alpha(g2, 0.5f);
                }
            }

            if(dying){
                dyingAnimation(g2);
            }

            drawTheImage(g2,image,tempScreenX,tempScreenY);

            gp.effectH.drawEffect(g2,this);

            setG2Alpha(g2, 1f);
        }
    }

    public void drawTheImage(Graphics2D g2,BufferedImage image,int x,int y){
        g2.drawImage(image,x,y,null);
    }

    public BufferedImage setDrawImage() {
        BufferedImage image=null;
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
        return image;
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 4;

        if(dyingCounter<=i) setG2Alpha(g2, 0.9f);
        else if(dyingCounter<=i*2) setG2Alpha(g2, 0.8f);
        else if(dyingCounter<=i*3) setG2Alpha(g2, 0.7f);
        else if(dyingCounter<=i*4) setG2Alpha(g2, 0.6f);
        else if(dyingCounter<=i*5) setG2Alpha(g2, 0.5f);
        else if(dyingCounter<=i*6) setG2Alpha(g2, 0.4f);
        else if(dyingCounter<=i*7) setG2Alpha(g2, 0.3f);
        else if(dyingCounter<=i*8) setG2Alpha(g2, 0.2f);
        else if(dyingCounter<=i*9) setG2Alpha(g2, 0.1f);
        else {
            alive=false;
        }

    }

    public static void setG2Alpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public BufferedImage setImage(String path,int width,int height){
        UtilityTool utility = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path+".png")));
            image = utility.scaleImage(image,width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public Color getParticleColor(){
        return null;
    }

    public int getParticleSize(){
        return 0;
    }

    public int getParticleSpeed(){
        return 0;
    }

    public int getMaxHp(){
        return 0;
    }

    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxHp = generator.getMaxHp();

        Particle p1 = new Particle(gp,target,color,size,speed,maxHp,2,1);
        Particle p2 = new Particle(gp,target,color,size,speed,maxHp,2,-1);
        Particle p3 = new Particle(gp,target,color,size,speed,maxHp,-2,1);
        Particle p4 = new Particle(gp,target,color,size,speed,maxHp,-2,-1);
        gp.particles.add(p1);
        gp.particles.add(p2);
        gp.particles.add(p3);
        gp.particles.add(p4);
    }

    public void searchPath(int goalCol,int goalRow){
        int startCol=getCol();
        int startRow=getRow();

        gp.pathFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if(gp.pathFinder.search()){
            //Next worldX and worldY
            int nextX = gp.pathFinder.pathList.get(0).col*gp.tileSize;
            int nextY = gp.pathFinder.pathList.get(0).row*gp.tileSize;
            //Collision box positions
            int enLeftX = getLeftX();
            int enRightX = getRightX();
            int enTopY = getTopY();
            int enBottomY = getBottomY();

            if(enTopY>nextY && enLeftX>=nextX && enRightX<nextX+gp.tileSize){
                direction="up";
            }else if(enTopY<nextY && enLeftX>=nextX && enRightX<nextX+gp.tileSize){
                direction="down";
            }else if(enTopY>=nextY && enBottomY<nextY+gp.tileSize){
                //Left or Right
                if(enLeftX>nextX){
                    direction="left";
                }
                if(enLeftX<nextX){
                    direction="right";
                }
            }else if(enTopY>nextY && enLeftX>nextX){
                //Up or Left
                direction="up";
                checkCollision();
                if(collision)
                    direction="left";
            }else if(enTopY>nextY && enLeftX<nextX){
                //Up or Right
                direction="up";
                checkCollision();
                if(collision)
                    direction="right";
            }else if(enTopY<nextY && enLeftX>nextX){
                //Down or Left
                direction="down";
                checkCollision();
                if(collision)
                    direction="left";
            }else if(enTopY<nextY && enLeftX<nextX){
                //Down or Right
                direction="down";
                checkCollision();
                if(collision)
                    direction="right";
            }
        }
    }

    public int getDetected(Entity user, Entity[][] target, String name){
        int index = 999;

        //Check surrounding objects
        int nextWorldX = user.getCol();
        int nextWorldY = user.getRow();

        switch(user.direction){
            case "up" -> nextWorldY = user.getRow()-1;
            case "down" -> nextWorldY = user.getRow()+1;
            case "left" -> nextWorldX = user.getCol()-1;
            case "right" -> nextWorldX = user.getCol()+1;
        }

        int col = nextWorldX;
        int row = nextWorldY;

        for(int i=0;i<target[gp.currentMap].length;i++) {
            if(target[gp.currentMap][i]!=null && target[gp.currentMap][i].getCol()==col && target[gp.currentMap][i].getRow()==row && (target[gp.currentMap][i].name.equals(name) || target[gp.currentMap][i].name.equals(""))){
                index=i;
                break;
            }
        }

        return index;
    }

}
