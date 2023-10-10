package object;

import entity.Entity;
import main.GamePanel;

public class Candy extends Entity {

    public Candy(GamePanel gp){
        super(gp);

        name = "Candy";

        down1 = setImage("/objects/candy",gp.tileSize,gp.tileSize);

    }

}
