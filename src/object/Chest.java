package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {

    public static final String objName = "Chest";

    public Chest(GamePanel gp){
        super(gp);

        type=typeObstacle;
        name = objName;
        displayedName = "Sandık";
        description = "Birşeyler saklamak için on numara";
        image1 = setImage("/objects/chest",gp.TILE_SIZE,gp.TILE_SIZE);
        image2 = setImage("/objects/chest_open",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = image1;

        collisionOn=true;

        collisionBox.x = 4;
        collisionBox.y = 8;
        collisionBox.width = 40;
        collisionBox.height = 36;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;
    }

    public void setDialogues(){
        dialogues[0][0] = "Sandıktan "+loot.displayedName+" çıktı!";
        dialogues[1][0] = "Sandıktan "+loot.displayedName+" çıktı!\nAma üstün dolu...";
        dialogues[2][0] = "Sandık zaten açılmış kardes :P";
    }

    @Override
    public void setLoot(Entity loot){
        this.loot = loot;
        setDialogues();
    }

    @Override
    public void interact() {
        if(!opened){
            if(gp.player.canObtainItem(loot)){
                gp.playSoundEffect(6);
                down1=image2;
                opened=true;
                startDialogue(this,0);
            }
            else{
                startDialogue(this,1);
            }
        }else{
            startDialogue(this,2);
        }
    }
}
