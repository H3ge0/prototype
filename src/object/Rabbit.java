package object;

import entity.Entity;
import main.GamePanel;

public class Rabbit extends Entity {

    public Rabbit(GamePanel gp){
        super(gp);

        name = "Rabbit";

        down1 = setImage("/objects/rabbit");

        collisionOn=true;

    }

}

