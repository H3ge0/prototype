package object;

import entity.Entity;
import main.GamePanel;

public class SleepPotion extends Entity {

    public static final String objName = "Sleep Potion";

    public SleepPotion(GamePanel gp){
        super(gp);

        name = objName;
        displayedName="Uyku İksiri";
        description="\"Sabaha kadar uyku\ngarantili!!\"";
        coin=5;
        stackable=true;
        type=typeConsumable;

        down1 = setImage("/objects/sleep_potion",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    @Override
    public boolean use(Entity entity){
        gp.gameState=gp.SLEEP_STATE;
        gp.player.getSleepingImage();
        gp.playSoundEffect(15);
        return true;
    }

}
