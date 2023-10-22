package object;

import entity.Entity;
import main.GamePanel;

public class ArmorLeather extends Entity {
    public ArmorLeather(GamePanel gp) {
        super(gp);

        name="Leather Armor";
        displayedName="Deri Zırh";
        description="Ormanın ortasında bulmuş\nolsan da iş görüyor.";
        coin=5;
        isOneTime=true;
        type=typeArmor;

        down1=setImage("/objects/armor_leather",gp.tileSize,gp.tileSize);

        defenseValue=1;
    }
}
