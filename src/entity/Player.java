package entity;

import main.GamePanel;
import main.KeyHandler;
import object.ArmorLeather;
import object.FireballOrange;
import object.ThrowingKnife;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity{

    public boolean canDrink=true, canFall=true;
    KeyHandler keyH;
    Random random;
    public final int screenX, screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int invSize = 20;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH=keyH;

        random = new Random();

        screenX = gp.screenWidth/2-gp.tileSize/2;
        screenY = gp.screenHeight/2-gp.tileSize/2;

        collisionBox = new Rectangle(3*gp.scale,7*gp.scale,10*gp.scale,8*gp.scale);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;

        setValues();
        getImages();
        getAttackImages();
        setInventory();
    }

    public void setValues() {
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*21;
        speed=4;
        direction="down";
        //Player Stats
        level=1;
        maxHp=6;
        hp=maxHp;
        maxEnergy=4;
        energy=maxEnergy;
        strength=1;
        dexterity=1;
        exp=0;
        nextLevelExp=4;
        coin=0;
        currentFireball=new FireballOrange(gp);
        currentArmor=new ArmorLeather(gp);
        currentProjectile=new ThrowingKnife(gp);
        attack=getAttack();
        defense=getDefense();
    }

    public void setInventory(){
        inventory.add(currentFireball);
        inventory.add(currentArmor);
    }

    public int getAttack(){
        attackArea = currentFireball.attackArea;
        return strength*currentFireball.attackValue;
    }

    public int getDefense(){
        return dexterity*currentArmor.defenseValue;
    }

    public void getImages(){
        upidle = setImage("/player/gopi_up_idle",gp.tileSize,gp.tileSize);
        up1 = setImage("/player/gopi_up_1",gp.tileSize,gp.tileSize);
        up2 = setImage("/player/gopi_up_2",gp.tileSize,gp.tileSize);
        downidle = setImage("/player/gopi_down_idle",gp.tileSize,gp.tileSize);
        down1 = setImage("/player/gopi_down_1",gp.tileSize,gp.tileSize);
        down2 = setImage("/player/gopi_down_2",gp.tileSize,gp.tileSize);
        leftidle = setImage("/player/gopi_left_idle",gp.tileSize,gp.tileSize);
        left1 = setImage("/player/gopi_left_1",gp.tileSize,gp.tileSize);
        left2 = setImage("/player/gopi_left_2",gp.tileSize,gp.tileSize);
        rightidle = setImage("/player/gopi_right_idle",gp.tileSize,gp.tileSize);
        right1 = setImage("/player/gopi_right_1",gp.tileSize,gp.tileSize);
        right2 = setImage("/player/gopi_right_2",gp.tileSize,gp.tileSize);
    }

    public void getAttackImages(){
        switch (currentFireball.name) {
            case "Orange Fireball" -> {
                attackUp1 = setImage("/player/gopi_attack_up_1", gp.tileSize, gp.tileSize * 2);
                attackUp2 = setImage("/player/gopi_attack_up_2", gp.tileSize, gp.tileSize * 2);
                attackUp3 = setImage("/player/gopi_attack_up_3", gp.tileSize, gp.tileSize * 2);
                attackDown1 = setImage("/player/gopi_attack_down_1", gp.tileSize, gp.tileSize * 2);
                attackDown2 = setImage("/player/gopi_attack_down_2", gp.tileSize, gp.tileSize * 2);
                attackDown3 = setImage("/player/gopi_attack_down_3", gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setImage("/player/gopi_attack_left_1", gp.tileSize * 2, gp.tileSize);
                attackLeft2 = setImage("/player/gopi_attack_left_2", gp.tileSize * 2, gp.tileSize);
                attackLeft3 = setImage("/player/gopi_attack_left_3", gp.tileSize * 2, gp.tileSize);
                attackRight1 = setImage("/player/gopi_attack_right_1", gp.tileSize * 2, gp.tileSize);
                attackRight2 = setImage("/player/gopi_attack_right_2", gp.tileSize * 2, gp.tileSize);
                attackRight3 = setImage("/player/gopi_attack_right_3", gp.tileSize * 2, gp.tileSize);
            }
            case "Purple Fireball" -> {
                attackUp1 = setImage("/player/purple/gopi_attack_up_1", gp.tileSize, gp.tileSize * 2);
                attackUp2 = setImage("/player/purple/gopi_attack_up_2", gp.tileSize, gp.tileSize * 2);
                attackUp3 = setImage("/player/purple/gopi_attack_up_3", gp.tileSize, gp.tileSize * 2);
                attackDown1 = setImage("/player/purple/gopi_attack_down_1", gp.tileSize, gp.tileSize * 2);
                attackDown2 = setImage("/player/purple/gopi_attack_down_2", gp.tileSize, gp.tileSize * 2);
                attackDown3 = setImage("/player/purple/gopi_attack_down_3", gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setImage("/player/purple/gopi_attack_left_1", gp.tileSize * 2, gp.tileSize);
                attackLeft2 = setImage("/player/purple/gopi_attack_left_2", gp.tileSize * 2, gp.tileSize);
                attackLeft3 = setImage("/player/purple/gopi_attack_left_3", gp.tileSize * 2, gp.tileSize);
                attackRight1 = setImage("/player/purple/gopi_attack_right_1", gp.tileSize * 2, gp.tileSize);
                attackRight2 = setImage("/player/purple/gopi_attack_right_2", gp.tileSize * 2, gp.tileSize);
                attackRight3 = setImage("/player/purple/gopi_attack_right_3", gp.tileSize * 2, gp.tileSize);
            }
            case "Red Fireball" -> {
                attackUp1 = setImage("/player/red/gopi_attack_up_1", gp.tileSize, gp.tileSize * 2);
                attackUp2 = setImage("/player/red/gopi_attack_up_2", gp.tileSize, gp.tileSize * 2);
                attackUp3 = setImage("/player/red/gopi_attack_up_3", gp.tileSize, gp.tileSize * 2);
                attackDown1 = setImage("/player/red/gopi_attack_down_1", gp.tileSize, gp.tileSize * 2);
                attackDown2 = setImage("/player/red/gopi_attack_down_2", gp.tileSize, gp.tileSize * 2);
                attackDown3 = setImage("/player/red/gopi_attack_down_3", gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setImage("/player/red/gopi_attack_left_1", gp.tileSize * 2, gp.tileSize);
                attackLeft2 = setImage("/player/red/gopi_attack_left_2", gp.tileSize * 2, gp.tileSize);
                attackLeft3 = setImage("/player/red/gopi_attack_left_3", gp.tileSize * 2, gp.tileSize);
                attackRight1 = setImage("/player/red/gopi_attack_right_1", gp.tileSize * 2, gp.tileSize);
                attackRight2 = setImage("/player/red/gopi_attack_right_2", gp.tileSize * 2, gp.tileSize);
                attackRight3 = setImage("/player/red/gopi_attack_right_3", gp.tileSize * 2, gp.tileSize);
            }
        }

    }

    public void update() {

        keyH.keyPressed = keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed||keyH.xKeyPressed;

        if(attacking){
            attackUpdate();
        }else if(keyH.keyPressed){

            if (keyH.upPressed){
                direction="up";
            }else if (keyH.downPressed){
                direction="down";
            }else if (keyH.leftPressed){
                direction="left";
            }else if (keyH.rightPressed){
                direction="right";
            }

            collision = false;
            gp.collisionH.checkEntity(this,gp.iTiles);

            gp.collisionH.checkTile(this);

            int objIndex = gp.collisionH.checkObject(this,true);
            interactWithObj(objIndex);

            int npcIndex = gp.collisionH.checkEntity(this,gp.npcs);
            interactWithNPC(npcIndex);

            int monsterIndex = gp.collisionH.checkEntity(this,gp.monsters);
            interactWithMonster(monsterIndex);

            gp.eventH.checkEvent();

            if(!collision&&!keyH.xKeyPressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if (keyH.xKeyPressed && !attackCanceled){
                gp.playSoundEffect(9);
                attacking=true;
                spriteCounter=0;
            }

            attackCanceled=false;
            keyH.xKeyPressed=false;

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
        if(projectileCooldownCounter==60 && gp.keyH.zKeyPressed && !currentProjectile.alive){
            if(currentProjectile.hasEnergy(this)){
                currentProjectile.subtractEnergy(this);
                currentProjectile.setProjectile(worldX,worldY,direction,true,this);
                gp.projectiles.add(currentProjectile);
                gp.playSoundEffect(11);
            }else {
                gp.uiH.addMessage("Yeterli enerjin yok.");
            }
            projectileCooldownCounter=0;
        }
        if (projectileCooldownCounter<60)
            projectileCooldownCounter++;

        if(invincible){
            invincibleCounter++;
            if (invincibleCounter>90){
                invincible=false;
                invincibleCounter=0;
            }
        }

        if(hp>maxHp){
            hp=maxHp;
        }
        if(energy>maxEnergy){
            energy=maxEnergy;
        }

    }

    public void attackUpdate(){
        spriteCounter++;

        //Attacking
        if(spriteCounter>5 && spriteCounter<=25){
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

            int monsterIndex = gp.collisionH.checkEntity(this,gp.monsters);
            attackMonster(monsterIndex,attack);

            int iTileIndex = gp.collisionH.checkEntity(this,gp.iTiles);
            attackInteractiveTile(iTileIndex);

            worldX=currentWorldX;
            worldY=currentWorldY;
            collisionBox.width=collisionBoxWidth;
            collisionBox.height=collisionBoxHeight;
        }

        //Sprite
        if(spriteCounter<=5){
            spriteNum = 1;
        }
        if(spriteCounter>5 && spriteCounter<=12){
            spriteNum = 2;
        }
        if (spriteCounter>12 && spriteCounter<=25) {
            spriteNum = 3;
        }
        if(spriteCounter>25){
            attacking=false;
            spriteCounter=0;
        }
    }

    public void interactWithObj(int index){
        if(index!=999){
            //Pickup only items
            if(gp.obj[index].type==typePickUpOnly){
                gp.obj[index].use(this);
            }

            //Normal Items
            else{
                String text;
                if(inventory.size()<invSize){
                    inventory.add(gp.obj[index]);
                    gp.playSoundEffect(4);
                    text = "Bir "+gp.obj[index].displayedName+" buldun!";
                }else
                    text = "Envanterin dolu.";
                gp.uiH.addMessage(text);
            }
            gp.obj[index] = null;
        }
    }

    public void interactWithNPC(int index){
        if(keyH.xKeyPressed){
            if(index!=999){
                attackCanceled = true;
                gp.gameState=gp.dialogueState;
                gp.npcs[index].speak();
            }
        }
    }

    public void interactWithMonster(int index){
        if(index!=999){
            if(hp>0 && !invincible && !gp.monsters[index].dying){
                int damage = gp.monsters[index].attack-defense;
                gp.playSoundEffect(7);
                if(damage>0){
                    hp-=damage;
                } else {
                    hp--;
                }
                invincible=true;
            }
        }
    }

    public void attackMonster(int index, int attack){
        if(index!=999){
            if(gp.monsters[index].hp>0 && !gp.monsters[index].invincible){
                gp.playSoundEffect(8);
                int damage = attack-gp.monsters[index].defense;
                if(damage<0)
                    damage=0;
                gp.monsters[index].hp-=damage;
                gp.uiH.addMessage(damage+" hasar!");
                gp.monsters[index].invincible=true;
                gp.monsters[index].damageReaction();
            }
            if (gp.monsters[index].hp<=0 && !gp.monsters[index].dying){
                gp.monsters[index].dying = true;
                gp.uiH.addMessage(gp.monsters[index].name+" öldü.");
                exp += gp.monsters[index].exp;
                gp.uiH.addMessage("+"+gp.monsters[index].exp+" deneyim.");
                checkLevelUp();
            }
        }
    }

    public void attackInteractiveTile(int index){
        if(index!=999 && gp.iTiles[index].canChange && gp.iTiles[index].isCorrectItem(this)){
            gp.iTiles[index].attack(index);
        }
    }

    public void checkLevelUp(){
        if(exp>=nextLevelExp){
            level++;
            nextLevelExp*=2;
            maxHp+=2;
            if(maxHp>12)
                maxHp=12;
            maxEnergy++;
            if(maxEnergy>6)
                maxEnergy=6;
            strength++;
            dexterity++;
            attack=getAttack();
            defense=getDefense();

            gp.playSoundEffect(6);
            gp.gameState=gp.dialogueState;
            gp.uiH.currentDialogueText="Seviye atladın!! Artık "+level+". seviyesin!";
        }
    }

    public void useItem(){
        int itemIndex = gp.uiH.getItemIndex();

        if(itemIndex<inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            switch(selectedItem.type){
                case typeFireball -> {
                    if(!currentFireball.name.equals(selectedItem.name)){
                        gp.playSoundEffect(10);
                        currentFireball=selectedItem;
                        attack=getAttack();
                        getAttackImages();
                    }
                }
                case typeArmor -> {
                    if(!currentArmor.name.equals(selectedItem.name)){
                        gp.playSoundEffect(10);
                        currentArmor=selectedItem;
                        defense=getDefense();
                    }
                }
                case typeConsumable -> {
                    selectedItem.use(this);
                    inventory.remove(selectedItem);
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
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
        } else if(keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed){
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

        if (invincible){
            if (invincibleCounter<15){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }else if (invincibleCounter<30){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            }else if (invincibleCounter<45){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }else if (invincibleCounter<60){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            }else if (invincibleCounter<75){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }else if (invincibleCounter<90){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

}
