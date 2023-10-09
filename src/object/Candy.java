package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Candy extends Object{

    public Candy(GamePanel gp){

        name = "Candy";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/candy.png")));
            image1 = utility.scaleImage(image1,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
