package object;

import entity.Entity;
import main.GamePanel;

public class Candy extends Entity {

    public Candy(GamePanel gp){
        super(gp);

        name = "Candy";
        displayedName="Şeker";
        description="Çok tatlı duruyor...";

        down1 = setImage("/objects/candy",gp.tileSize,gp.tileSize);

    }

}
