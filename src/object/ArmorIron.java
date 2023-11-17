package object;

import entity.Entity;
import main.GamePanel;

public class ArmorIron extends Entity {

    public static final String objName = "Iron Armor";

    public ArmorIron(GamePanel gp) {
        super(gp);

        name=objName;
        displayedName="Demir Zırh";
        description="Parlak, ama yeteri kadar\ndeğil...";
        coin=20;
        type=typeArmor;

        down1=setImage("/objects/armor_iron",gp.tileSize,gp.tileSize);

        defenseValue=2;
    }
}
