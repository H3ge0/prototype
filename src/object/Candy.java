package object;

import entity.Entity;
import main.GamePanel;

public class Candy extends Entity {

    public Candy(GamePanel gp){
        super(gp);

        name = "Candy";
        displayedName="Şeker";
        description="Çok tatlı duruyor...\n\n\n*1 can iyileştirir.*";
        type=typeConsumable;

        down1 = setImage("/objects/candy",gp.tileSize,gp.tileSize);

    }

    @Override
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.uiH.currentDialogueText="Şekeri yedin. İçin enejiyle doldu.\n\n*Hızın Arttı*";
        entity.hp++;
        if(entity.hp>entity.maxHp)
            entity.hp=entity.maxHp;
        entity.speed++;
        gp.playSoundEffect(5);
    }

}
