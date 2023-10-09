package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TreasureChest extends Object{

    public TreasureChest(GamePanel gp){

        name = "Treasure Chest";

        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/treasure_chest.png")));
            image1 = utility.scaleImage(image1,gp.tileSize,gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

        collision = true;

    }

}
