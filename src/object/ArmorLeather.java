package object;

import entity.Entity;
import main.GamePanel;

public class ArmorLeather extends Entity {
    public ArmorLeather(GamePanel gp) {
        super(gp);

        name="Leather Armor";
        displayedName="Deri Zırh";
        description="Sokakta bulsan da iş\ngörüyor.";

        down1=setImage("/objects/armor_leather",gp.tileSize,gp.tileSize);

        defenseValue=1;
    }
}
