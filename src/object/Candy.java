package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Candy extends Object{

    public Candy(GamePanel gp){

        name = "Candy";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/candy.png")));
            image = utility.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
