package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Carrot extends Object {

    public Carrot(){

        name = "Carrot";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/carrot.png")));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
