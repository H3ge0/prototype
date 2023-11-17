package object;

import entity.Entity;
import main.GamePanel;

public class BronzeCoin extends Entity {

    public static final String objName = "Bronze Coin";

    public BronzeCoin(GamePanel gp) {
        super(gp);

        type=typePickUpOnly;

        name=objName;
        displayedName="Bronz Para";
        value=1;
        down1=setImage("/objects/bronze_coin",gp.tileSize,gp.tileSize);
    }

    @Override
    public boolean use(Entity entity){
        gp.playSoundEffect(5);
        entity.coin+=value;

        return true;
    }
}
