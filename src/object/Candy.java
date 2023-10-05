package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Candy extends Object{

    public Candy(){

        name = "Candy";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/candy.png")));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
