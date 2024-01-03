package main;

import entity.Entity;
import object.*;

import java.util.Random;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;

        int i = new Random().nextInt(14)+1;

        switch (itemName){
            case "HP Energy" -> {
                if (gp.player.hp == gp.player.maxHp && gp.player.energy == gp.player.maxEnergy) {
                    obj = new BronzeCoin(gp);
                }

                //Only full hp
                else if(gp.player.hp == gp.player.maxHp){
                    if(i<=8){
                        obj = new BronzeCoin(gp);
                    } else{
                        obj = new Energy(gp);
                    }
                }

                //Only full energy
                else if(gp.player.energy == gp.player.maxEnergy){
                    if(i<=8){
                        obj = new BronzeCoin(gp);
                    } else{
                        obj = new Heart(gp);
                    }
                }

                //None of them are full
                else{
                    if(i<=8){
                        obj = new BronzeCoin(gp);
                    }else if(i<=11){
                        obj = new Heart(gp);
                    }else{
                        obj = new Energy(gp);
                    }
                }
            }
            case ArmorIron.objName -> obj = new ArmorIron(gp);
            case ArmorLeather.objName -> obj = new ArmorLeather(gp);
            case FireballOrange.objName -> obj = new FireballOrange(gp);
            case FireballPurple.objName -> obj = new FireballPurple(gp);
            case FireballRed.objName -> obj = new FireballRed(gp);
            case FireballBlack.objName -> obj = new FireballBlack(gp);
            case Torch.objName -> obj = new Torch(gp);
            case SleepPotion.objName -> obj = new SleepPotion(gp);
            case HealthPotion.objName -> obj = new HealthPotion(gp);
            case EnergyPotion.objName -> obj = new EnergyPotion(gp);
            case Candy.objName -> obj = new Candy(gp);
            case Carrot.objName -> obj = new Carrot(gp);
            case Rabbit.objName -> obj = new Rabbit(gp);
            case IronDoor.objName -> obj = new IronDoor(gp);
            case Chest.objName -> obj = new Chest(gp);
            case Heart.objName -> obj = new Heart(gp);
            case Energy.objName -> obj = new Energy(gp);
            case BronzeCoin.objName -> obj = new BronzeCoin(gp);
            case SilverCoin.objName -> obj = new SilverCoin(gp);
            case GoldCoin.objName -> obj = new GoldCoin(gp);
            case ThrowingKnife.objName -> obj = new ThrowingKnife(gp);
            case Rock.objName -> obj = new Rock(gp);
        }

        return obj;
    }

}
