package object;

import entity.Entity;
import main.GamePanel;

public class Carrot extends Entity {

    public Carrot(GamePanel gp){
        super(gp);

        name = "Carrot";
        displayedName="Havuç";
        description="Tavşanlar bunu havada\nkapar.\n\n\n*3 can iyileştirir.*";
        type=typeConsumable;
        coin=10;

        down1=setImage("/objects/carrot",gp.tileSize,gp.tileSize);
    }

    @Override
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.uiH.currentDialogueText="Havucu yedin. Tavşanların bu mükemmel\nlezzeti hak etmediğini düşündün.";
        entity.hp+=3;
        if(entity.hp>entity.maxHp)
            entity.hp=entity.maxHp;
        gp.playSoundEffect(5);
    }
}