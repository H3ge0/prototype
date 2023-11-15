package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {

    public Chest(GamePanel gp){
        super(gp);

        type=typeObstacle;
        name = "Chest";
        displayedName = "Sandık";
        description = "Birşeyler saklamak için on numara";
        image1 = setImage("/objects/chest",gp.tileSize,gp.tileSize);
        image2 = setImage("/objects/chest_open",gp.tileSize,gp.tileSize);
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
