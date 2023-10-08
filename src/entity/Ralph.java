package entity;

import main.GamePanel;
import java.awt.*;
import java.util.Random;

public class Ralph extends Entity{

    public Ralph(GamePanel gp) {
        super(gp);

        direction = "down";
        directionX = "null";
        directionY = "null";

        speed=1;

        getImages();

    }

    public void getImages(){
        upidle = setImage("/npc/ralph_up_idle");
        up1 = setImage("/npc/ralph_up_1");
        up2 = setImage("/npc/ralph_up_2");
        downidle = setImage("/npc/ralph_down_idle");
        down1 = setImage("/npc/ralph_down_1");
        down2 = setImage("/npc/ralph_down_2");
        leftidle = setImage("/npc/ralph_left_idle");
        left1 = setImage("/npc/ralph_left_1");
        left2 = setImage("/npc/ralph_left_2");
        rightidle = setImage("/npc/ralph_right_idle");
        right1 = setImage("/npc/ralph_right_1");
        right2 = setImage("/npc/ralph_right_2");
    }

    @Override
    public void setAction(){

        actionLockCounter++;

        if (actionLockCounter==120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i<=25){
                direction="up";
            } else if (i<=50){
                direction="down";
            } else if (i<=75){
                direction="left";
            } else {
                direction="right";
            }
            actionLockCounter=0;
        }
    }
}
