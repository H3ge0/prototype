package object;

import entity.Entity;
import main.GamePanel;

public class SilverCoin extends Entity {

    public static final String objName = "Silver Coin";

    public SilverCoin(GamePanel gp) {
        super(gp);

        type=typePickUpOnly;

        name=objName;
        displayedName="Gümüş Para";
        value=5;
        down1=setImage("/objects/silver_coin",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;
    }

    @Override
    public boolean use(Entity entity){
        gp.playSoundEffect(5);
        entity.coin+=value;

        return true;
    }
}
