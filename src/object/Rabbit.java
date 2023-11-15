package object;

import entity.Entity;
import main.GamePanel;

public class Rabbit extends Entity {

    public Rabbit(GamePanel gp){
        super(gp);

        setDialogues();

        type=typeObstacle;
        name = "Rabbit";
        displayedName = "Tavşan";
        description = "Bu neden burada?";
        coin=10;

        down1 = setImage("/objects/rabbit",gp.tileSize,gp.tileSize);

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

