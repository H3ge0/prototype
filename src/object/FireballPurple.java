package object;

import entity.Entity;
import main.GamePanel;

public class FireballPurple extends Entity {
    public FireballPurple(GamePanel gp) {
        super(gp);

        name="Purple Fireball";
        displayedName="Mor Ateş Topu";
        description="Hatalarını geri\nçevirebilir.\n\n\n(Potasyum).";
        type=typeFireball;

        down1=setImage("/objects/fireball_purple",gp.tileSize,gp.tileSize);

        attackArea.width=36;
        attackArea.height=36;

        attackValue=1;
    }
}
