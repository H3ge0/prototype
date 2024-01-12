package object;

import entity.Entity;
import main.GamePanel;

public class GoldCoin extends Entity {

    public static final String objName = "Gold Coin";

    public GoldCoin(GamePanel gp) {
        super(gp);

        type=typePickUpOnly;

        name=objName;
        displayedName="Altın Para";
        value=10;
        down1=setImage("/objects/gold_coin",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;
    }

    @Override
    public boolean use(Entity entity){
        gp.playSoundEffect(5);
        entity.coin+=value;

        return true;
    }
}
