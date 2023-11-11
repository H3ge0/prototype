package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;

        switch (itemName){
            case "Iron Armor" -> obj = new ArmorIron(gp);
            case "Leather Armor" -> obj = new ArmorLeather(gp);
            case "Candy" -> obj = new Candy(gp);
            case "Carrot" -> obj = new Carrot(gp);
            case "Chest" -> obj = new Chest(gp);
            case "Orange Fireball" -> obj = new FireballOrange(gp);
            case "Purple Fireball" -> obj = new FireballPurple(gp);
            case "Red Fireball" -> obj = new FireballRed(gp);
            case "Rabbit" -> obj = new Rabbit(gp);
            case "Sleep Potion" -> obj = new SleepPotion(gp);
            case "Throwing Knife" -> obj = new ThrowingKnife(gp);
            case "Torch" -> obj = new Torch(gp);
            case "Treasure Chest" -> obj = new TreasureChest(gp);
            case "Heart" -> obj = new Heart(gp);
            case "Energy" -> obj = new Energy(gp);
            case "Bronze Coin" -> obj = new BronzeCoin(gp);
            case "Silver Coin" -> obj = new SilverCoin(gp);
        }

        return obj;
    }

    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));

            DataStorage ds = new DataStorage();

            //Player Stats
            ds.level = gp.player.level;
            ds.maxHp = gp.player.maxHp;
            ds.hp = gp.player.hp;
            ds.maxEnergy = gp.player.maxEnergy;
            ds.energy = gp.player.energy;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            ds.mapNum = gp.currentMap;
            ds.playerWorldX = gp.player.worldX;
            ds.playerWorldY = gp.player.worldY;

            //Player Inventory
            for(Entity entity:gp.player.inventory){
                ds.itemNames.add(entity.name);
                ds.itemAmounts.add(entity.amount);
            }

            //Current Items
            ds.currentFireballSlot = gp.player.getCurrentFireballSlot();
            ds.currentArmorSlot = gp.player.getCurrentArmorSlot();

            //Objects On Map
            ds.mapObjectNames = new String[gp.mapAmount][gp.obj[1].length];
            ds.mapObjectWorldX = new int[gp.mapAmount][gp.obj[1].length];
            ds.mapObjectWorldY = new int[gp.mapAmount][gp.obj[1].length];
            ds.mapObjectLootNames = new String[gp.mapAmount][gp.obj[1].length];
            ds.mapObjectOpened = new boolean[gp.mapAmount][gp.obj[1].length];

            for(int mapNum=0; mapNum<gp.mapAmount; mapNum++){
                for(int i=0; i<gp.obj[1].length; i++){
                    if(gp.obj[mapNum][i]==null){
                        ds.mapObjectNames[mapNum][i] = "N/A";
                    }else{
                        ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
                        ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
                        ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
                        if(gp.obj[mapNum][i].loot!=null){
                            ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
                    }
                }
            }

            //Lighting
            ds.dayNumber = gp.environmentH.lighting.dayNumber;
            ds.dayCounter = gp.environmentH.lighting.dayCounter;
            ds.filterAlpha = gp.environmentH.lighting.filterAlpha;
            ds.dayState = gp.environmentH.lighting.dayState;

            oos.writeObject(ds);
        }
        catch(Exception e){
            System.out.println("Save exception!:(");
        }
    }

    public void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));

            DataStorage ds = (DataStorage) ois.readObject();

            //Status
            gp.player.level = ds.level;
            gp.player.maxHp = ds.maxHp;
            gp.player.hp = ds.hp;
            gp.player.maxEnergy = ds.maxEnergy;
            gp.player.energy = ds.energy;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            gp.currentMap = ds.mapNum;
            gp.player.worldX = ds.playerWorldX;
            gp.player.worldY = ds.playerWorldY;

            //Player Inventory
            gp.player.inventory.clear();
            for(int i=0;i<ds.itemNames.size();i++){
                gp.player.inventory.add(getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount=ds.itemAmounts.get(i);
            }

            //Current Items
            gp.player.currentFireball = gp.player.inventory.get(ds.currentFireballSlot);
            gp.player.currentArmor = gp.player.inventory.get(ds.currentArmorSlot);
            gp.player.attack = gp.player.getAttack();
            gp.player.defense = gp.player.getDefense();
            gp.player.getAttackImages();

            //Objects On Map
            for(int mapNum=0; mapNum<gp.mapAmount; mapNum++){
                for(int i=0; i<gp.obj[1].length; i++){
                    if(ds.mapObjectNames[mapNum][i].equals("N/A")){
                        gp.obj[mapNum][i] = null;
                    }else{
                        gp.obj[mapNum][i] = getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if(ds.mapObjectLootNames[mapNum][i]!=null){
                            gp.obj[mapNum][i].loot = getObject(ds.mapObjectLootNames[mapNum][i]);
                        }
                        gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
                        if(gp.obj[mapNum][i].opened){
                            gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
                        }
                    }
                }
            }

            //Lighting
            gp.environmentH.lighting.dayNumber = ds.dayNumber;
            gp.environmentH.lighting.dayCounter = ds.dayCounter;
            gp.environmentH.lighting.filterAlpha = ds.filterAlpha;
            gp.environmentH.lighting.dayState = ds.dayState;
        }
        catch(Exception e){
            System.out.println("Load exception!:(");
        }
    }

}
