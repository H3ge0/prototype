package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TreasureChest extends Object{

    public TreasureChest(){

        name = "Treasure Chest";

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/treasure_chest.png")));
        } catch (IOException e){
            e.printStackTrace();
        }

        collision = true;

    }

}
