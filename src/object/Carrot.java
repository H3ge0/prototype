package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Carrot extends Object {

    public Carrot(GamePanel gp){

        name = "Carrot";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/carrot.png")));
            image1 = utility.scaleImage(image1,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
