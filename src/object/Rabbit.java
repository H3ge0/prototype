package object;

import entity.Entity;
import main.GamePanel;

public class Rabbit extends Entity {

    public static final String objName = "Rabbit";

    public Rabbit(GamePanel gp){
        super(gp);

        setDialogues();

        type=typeObstacle;
        name = objName;
        displayedName = "Tavşan";
        description = "Bu neden burada?";
        coin=10;

        down1 = setImage("/objects/rabbit",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;

        collisionOn=true;

    }

    public void setDialogues(){
        dialogues[0][0] = "Havuç lazım...";
    }

    @Override
    public void interact() {
        startDialogue(this,0);
    }
}

