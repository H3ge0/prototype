package object;

import entity.Entity;
import main.GamePanel;

public class FireballOrange extends Entity {
    public FireballOrange(GamePanel gp) {
        super(gp);

        name="Orange Fireball";
        displayedName="Turuncu Ateş Topu";
        description="Doğduğundan beri\nkullandığın ateş topu.\n\n\n(Kalsiyum).";
        coin=5;
        isOneTime=true;
        type=typeFireball;

        down1=setImage("/objects/fireball_orange",gp.tileSize,gp.tileSize);

        attackArea.width=36;
        attackArea.height=36;

        attackValue=1;
    }
}
