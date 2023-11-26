package object;

import entity.Entity;
import main.GamePanel;

public class HealthPotion extends Entity {

    public static final String objName = "Health Potion";

    public HealthPotion(GamePanel gp){
        super(gp);

        name = objName;
        displayedName="Can İksiri";
        description="Havuç kadar sağlıklı\ndeğil...\n\n\n*2 can iyileştirir.*";
        coin=5;
        stackable=true;
        type=typeConsumable;

        setDialogues();
        down1 = setImage("/objects/health_potion",gp.tileSize,gp.tileSize);
    }

    public void setDialogues(){
        dialogues[0][0] = "Tadı iğrenç. En azından iyileştin...";
    }

    @Override
    public boolean use(Entity entity){
        startDialogue(this,0);
        entity.hp+=2;
        if(entity.hp>entity.maxHp)
            entity.hp=entity.maxHp;
        gp.playSoundEffect(1);
        return true;
    }

}
