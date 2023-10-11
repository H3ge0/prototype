package object;

import entity.Entity;
import main.GamePanel;

public class Carrot extends Entity {

    public Carrot(GamePanel gp){
        super(gp);

        name = "Carrot";
        displayedName="Havuç";
        description="Tavşanlar bunu havada\nkapar.";

        down1=setImage("/objects/carrot",gp.tileSize,gp.tileSize);
    }

}