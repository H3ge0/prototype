package object;

import entity.Entity;
import main.GamePanel;

public class IronDoor extends Entity {

    public static final String objName = "Iron Door";

    public IronDoor(GamePanel gp){
        super(gp);

        setDialogues();

        type = typeObstacle;
        name = objName;
        displayedName = "Demir Kapı";
        description = "Bu ne kardeş?";
        coin=10;

        down1 = setImage("/objects/dungeon_door",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;

        collisionOn=true;

    }

    public void setDialogues(){
        dialogues[0][0] = "Kırılma ihtimali yok...";
    }

    @Override
    public boolean interact() {
        startDialogue(this,0);
        return false;
    }
}
