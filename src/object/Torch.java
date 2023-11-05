package object;

import entity.Entity;
import main.GamePanel;

public class Torch extends Entity {

    public Torch(GamePanel gp){
        super(gp);

        type=typeLightSource;
        name = "Torch";
        displayedName = "Meşale";
        description = "Karanlığa karşı on numara";
        coin=20;
        lightRadius=500;

        down1 = setImage("/objects/torch",gp.tileSize,gp.tileSize);
    }
}
