package object;

import entity.Entity;
import main.GamePanel;

public class ArmorLeather extends Entity {

    public static final String objName = "Leather Armor";

    public ArmorLeather(GamePanel gp) {
        super(gp);

        name=objName;
        displayedName="Deri Zırh";
        description="Ormanın ortasında bulmuş\nolsan da iş görüyor.";
        coin=5;
        type=typeArmor;

        down1=setImage("/objects/armor_leather",gp.TILE_SIZE,gp.TILE_SIZE);

        defenseValue=1;
    }
}
