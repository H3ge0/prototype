package entity;

import main.GamePanel;
import java.util.Random;

public class Budi extends Entity{


    Random random;

    public Budi(GamePanel gp) {
        super(gp);
        random = new Random();

        direction = "down";

        speed=1;

        getImages();
        setDialogues();
        actionLockCounter = random.nextInt(1,120);
    }

    public void getImages(){
        upidle = setImage("/npc/budi_up_idle",gp.tileSize,gp.tileSize);
        up1 = setImage("/npc/budi_up_1",gp.tileSize,gp.tileSize);
        up2 = setImage("/npc/budi_up_2",gp.tileSize,gp.tileSize);
        downidle = setImage("/npc/budi_down_idle",gp.tileSize,gp.tileSize);
        down1 = setImage("/npc/budi_down_1",gp.tileSize,gp.tileSize);
        down2 = setImage("/npc/budi_down_2",gp.tileSize,gp.tileSize);
        leftidle = setImage("/npc/budi_left_idle",gp.tileSize,gp.tileSize);
        left1 = setImage("/npc/budi_left_1",gp.tileSize,gp.tileSize);
        left2 = setImage("/npc/budi_left_2",gp.tileSize,gp.tileSize);
        rightidle = setImage("/npc/budi_right_idle",gp.tileSize,gp.tileSize);
        right1 = setImage("/npc/budi_right_1",gp.tileSize,gp.tileSize);
        right2 = setImage("/npc/budi_right_2",gp.tileSize,gp.tileSize);
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
        if(gp.player.hp<=1&&this.dialogueIndex>=1){
            gp.playSoundEffect(1);
            gp.player.hp+=2;
            gp.uiH.currentDialogueText="Canın kalmamış kanki, al şu iksiri.\n\n\n*İyileştin!*";
            switch(gp.player.direction){
                case "up" -> direction = "down";
                case "down" -> direction = "up";
                case "left" -> direction = "right";
                case "right" -> direction = "left";
            }
        }else
            super.speak();
    }

}
