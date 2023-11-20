package entity;

import main.GamePanel;
import object.IronDoor;
import tile_interactive.DungeonPlate;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BigRock extends Entity{

    public static final String npcName = "Big Rock";
    Random random;

    public BigRock(GamePanel gp) {
        super(gp);
        random = new Random();

        name = npcName;
        direction = "down";
        speed=4;

        collisionBox = new Rectangle();
        collisionBox.x=2;
        collisionBox.y=2;
        collisionBoxDefaultX=collisionBox.x;
        collisionBoxDefaultY=collisionBox.y;
        collisionBox.width=44;
        collisionBox.height=44;

        dialogueSet=-1;

        getImages();
        setDialogues();
        actionLockCounter = random.nextInt(1,120);
    }

    public void getImages(){
        down1 = setImage("/npc/big_rock",gp.tileSize,gp.tileSize);
    }

    public void setDialogues(){
        dialogues[0][0] = "Galiba obez bir taş.";
    }

    @Override
    public BufferedImage setDrawImage() {
        return down1;
    }

    @Override
    public void update() {

    }

    public void speak(){
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if(dialogues[dialogueSet][0]==null){
            dialogueSet--;
        }
    }

    @Override
    public void move(String direction) {
        this.direction=direction;

        checkCollision();

        if(!collision){
            switch(direction){
                case "up" -> worldY-=speed;
                case "down" -> worldY+=speed;
                case "left" -> worldX-=speed;
                case "right" -> worldX+=speed;
            }
        }

        detectPlate();
    }

    public void detectPlate(){
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        for(InteractiveTile iTile:gp.iTiles[gp.currentMap]){
            if(iTile!=null && iTile.name.equals(DungeonPlate.itName)){
                plateList.add(iTile);
            }
        }
        for(Entity rock:gp.npcs[gp.currentMap]){
            if(rock!=null && rock.name.equals(BigRock.npcName)){
                rockList.add(rock);
            }
        }

        int count = 0;

        for(InteractiveTile iTile:plateList){
            int xDistance = getXDistance(iTile);
            int yDistance = getYDistance(iTile);
            int distance = Math.max(xDistance,yDistance);

            if(distance<8){
                if(linkedEntity==null){
                    linkedEntity = iTile;
                    gp.playSoundEffect(27);
                }
            }else{
                if(linkedEntity==iTile){
                    linkedEntity=null;
                }
            }
        }

        for(Entity rock:rockList){
            if(rock.linkedEntity!=null){
                count++;
            }
        }

        if(count==rockList.size()){
            for(int i=0; i<gp.obj[gp.currentMap].length; i++){
                if(gp.obj[gp.currentMap][i]!=null && gp.obj[gp.currentMap][i].name.equals(IronDoor.objName)){
                    gp.obj[gp.currentMap][i]=null;
                    gp.playSoundEffect(27);
                }
            }
        }
    }
}
