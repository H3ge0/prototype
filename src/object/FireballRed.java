package object;

import entity.Entity;
import main.GamePanel;

public class FireballRed extends Entity {

    public static final String objName = "Red Fireball";

    public FireballRed(GamePanel gp) {
        super(gp);

        name=objName;
        displayedName="Kırmızı Ateş Topu";
        description="Gerkeçten birşeyleri\nyakabilir gibi duruyor.\n\n\n(Strontiyum).";
        coin=20;
        type=typeFireball;
        knockBackPower=3;

        down1=setImage("/objects/fireball_red",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;

        attackArea.width=36;
        attackArea.height=36;

        attackValue=1;
    }
}
