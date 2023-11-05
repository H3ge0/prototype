package object;

import entity.Entity;
import main.GamePanel;

public class SleepPotion extends Entity {

    public SleepPotion(GamePanel gp){
        super(gp);

        name = "Sleep Potion";
        displayedName="Uyku İksiri";
        description="\"Sabaha kadar uyku\ngarantili!!\"";
        coin=5;
        stackable=true;
        type=typeConsumable;

        down1 = setImage("/objects/sleep_potion",gp.tileSize,gp.tileSize);
    }

    @Override
    public boolean use(Entity entity){
        gp.gameState=gp.sleepState;
        gp.player.getSleepingImage();
        gp.playSoundEffect(15);
        return true;
    }

}
