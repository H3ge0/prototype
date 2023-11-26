package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{

    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp) {
        super(gp);

        name = npcName;

        getImages();
    }

    public void getImages(){
        upidle = setImage("/player/gopi_up_idle",gp.tileSize,gp.tileSize);
        up1 = setImage("/player/gopi_up_1",gp.tileSize,gp.tileSize);
        up2 = setImage("/player/gopi_up_2",gp.tileSize,gp.tileSize);
        downidle = setImage("/player/gopi_down_idle",gp.tileSize,gp.tileSize);
        down1 = setImage("/player/gopi_down_1",gp.tileSize,gp.tileSize);
        down2 = setImage("/player/gopi_down_2",gp.tileSize,gp.tileSize);
        leftidle = setImage("/player/gopi_left_idle",gp.tileSize,gp.tileSize);
        left1 = setImage("/player/gopi_left_1",gp.tileSize,gp.tileSize);
        left2 = setImage("/player/gopi_left_2",gp.tileSize,gp.tileSize);
        rightidle = setImage("/player/gopi_right_idle",gp.tileSize,gp.tileSize);
        right1 = setImage("/player/gopi_right_1",gp.tileSize,gp.tileSize);
        right2 = setImage("/player/gopi_right_2",gp.tileSize,gp.tileSize);
    }
}
