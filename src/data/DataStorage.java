package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    //Player Stats
    int level;
    int maxHp;
    int hp;
    int maxEnergy;
    int energy;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;
    int mapNum;
    int playerWorldX;
    int playerWorldY;

    //Player Inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentFireballSlot;
    int currentArmorSlot;

    //Objects On Map
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootNames;
    boolean[][] mapObjectOpened;

    //Lighting
    int dayNumber;
    int dayCounter;
    float filterAlpha;
    int dayState;

}
