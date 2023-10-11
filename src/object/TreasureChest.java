package object;

import entity.Entity;
import main.GamePanel;

public class TreasureChest extends Entity {

    public TreasureChest(GamePanel gp){
        super(gp);

        name = "Treasure Chest";
        displayedName = "Hazine Sandığı";
        description = "Birşeyler gizlemek için on numara";

        down1 = setImage("/objects/treasure_chest",gp.tileSize,gp.tileSize);

        collisionOn = true;

    }

}
