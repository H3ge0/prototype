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
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj, "Rabbit");

        if(objIndex!=999){
            gp.uiH.currentDialogueText="Havucu tavşana verdin. Gopinin bu mükemmel lezzeti\nhak etmediğini düşündün.";
            gp.playSoundEffect(5);
            gp.obj[gp.currentMap][objIndex]=null;
            gp.player.inventory.add(new Rabbit(gp));
        }else{
            gp.uiH.currentDialogueText="Havucu yedin. Tavşanların bu mükemmel lezzeti hak\netmediğini düşündün.";
            entity.hp+=3;
            if(entity.hp>entity.maxHp)
                entity.hp=entity.maxHp;
            gp.playSoundEffect(5);
        }
        return true;
    }
}