package object;

import entity.Entity;
import main.GamePanel;

public class Torch extends Entity {

    public static final String objName = "Torch";

    public Torch(GamePanel gp){
        super(gp);

        type=typeLightSource;
        name = objName;
        displayedName = "Meşale";
        description = "Karanlığa karşı on numara";
        coin=20;
        lightRadius=600;

        down1 = setImage("/objects/torch",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;
    }
}
