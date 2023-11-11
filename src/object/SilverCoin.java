package object;

import entity.Entity;
import main.GamePanel;

public class SilverCoin extends Entity {
    public SilverCoin(GamePanel gp) {
        super(gp);

        type=typePickUpOnly;

        name="Silver Coin";
        displayedName="Gümüş Para";
        value=5;
        down1=setImage("/objects/silver_coin",gp.tileSize,gp.tileSize);
    }

    @Override
    public boolean use(Entity entity){
        gp.playSoundEffect(5);
        entity.coin+=value;

        return true;
    }
}
