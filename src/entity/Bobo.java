package entity;

import main.GamePanel;
import object.Candy;
import object.Carrot;
import object.FireballPurple;
import object.Torch;

import java.awt.*;

public class Bobo extends Entity{

    public Bobo(GamePanel gp) {
        super(gp);

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
        setItems();
    }

    @Override
    public void update() {

    }

    public void getImages(){
        upidle = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        up1 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        up2 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        downidle = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        down1 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        down2 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        leftidle = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        left1 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        left2 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        rightidle = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        right1 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
        right2 = setImage("/npc/bobo1",gp.tileSize,gp.tileSize);
    }

    public void setDialogues(){
        dialogues[0] = "Nasi buldun beni?? Bu degil onemli olan.\nAlış veriş yapmak ister misin sen?";
    }

    public void setItems(){
        inventory.add(new Carrot(gp));
        inventory.add(new Candy(gp));
        inventory.add(new FireballPurple(gp));
        inventory.add(new Torch(gp));
    }

    @Override
    public void speak() {
        super.speak();
        gp.gameState=gp.tradeState;
        gp.uiH.commandNum=0;
        gp.uiH.subState=0;
        gp.uiH.npc=this;
    }
}
