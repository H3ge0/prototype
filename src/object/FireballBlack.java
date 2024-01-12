package object;

import entity.Entity;
import main.GamePanel;

public class FireballBlack extends Entity {

    public static final String objName = "Black Fireball";

    public FireballBlack(GamePanel gp) {
        super(gp);

        name=objName;
        displayedName="Siyah Ateş Topu";
        description="Bu kadar sert alev\ntopu mu olurmuş?!.\n\n\n(Sodyum).";
        coin=30;
        type=typeFireball;
        knockBackPower=3;

        down1=setImage("/objects/fireball_black",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;

        attackArea.width=36;
        attackArea.height=36;

        attackValue=1;
    }
}
