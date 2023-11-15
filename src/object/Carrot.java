package object;

import entity.Entity;
import main.GamePanel;

public class Carrot extends Entity {

    public Carrot(GamePanel gp){
        super(gp);

        setDialogues();

        name = "Carrot";
        displayedName="Havuç";
        description="Tavşanlar bunu havada\nkapar.\n\n\n*3 can iyileştirir.*";
        type=typeConsumable;
        coin=10;
        stackable=true;

        down1=setImage("/objects/carrot",gp.tileSize,gp.tileSize);
    }

    public void setDialogues(){
        dialogues[0][0] = "Havucu tavşana verdin. Gopinin bu mükemmel lezzeti\nhak etmediğini düşündün.";
        dialogues[1][0] = "Havucu yedin. Tavşanların bu mükemmel lezzeti hak\netmediğini düşündün.";
    }

    @Override
    public boolean use(Entity entity){
        int objIndex = getDetected(entity, gp.obj, "Rabbit");

        if(objIndex!=999){
            startDialogue(this,0);
            gp.playSoundEffect(5);
            gp.obj[gp.currentMap][objIndex]=null;
            gp.player.inventory.add(new Rabbit(gp));
        }else{
            startDialogue(this,1);
            entity.hp+=3;
            if(entity.hp>entity.maxHp)
                entity.hp=entity.maxHp;
            gp.playSoundEffect(5);
        }
        return true;
    }
}