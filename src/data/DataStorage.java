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
    int defaultSpeed;

    //Player Inventory
    ArrayList<String> playerItemNames = new ArrayList<>();
    ArrayList<Integer> playerItemAmounts = new ArrayList<>();
    String currentFireball;
    String currentArmor;
    String currentProjectile;
    String currentLight;
    //Bobo Inventory
    boolean boboExists;
    ArrayList<String> boboItemNames = new ArrayList<>();
    ArrayList<Integer> boboItemAmounts = new ArrayList<>();

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

    //Progress
    boolean ipogDefeated;

}
