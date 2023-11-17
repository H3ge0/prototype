package main;

import entity.Entity;
import object.*;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }

    public Entity getObject(String itemName){
        Entity obj = null;

        switch (itemName){
            case ArmorIron.objName -> obj = new ArmorIron(gp);
            case ArmorLeather.objName -> obj = new ArmorLeather(gp);
            case FireballOrange.objName -> obj = new FireballOrange(gp);
            case FireballPurple.objName -> obj = new FireballPurple(gp);
            case FireballRed.objName -> obj = new FireballRed(gp);
            case Torch.objName -> obj = new Torch(gp);
            case SleepPotion.objName -> obj = new SleepPotion(gp);
            case Candy.objName -> obj = new Candy(gp);
            case Carrot.objName -> obj = new Carrot(gp);
            case Rabbit.objName -> obj = new Rabbit(gp);
            case Chest.objName -> obj = new Chest(gp);
            case Heart.objName -> obj = new Heart(gp);
            case Energy.objName -> obj = new Energy(gp);
            case BronzeCoin.objName -> obj = new BronzeCoin(gp);
            case SilverCoin.objName -> obj = new SilverCoin(gp);
            case ThrowingKnife.objName -> obj = new ThrowingKnife(gp);
            case Rock.objName -> obj = new Rock(gp);
        }

        return obj;
    }

}
