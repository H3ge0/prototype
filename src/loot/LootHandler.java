package loot;

import entity.Entity;
import main.GamePanel;
import object.BronzeCoin;

import java.util.List;
import java.util.Random;

public class LootHandler {

    GamePanel gp;

    public LootHandler(GamePanel gp) {
        this.gp = gp;
    }

    public Entity dropLoot(List<Drop> lootTable){
        double rand = Math.random();
        Entity winner = new BronzeCoin(gp);

        int total=0;
        for(Drop drop:lootTable){
            total+=drop.chance;
        }

        rand *= total;

        System.out.println(rand);

        for(Drop drop:lootTable){
            if(rand<drop.chance){
                winner = gp.entityGenerator.getObject(drop.name);
                break;
            }
            rand-=drop.chance;
        }

        return winner;
    }

}
