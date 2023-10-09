package entity;

import main.GamePanel;
import java.util.Random;

public class Budi extends Entity{

    public Budi(GamePanel gp) {
        super(gp);

        direction = "down";

        speed=1;

        getImages();
        setDialogues();

    }

    public void getImages(){
        upidle = setImage("/npc/budi_up_idle");
        up1 = setImage("/npc/budi_up_1");
        up2 = setImage("/npc/budi_up_2");
        downidle = setImage("/npc/budi_down_idle");
        down1 = setImage("/npc/budi_down_1");
        down2 = setImage("/npc/budi_down_2");
        leftidle = setImage("/npc/budi_left_idle");
        left1 = setImage("/npc/budi_left_1");
        left2 = setImage("/npc/budi_left_2");
        rightidle = setImage("/npc/budi_right_idle");
        right1 = setImage("/npc/budi_right_1");
        right2 = setImage("/npc/budi_right_2");
    }

    public void setDialogues(){
        dialogues[0] = "Meraba kanki.";
        dialogues[1] = "Demek o hazine için geldin...";
        dialogues[2] = "O hazine fazla korumalı. O mağaradakileri\nkendi gözümle gördüm.";
        dialogues[3] = "Kesinlikle normal değiller...";
        dialogues[4] = "Dikkat et kendine.";
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

    public void speak(){
        super.speak();
    }

}