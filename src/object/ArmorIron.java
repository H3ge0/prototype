package object;

import entity.Entity;
import main.GamePanel;

public class ArmorIron extends Entity {
    public ArmorIron(GamePanel gp) {
        super(gp);

        name="Iron Armor";
        displayedName="Demir Zırh";
        description="Parlak, ama yeteri kadar\ndeğil...";
        coin=20;
        type=typeArmor;

        down1=setImage("/objects/armor_iron",gp.tileSize,gp.tileSize);

        defenseValue=2;
    }
}
