package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Rabbit extends Object {

    public Rabbit(GamePanel gp){

        name = "Rabbit";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/rabbit.png")));
            image = utility.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

        collision=true;

    }

}

