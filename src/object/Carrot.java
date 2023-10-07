package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Carrot extends Object {

    public Carrot(GamePanel gp){

        name = "Carrot";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/carrot.png")));
            image = utility.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
