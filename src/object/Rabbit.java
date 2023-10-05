package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Rabbit extends Object {

    public Rabbit(){

        name = "Rabbit";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/rabbit.png")));
        } catch (IOException e){
            e.printStackTrace();
        }

        collision=true;

    }

}

