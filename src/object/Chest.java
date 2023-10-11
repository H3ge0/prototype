package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {

    public Chest(GamePanel gp){
        super(gp);

        name = "Chest";
        displayedName = "Sandık";
        description = "Birşeyler saklamak için on numara";

        down1 = setImage("/objects/chest",gp.tileSize,gp.tileSize);

        collisionOn=true;

    }

}
