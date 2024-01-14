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
        dialogues[0][0] = "Havuç lazım.";
        dialogues[1][0] = "Havucu tavşana verdin. Gopinin bu mükemmel lezzeti\nhak etmediğini düşündün.";
    }

    @Override
    public boolean interact() {
        int carrotIndex = playerHasItem("Carrot");

        if(carrotIndex!=999){
            startDialogue(this,1);
            gp.playSoundEffect(5);
            gp.player.inventory.add(new Rabbit(gp));
            gp.player.inventory.remove(carrotIndex);
            return true;
        }else{
            startDialogue(this,0);
            return false;
        }
    }
}

