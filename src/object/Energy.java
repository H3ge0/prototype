package object;

import entity.Entity;
import main.GamePanel;

public class Energy extends Entity {
    public Energy(GamePanel gp) {
        super(gp);

        name = "Energy";

        image1 = setImage("/ui/energy_full",gp.tileSize,gp.tileSize);
        image2 = setImage("/ui/energy_empty",gp.tileSize,gp.tileSize);
    }
}
