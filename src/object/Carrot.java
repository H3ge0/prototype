package object;

import entity.Entity;
import main.GamePanel;

public class Carrot extends Entity {

    public static final String objName = "Carrot";

    public Carrot(GamePanel gp){
        super(gp);

        setDialogues();

        name = objName;
        displayedName="Havuç";
        description="Tavşanlar bunu havada\nkapar.\n\n\n*3 can iyileştirir.*";
        type=typeConsumable;
        coin=10;
        stackable=true;

        down1=setImage("/objects/carrot",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;
    }

    public void setDialogues(){
        dialogues[0][0] = "Havucu yedin. Tavşanların bu mükemmel lezzeti hak\netmediğini düşündün.";
    }

    @Override
    public boolean use(Entity entity){
        startDialogue(this,0);
        entity.hp+=3;
        if(entity.hp>entity.maxHp)
            entity.hp=entity.maxHp;
        gp.playSoundEffect(5);
        return true;
    }
}