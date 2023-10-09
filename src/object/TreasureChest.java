package object;

import entity.Entity;
import main.GamePanel;

public class TreasureChest extends Entity {

    public TreasureChest(GamePanel gp){
        super(gp);

        name = "Treasure Chest";

        down1 = setImage("/objects/treasure_chest");

        collisionOn = true;

    }

}
