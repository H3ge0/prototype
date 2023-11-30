package data;

import entity.Entity;
import main.GamePanel;

import java.io.*;

public class SaveLoad {

    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
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
            ds.defaultSpeed = gp.player.defaultSpeed;

            //Player Inventory
            for(Entity entity:gp.player.inventory){
                ds.playerItemNames.add(entity.name);
                ds.playerItemAmounts.add(entity.amount);
            }

            //Current Items
            ds.currentFireballSlot = gp.player.getCurrentFireballSlot();
            ds.currentArmorSlot = gp.player.getCurrentArmorSlot();
            ds.currentLightSlot = gp.player.getCurrentLightSlot();

            if(gp.npcs[1][0]!=null){
                ds.boboExists = true;
                for(Entity entity:gp.npcs[1][0].inventory){
                    ds.boboItemNames.add(entity.name);
                    ds.boboItemAmounts.add(entity.amount);
                }
            }else{
                ds.boboExists=false;
            }

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

            //Progress
            ds.ipogDefeated = Progress.ipogDefeated;

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
            gp.player.defaultSpeed=ds.defaultSpeed;
            gp.player.speed = gp.player.defaultSpeed;

            //Player Inventory
            gp.player.inventory.clear();
            for(int i=0;i<ds.playerItemNames.size();i++){
                gp.player.inventory.add(gp.entityGenerator.getObject(ds.playerItemNames.get(i)));
                gp.player.inventory.get(i).amount=ds.playerItemAmounts.get(i);
            }

            //Current Items
            gp.player.currentFireball = gp.player.inventory.get(ds.currentFireballSlot);
            gp.player.currentArmor = gp.player.inventory.get(ds.currentArmorSlot);
            if(ds.currentLightSlot!=-1){
                gp.player.currentLightSource = gp.player.inventory.get(ds.currentLightSlot);
            }
            gp.player.lightUpdated=true;
            gp.player.attack = gp.player.getAttack();
            gp.player.defense = gp.player.getDefense();
            gp.player.getAttackImages();

            //Bobo Inventory
            if(ds.boboExists){
                gp.npcs[1][0].inventory.clear();
                for(int i=0;i<ds.boboItemNames.size();i++){
                    gp.npcs[1][0].inventory.add(gp.entityGenerator.getObject(ds.boboItemNames.get(i)));
                    gp.npcs[1][0].inventory.get(i).amount=ds.boboItemAmounts.get(i);
                }
            }

            //Objects On Map
            for(int mapNum=0; mapNum<gp.mapAmount; mapNum++){
                for(int i=0; i<gp.obj[1].length; i++){
                    if(ds.mapObjectNames[mapNum][i].equals("N/A")){
                        gp.obj[mapNum][i] = null;
                    }else{
                        gp.obj[mapNum][i] = gp.entityGenerator.getObject(ds.mapObjectNames[mapNum][i]);
                        gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
                        gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
                        if(ds.mapObjectLootNames[mapNum][i]!=null){
                            gp.obj[mapNum][i].setLoot(gp.entityGenerator.getObject(ds.mapObjectLootNames[mapNum][i]));
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

            //Progress
            Progress.ipogDefeated = ds.ipogDefeated;
        }
        catch(Exception e){
            System.out.println("Load exception!:(");
        }
    }

}
