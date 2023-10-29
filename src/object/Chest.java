package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {

    Entity loot;
    boolean opened=false;

    public Chest(GamePanel gp, Entity loot){
        super(gp);
        this.loot=loot;

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

    @Override
    public void interact() {
        gp.gameState=gp.dialogueState;

        if(!opened){
            gp.uiH.currentDialogueText="";

            StringBuilder sb = new StringBuilder();
            sb.append("Sandıktan ").append(loot.displayedName).append(" çıktı!");

            if(gp.player.canObtainItem(loot)){
                gp.playSoundEffect(6);
                down1=image2;
                opened=true;
            }
            else{
                sb.append("\nAma üstün dolu...");
            }

            gp.uiH.currentDialogueText = sb.toString();
        }else{
            gp.uiH.currentDialogueText = "Sandık zaten açılmış kardes :P";
        }
    }
}
