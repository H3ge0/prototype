package object;

import entity.Entity;
import main.GamePanel;

public class FireballOrange extends Entity {
    public FireballOrange(GamePanel gp) {
        super(gp);

        name="Orange Fireball";
        displayedName="Turuncu Ateş Topu";
        description="Doğduğundan beri\nkullandığın ateş topu.";

        down1=setImage("/objects/fireball_orange",gp.tileSize,gp.tileSize);

        attackValue=1;
    }
}
