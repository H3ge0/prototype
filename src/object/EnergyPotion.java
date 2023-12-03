package object;

import entity.Entity;
import main.GamePanel;

public class EnergyPotion extends Entity {

    public static final String objName = "Energy Potion";

    public EnergyPotion(GamePanel gp){
        super(gp);

        name = objName;
        displayedName="Enerji İksiri";
        description="Şeker kadar tatlı\ndeğil...\n\n\n*3 enerji doldurur.*";
        coin=5;
        stackable=true;
        type=typeConsumable;

        setDialogues();
        down1 = setImage("/objects/energy_potion",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    public void setDialogues(){
        dialogues[0][0] = "Tadı iğrenç. En azından enerjin doldu...";
    }

    @Override
    public boolean use(Entity entity){
        startDialogue(this,0);
        entity.energy+=3;
        if(entity.energy>entity.maxEnergy)
            entity.energy=entity.maxEnergy;
        gp.playSoundEffect(1);
        return true;
    }

}
