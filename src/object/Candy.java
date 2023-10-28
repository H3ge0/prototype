package object;

import entity.Entity;
import main.GamePanel;

public class Candy extends Entity {

    public Candy(GamePanel gp){
        super(gp);

        name = "Candy";
        displayedName="Şeker";
        description="Çok tatlı duruyor...\n\n\n\n*Enerjini doldurur.*";
        coin=15;
        type=typeConsumable;

        down1 = setImage("/objects/candy",gp.tileSize,gp.tileSize);

    }

    @Override
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        if(gp.player.speed<6){
            gp.uiH.currentDialogueText="Şekeri yedin. İçin enejiyle doldu.\n\n*Hızın Arttı*\n*Enerjin doldu*";
        } else
            gp.uiH.currentDialogueText="Şekeri yedin. İçin enejiyle doldu.\n\n\n*Enerjin doldu*";
        entity.energy=entity.maxEnergy;
        entity.speed++;
        gp.playSoundEffect(5);
        return true;
    }

}
