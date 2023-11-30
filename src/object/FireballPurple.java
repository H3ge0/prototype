package object;

import entity.Entity;
import main.GamePanel;

public class FireballPurple extends Entity {

    public static final String objName = "Purple Fireball";

    public FireballPurple(GamePanel gp) {
        super(gp);

        name=objName;
        displayedName="Mor Ateş Topu";
        description="Hatalarını geri\nçevirebilir.\n\n\n(Potasyum).";
        coin=20;
        type=typeFireball;
        knockBackPower=3;

        down1=setImage("/objects/fireball_purple",gp.TILE_SIZE,gp.TILE_SIZE);

        attackArea.width=36;
        attackArea.height=36;

        attackValue=1;
    }
}
