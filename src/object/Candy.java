package object;

import entity.Entity;
import main.GamePanel;

public class Candy extends Entity {

    public Candy(GamePanel gp){
        super(gp);

        setDialogues();

        name = "Candy";
        displayedName="Şeker";
        description="Çok tatlı duruyor...\n\n\n\n*Enerjini doldurur.*";
        coin=15;
        stackable=true;
        type=typeConsumable;

        down1 = setImage("/objects/candy",gp.tileSize,gp.tileSize);
    }

    public void setDialogues(){
        dialogues[0][0] = "Şekeri yedin. İçin enejiyle doldu.\n\n*Hızın Arttı*\n*Enerjin doldu*";
        dialogues[1][0] = "Şekeri yedin. İçin enejiyle doldu.\n\n\n*Enerjin doldu*";
    }

    @Override
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        if(gp.player.speed<6){
            startDialogue(this,0);
        } else
            startDialogue(this,1);
        entity.energy=entity.maxEnergy;
        entity.speed++;
        gp.playSoundEffect(5);
        return true;
    }

}
