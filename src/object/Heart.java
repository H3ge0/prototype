package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Heart extends Entity {

    public static final String objName = "Heart";

    public Heart(GamePanel gp) {
        super(gp);

        name = objName;
        value = 2;
        type=typePickUpOnly;

        down1 = setImage("/ui/heart_full",gp.TILE_SIZE *2/3,gp.TILE_SIZE *2/3);
        image1 = setImage("/ui/heart_full",gp.TILE_SIZE,gp.TILE_SIZE);
        image2 = setImage("/ui/heart_half",gp.TILE_SIZE,gp.TILE_SIZE);
        image3 = setImage("/ui/heart_empty",gp.TILE_SIZE,gp.TILE_SIZE);

    }

    @Override
    public boolean use(Entity entity) {
        gp.playSoundEffect(5);
        entity.hp+=value;
        return true;
    }

    @Override
    public void drawTheImage(Graphics2D g2, BufferedImage image, int x, int y) {
        g2.drawImage(image,x+8,y+8,null);
    }
}
