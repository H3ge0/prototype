package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends Object{

    public Chest(GamePanel gp){

        name = "Chest";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            image1 = utility.scaleImage(image1,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

        collision=true;

    }

}
