package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Energy extends Entity {
    public Energy(GamePanel gp) {
        super(gp);

        name = "Energy";
        value = 1;
        type=typePickUpOnly;

        down1 = setImage("/ui/energy_full",gp.tileSize*2/3,gp.tileSize*2/3);
        image1 = setImage("/ui/energy_full",gp.tileSize,gp.tileSize);
        image2 = setImage("/ui/energy_empty",gp.tileSize,gp.tileSize);
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
