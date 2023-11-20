package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends Entity{

    public boolean canDrink=true, canFall=true;
    KeyHandler keyH;
    Random random;
    public final int screenX, screenY;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH=keyH;

        random = new Random();

        screenX = gp.screenWidth/2-gp.tileSize/2;
        screenY = gp.screenHeight/2-gp.tileSize/2;

        collisionBox = new Rectangle(3*gp.scale,7*gp.scale,10*gp.scale,8*gp.scale);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;

        setDefaultValues();
    }

    public void setDefaultValues() {
        setDefaultPosition();
        defaultSpeed=4;
        speed=defaultSpeed;
        //Player Stats
        level=1;
        maxHp=6;
        hp=maxHp;
        maxEnergy=3;
        energy=maxEnergy;
        strength=1;
        dexterity=1;
        exp=0;
        nextLevelExp=4;
        coin=0;
        currentFireball=new FireballOrange(gp);
        currentArmor=new ArmorLeather(gp);
        currentLightSource=null;
        currentProjectile=new ThrowingKnife(gp);
        attack=getAttack();
        defense=getDefense();

        getImages();
        getAttackImages();
        setInventory();
        getGuardImages();
        setDialogues();
    }

    public void setDefaultPosition(){
        gp.currentMap=0;
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*22;
        direction="down";
    }

    public void restoreStatus(){
        hp=maxHp;
        energy=maxEnergy;
        speed=defaultSpeed;
        invincible=false;
        transparent=false;
        attacking=false;
        guarding=false;
        knockBack=false;
        lightUpdated=true;
    }

    public void setInventory(){
        inventory.clear();
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

    public int getCurrentFireballSlot(){
        int currentFireballSlot = 0;
        for(int i = 0; i<inventory.size(); i++){
            if(inventory.get(i)==currentFireball){
                currentFireballSlot=i;
            }
        }

        return currentFireballSlot;
    }

    public int getCurrentArmorSlot(){
        int currentArmorSlot = 0;
        for(int i = 0; i<inventory.size(); i++){
            if(inventory.get(i)==currentArmor){
                currentArmorSlot =i;
            }
        }

        return currentArmorSlot;
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

        image1 = setImage("/player/gopi_sleep",gp.tileSize,gp.tileSize);
    }

    public void getSleepingImage(){
        upidle = image1;
        up1 = image1;
        up2 = image1;
        downidle = image1;
        down1 = image1;
        down2 = image1;
        leftidle = image1;
        left1 = image1;
        left2 = image1;
        rightidle = image1;
        right1 = image1;
        right2 = image1;
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
            case "Black Fireball" -> {
                attackUp1 = setImage("/player/black/gopi_attack_up_1", gp.tileSize, gp.tileSize * 2);
                attackUp2 = setImage("/player/black/gopi_attack_up_2", gp.tileSize, gp.tileSize * 2);
                attackUp3 = setImage("/player/black/gopi_attack_up_3", gp.tileSize, gp.tileSize * 2);
                attackDown1 = setImage("/player/black/gopi_attack_down_1", gp.tileSize, gp.tileSize * 2);
                attackDown2 = setImage("/player/black/gopi_attack_down_2", gp.tileSize, gp.tileSize * 2);
                attackDown3 = setImage("/player/black/gopi_attack_down_3", gp.tileSize, gp.tileSize * 2);
                attackLeft1 = setImage("/player/black/gopi_attack_left_1", gp.tileSize * 2, gp.tileSize);
                attackLeft2 = setImage("/player/black/gopi_attack_left_2", gp.tileSize * 2, gp.tileSize);
                attackLeft3 = setImage("/player/black/gopi_attack_left_3", gp.tileSize * 2, gp.tileSize);
                attackRight1 = setImage("/player/black/gopi_attack_right_1", gp.tileSize * 2, gp.tileSize);
                attackRight2 = setImage("/player/black/gopi_attack_right_2", gp.tileSize * 2, gp.tileSize);
                attackRight3 = setImage("/player/black/gopi_attack_right_3", gp.tileSize * 2, gp.tileSize);
            }
        }

    }

    public void getGuardImages(){
        guardUp = setImage("/player/gopi_up_guard",gp.tileSize,gp.tileSize);
        guardDown = setImage("/player/gopi_down_guard",gp.tileSize,gp.tileSize);
        guardLeft = setImage("/player/gopi_left_guard",gp.tileSize,gp.tileSize);
        guardRight = setImage("/player/gopi_right_guard",gp.tileSize,gp.tileSize);
    }

    public void setDialogues(){
        dialogues[0][0]="Seviye atladın!! Artık "+level+". seviyesin!";
    }

    public void update() {

        keyH.keyPressed = keyH.upPressed||keyH.downPressed||keyH.leftPressed||keyH.rightPressed||keyH.xKeyPressed;

        if(knockBack){

            gp.collisionH.checkEntity(this,gp.iTiles);

            gp.collisionH.checkTile(this);

            gp.collisionH.checkObject(this,true);

            gp.collisionH.checkEntity(this,gp.npcs);

            gp.collisionH.checkEntity(this,gp.monsters);

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
        }else if(keyH.sKeyPressed){
            if(!guarding)
                gp.playSoundEffect(18);
            guarding=true;

            guardCounter++;
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
                spriteNum=1;
            }

            attackCanceled=false;
            keyH.xKeyPressed=false;
            guarding=false;
            guardCounter = 0;

            spriteCounter++;
            if (spriteCounter>10){
                if (spriteNum==4){
                    spriteNum=1;
                } else {
                    spriteNum++;
                }
                spriteCounter=0;
            }
        }else {
            guarding = false;
            guardCounter = 0;
        }

        if(projectileCooldownCounter==60 && gp.keyH.zKeyPressed && !currentProjectile.alive){
            if(currentProjectile.hasEnergy(this)){
                currentProjectile.subtractEnergy(this);
                currentProjectile.setProjectile(worldX,worldY,direction,true,this);
                //Check empty place in array
                for(int i=0;i<gp.projectiles[gp.currentMap].length;i++){
                    if(gp.projectiles[gp.currentMap][i]==null){
                        gp.projectiles[gp.currentMap][i]=currentProjectile;
                        break;
                    }
                }
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
                transparent=false;
                invincibleCounter=0;
            }
        }

        if(hp>maxHp){
            hp=maxHp;
        }
        if(energy>maxEnergy){
            energy=maxEnergy;
        }
        /*
        if(defaultSpeed>6){
            defaultSpeed=6;
        }
        */

        if(hp<=0){
            gp.playSoundEffect(13);
            gp.gameState=gp.deadState;
            gp.uiH.commandNum=-1;
            gp.stopMusic();
        }

    }

    @Override
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
            if(gp.obj[gp.currentMap][index].type==typePickUpOnly){
                gp.obj[gp.currentMap][index].use(this);
                gp.obj[gp.currentMap][index] = null;
            }

            //Obstacle items
            else if(gp.obj[gp.currentMap][index].type==typeObstacle){
                if(keyH.xKeyPressed){
                    gp.obj[gp.currentMap][index].interact();
                    attackCanceled=true;
                }
            }

            //Normal Items
            else{
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][index])){
                    gp.playSoundEffect(4);
                    text = "Bir "+gp.obj[gp.currentMap][index].displayedName+" buldun!";
                }else
                    text = "Envanterin dolu. Ama canın "+ gp.obj[gp.currentMap][index].displayedName +" yemek istedi.";
                gp.uiH.addMessage(text);
                gp.obj[gp.currentMap][index] = null;
            }
        }
    }

    public void interactWithNPC(int index){
        if(index!=999){
            if(keyH.xKeyPressed){
                attackCanceled = true;
                gp.npcs[gp.currentMap][index].speak();
            }
            gp.npcs[gp.currentMap][index].move(direction);
        }
    }

    public void interactWithMonster(int index){
        if(index!=999){
            if(hp>0 && !invincible && !gp.monsters[gp.currentMap][index].dying){
                int damage = gp.monsters[gp.currentMap][index].attack-defense;

                if(damage<1){
                    damage=1;
                }

                hp-=damage;

                if(hp<=0)
                    gp.playSoundEffect(13);
                else
                    gp.playSoundEffect(7);

                transparent=true;
                invincible=true;
            }
        }
    }

    public void attackMonster(int index, Entity attacker, int attack, int knockBackPower){
        if(index!=999){
            if(gp.monsters[gp.currentMap][index].hp>0 && !gp.monsters[gp.currentMap][index].invincible){
                gp.playSoundEffect(8);
                if(knockBackPower>0)
                    applyKnockBack(gp.monsters[gp.currentMap][index], attacker, knockBackPower);

                if(gp.monsters[gp.currentMap][index].weak){
                    attack *=5;
                }

                int damage = attack-gp.monsters[gp.currentMap][index].defense;
                if(damage<0)
                    damage=0;
                gp.monsters[gp.currentMap][index].hp-=damage;
                gp.uiH.addMessage(damage+" hasar!");
                gp.monsters[gp.currentMap][index].invincible=true;
                gp.monsters[gp.currentMap][index].damageReaction();
            }
            if (gp.monsters[gp.currentMap][index].hp<=0 && !gp.monsters[gp.currentMap][index].dying){
                gp.monsters[gp.currentMap][index].dying = true;
                gp.uiH.addMessage(gp.monsters[gp.currentMap][index].name+" öldü.");
                exp += gp.monsters[gp.currentMap][index].exp;
                gp.uiH.addMessage("+"+gp.monsters[gp.currentMap][index].exp+" deneyim.");
                checkLevelUp();
            }
        }
    }

    public void attackInteractiveTile(int index){
        if(index!=999 && gp.iTiles[gp.currentMap][index].canChange && gp.iTiles[gp.currentMap][index].isCorrectItem(this) && !gp.iTiles[gp.currentMap][index].invincible){
            gp.iTiles[gp.currentMap][index].generateITileParticle(index);

            gp.iTiles[gp.currentMap][index].hp--;
            gp.iTiles[gp.currentMap][index].invincible=true;

            if(gp.iTiles[gp.currentMap][index].hp == 0){
                gp.iTiles[gp.currentMap][index].attack(index);
            }
        }
    }

    public void attackProjectile(int index){
        if(index!=999){
            Entity projectile = gp.projectiles[gp.currentMap][index];
            if(projectile.name.equals("Rock")){
                projectile.alive=false;
                generateParticle(projectile,projectile);
            }
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

            dialogues[0][0] = "Seviye atladın!! Artık "+level+". seviyesin!";
            startDialogue(this,0);
        }
    }

    public void useItem(){
        int itemIndex = gp.uiH.getItemIndex(this);

        if(itemIndex<inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            switch(selectedItem.type){
                case typeFireball -> {
                    if(currentFireball!=selectedItem){
                        gp.playSoundEffect(10);
                        currentFireball=selectedItem;
                        attack=getAttack();
                        getAttackImages();
                    }
                }
                case typeArmor -> {
                    if(currentArmor!=selectedItem){
                        gp.playSoundEffect(10);
                        currentArmor=selectedItem;
                        defense=getDefense();
                    }
                }
                case typeLightSource -> {
                    if(currentLightSource!=selectedItem){
                        gp.playSoundEffect(10);
                        currentLightSource=selectedItem;
                    }else{
                        gp.playSoundEffect(10);
                        currentLightSource=null;
                    }
                    lightUpdated=true;
                }
                case typeConsumable -> {
                    if(selectedItem.use(this))
                        if(selectedItem.amount>1){
                            selectedItem.amount--;
                        }else
                            inventory.remove(selectedItem);
                }
            }
        }
    }

    public int searchItemInInventory(String name){
        int index=999;

        for(int i=0;i<inventory.size();i++){
            if(inventory.get(i).name.equals(name)){
                index=i;
                break;
            }
        }

        return index;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtainItem = false;

        Entity newItem = gp.entityGenerator.getObject(item.name);

        if(newItem.stackable){
            int itemIndex = searchItemInInventory(newItem.name);

            if(itemIndex!=999){
                inventory.get(itemIndex).amount++;
                canObtainItem=true;
            }else{
                if(inventory.size()<maxInvSize){
                    inventory.add(newItem);
                    canObtainItem=true;
                }
            }
        }else{
            if(inventory.size()<maxInvSize){
                inventory.add(newItem);
                canObtainItem=true;
            }
        }

        return canObtainItem;
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
                case "up" -> {
                    if(guarding)
                        image = guardUp;
                    else
                        image = upidle;
                }
                case "down" -> {
                    if(guarding)
                        image = guardDown;
                    else
                        image = downidle;
                }
                case "left" -> {
                    if(guarding)
                        image=guardLeft;
                    else
                        image=leftidle;
                }
                case "right" -> {
                    if(guarding)
                        image=guardRight;
                    else
                        image=rightidle;
                }
            }
            spriteNum=1;
            spriteCounter=0;
        }

        if (transparent){
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
