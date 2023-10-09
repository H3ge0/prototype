package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Heart extends Object{

    GamePanel gp;


    public Heart(GamePanel gp) {

        name = "Chest";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart_full.png")));
            image1 = utility.scaleImage(image1, gp.tileSize, gp.tileSize);

            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart_half.png")));
            image2 = utility.scaleImage(image2, gp.tileSize, gp.tileSize);

            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/heart_empty.png")));
            image3 = utility.scaleImage(image3, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
