package object;

import entity.Entity;
import main.GamePanel;

public class FireballOrange extends Entity {

    public static final String objName = "Orange Fireball";

    public FireballOrange(GamePanel gp) {
        super(gp);

        name=objName;
        displayedName="Turuncu Ateş Topu";
        description="Doğduğundan beri\nkullandığın ateş topu.\nYine de gayet kuvvetli.\n\n(Kalsiyum).";
        coin=5;
        type=typeFireball;
        knockBackPower=10;

        down1=setImage("/objects/fireball_orange",gp.TILE_SIZE,gp.TILE_SIZE);
        iconImage = down1;

        attackArea.width=36;
        attackArea.height=36;

        attackValue=1;
    }
}
