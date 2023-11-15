package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Budi extends Entity{


    Random random;

    public Budi(GamePanel gp) {
        super(gp);
        random = new Random();

        direction = "down";
        speed=1;

        collisionBox = new Rectangle();
        collisionBox.x=8;
        collisionBox.y=16;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;
        collisionBox.width=32;
        collisionBox.height=32;

        dialogueSet=-1;

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
        dialogues[0][0] = "Meraba kanki.";
        dialogues[0][1] = "Ben budi! Tek işim yürümek!!!!";
        dialogues[0][2] = "Neden bilmiyorum :O";

        dialogues[1][0] = "Batı tarafında bir ev var...";
        dialogues[1][1] = "Ve bu evin sadece bodrumu var?!?";
        dialogues[1][2] = "Bu bodruma giren kimse geri dönemedi :(";
        dialogues[1][3] = "Belki de dönmek istemedi...";
        dialogues[1][4] = "Neyse demek istediğim oraya gitmeyi düşünüyorsan\nbirkaç kere daha düşün.";

        dialogues[2][0] = "Eğer yorulursan yukardaki gölden su içebilirsin.";
        dialogues[2][1] = "Ama nedense su içersen canavarlar tekrar doğuyor.";

        dialogues[3][0] = "Sana kolay gelsin...";
    }

    @Override
    public void setAction(){
        if(onPath){
            int goalCol=(gp.player.worldX+gp.player.collisionBox.x)/gp.tileSize;
            int goalRow=(gp.player.worldY+gp.player.collisionBox.y)/gp.tileSize;
            searchPath(goalCol,goalRow);
        }else{
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
    }

    public void speak(){
        if(gp.player.hp<=1){
            gp.playSoundEffect(1);
            gp.player.hp+=4;
            gp.uiH.currentDialogueText="Canın kalmamış kanki, al şu iksiri.\n\n\n*İyileştin!*";
        }else{
            startDialogue(this, dialogueSet);
            dialogueSet++;

            if(dialogues[dialogueSet][0]==null){
                dialogueSet--;
            }
        }
        facePlayer();
        onPath=!onPath;
    }

}
