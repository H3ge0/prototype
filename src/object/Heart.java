package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {

    public Heart(GamePanel gp) {
        super(gp);

        name = "Chest";

        image1 = setImage("/ui/heart_full");
        image2 = setImage("/ui/heart_half");
        image3 = setImage("/ui/heart_empty");

    }
}
