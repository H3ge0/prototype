package entity;

import main.GamePanel;
import object.*;

import java.awt.*;

public class Bobo extends Entity{

    public static final String npcName = "Bobo";

    public Bobo(GamePanel gp) {
        super(gp);

        name = npcName;
        direction = "down";
        speed=1;

        collisionBox = new Rectangle();
        collisionBox.x=8;
        collisionBox.y=16;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;
        collisionBox.width=32;
        collisionBox.height=32;

        getImages();
        setDialogues();
        if(inventory.size()<1)
            setItems();
    }

    @Override
    public void update() {

    }

    public void getImages(){
        upidle = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        up1 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        up2 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        downidle = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        down1 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        down2 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        leftidle = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        left1 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        left2 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        rightidle = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        right1 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
        right2 = setImage("/npc/bobo1",gp.TILE_SIZE,gp.TILE_SIZE);
    }

    public void setDialogues(){
        dialogues[0][0] = "Nasi buldun beni?? Bu degil onemli olan.\nAlış veriş yapmak ister misin sen?";
        dialogues[1][0] = "Yine bekleriz...";
        dialogues[2][0] = "Yetmiyor senin paran :(";
        dialogues[3][0] = "Dolu senin envanterin :(";
        dialogues[4][0] = "Sende kalsin o. Lazim olur :)";
        dialogues[5][0] = "Yeter artik kardes. Benim de var sinirim.";
    }

    public void setItems(){
        inventory.add(new Carrot(gp));
        inventory.add(new Candy(gp));
        inventory.add(new SleepPotion(gp));
        inventory.add(new HealthPotion(gp));
        inventory.add(new Torch(gp));
    }

    @Override
    public void speak() {
        gp.gameState=gp.TRADE_STATE;
        gp.uiHandler.commandNum=0;
        gp.uiHandler.subState=0;
        gp.uiHandler.npcSlotCol=0;
        gp.uiHandler.npcSlotRow=0;
        gp.uiHandler.playerSlotRow=0;
        gp.uiHandler.playerSlotCol=0;
        gp.uiHandler.npc=this;
    }
}
