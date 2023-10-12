package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gp) {
        super(gp);

        name = "Heart";

        image1 = setImage("/ui/heart_full",gp.tileSize,gp.tileSize);
        image2 = setImage("/ui/heart_half",gp.tileSize,gp.tileSize);
        image3 = setImage("/ui/heart_empty",gp.tileSize,gp.tileSize);

    }
}
