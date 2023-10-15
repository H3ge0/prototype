package object;

import entity.Entity;
import main.GamePanel;

public class BronzeCoin extends Entity {
    public BronzeCoin(GamePanel gp) {
        super(gp);

        type=typePickUpOnly;

        name="Bronze Coin";
        displayedName="Bronz Para";
        value=1;
        down1=setImage("/objects/bronze_coin",gp.tileSize,gp.tileSize);
    }

    @Override
    public void use(Entity entity){
        gp.playSoundEffect(5);
        entity.coin+=value;
    }
}
