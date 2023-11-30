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
        upidle = setImage("/player/gopi_up_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        up1 = setImage("/player/gopi_up_1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setImage("/player/gopi_up_2",gp.TILE_SIZE,gp.TILE_SIZE);
        downidle = setImage("/player/gopi_down_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setImage("/player/gopi_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setImage("/player/gopi_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
        leftidle = setImage("/player/gopi_left_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setImage("/player/gopi_left_1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setImage("/player/gopi_left_2",gp.TILE_SIZE,gp.TILE_SIZE);
        rightidle = setImage("/player/gopi_right_idle",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setImage("/player/gopi_right_1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setImage("/player/gopi_right_2",gp.TILE_SIZE,gp.TILE_SIZE);
    }
}
