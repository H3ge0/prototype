package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Energy extends Entity {

    public static final String objName = "Energy";

    public Energy(GamePanel gp) {
        super(gp);

        name = objName;
        value = 1;
        type=typePickUpOnly;

        down1 = setImage("/ui/energy_full",gp.TILE_SIZE *2/3,gp.TILE_SIZE *2/3);
        image1 = setImage("/ui/energy_full",gp.TILE_SIZE*2/3,gp.TILE_SIZE*2/3);
        image2 = setImage("/ui/energy_empty",gp.TILE_SIZE*2/3,gp.TILE_SIZE*2/3);
        left1 = setImage("/ui/energy_bar_full",gp.TILE_SIZE/3,gp.TILE_SIZE*2/3);
        left2 = setImage("/ui/energy_bar_empty",gp.TILE_SIZE/3,gp.TILE_SIZE*2/3);
        leftidle = setImage("/ui/energy_bar_icon",gp.TILE_SIZE*2/3,gp.TILE_SIZE*2/3);
        iconImage = down1;
    }

    @Override
    public boolean use(Entity entity) {
        gp.playSoundEffect(5);
        entity.energy+=value;
        return true;
    }

    @Override
    public void drawTheImage(Graphics2D g2, BufferedImage image, int x, int y) {
        g2.drawImage(image,x+8,y+8,null);
    }
}
