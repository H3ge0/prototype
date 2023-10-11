package object;

import entity.Entity;
import main.GamePanel;

public class Rabbit extends Entity {

    public Rabbit(GamePanel gp){
        super(gp);

        name = "Rabbit";
        displayedName = "Tavşan";
        description = "Bu neden envanterinde?";

        down1 = setImage("/objects/rabbit",gp.tileSize,gp.tileSize);

        collisionOn=true;

    }

}

