package object;

import entity.Entity;
import main.GamePanel;

public class Rabbit extends Entity {

    public Rabbit(GamePanel gp){
        super(gp);

        type=typeObstacle;
        name = "Rabbit";
        displayedName = "Tavşan";
        description = "Bu neden burada?";
        coin=10;

        down1 = setImage("/objects/rabbit",gp.tileSize,gp.tileSize);

        collisionOn=true;

    }

    @Override
    public void interact() {
        gp.gameState=gp.dialogueState;
        gp.uiH.currentDialogueText="Havuç lazım...";
    }
}

