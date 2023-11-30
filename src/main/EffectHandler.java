package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class EffectHandler {

    GamePanel gp;

    BufferedImage weak1,weak2,weak3,weak4,weak5,weak6;
    BufferedImage  resistant1,resistant2,resistant3,resistant4,resistant5,resistant6;

    Random random;

    public EffectHandler(GamePanel gp){
        this.gp = gp;

        random = new Random();

        getWeakEffectImages();
    }

    public void getWeakEffectImages(){
        weak1 = setImage("/effects/weak/1",gp.TILE_SIZE,gp.TILE_SIZE);
        weak2 = setImage("/effects/weak/2",gp.TILE_SIZE,gp.TILE_SIZE);
        weak3 = setImage("/effects/weak/3",gp.TILE_SIZE,gp.TILE_SIZE);
        weak4 = setImage("/effects/weak/4",gp.TILE_SIZE,gp.TILE_SIZE);
        weak5 = setImage("/effects/weak/5",gp.TILE_SIZE,gp.TILE_SIZE);
        weak6 = setImage("/effects/weak/6",gp.TILE_SIZE,gp.TILE_SIZE);
        resistant1 = setImage("/effects/resistant/1",gp.TILE_SIZE *4,gp.TILE_SIZE *4);
        resistant2 = setImage("/effects/resistant/2",gp.TILE_SIZE *4,gp.TILE_SIZE *4);
        resistant3 = setImage("/effects/resistant/3",gp.TILE_SIZE *4,gp.TILE_SIZE *4);
        resistant4 = setImage("/effects/resistant/4",gp.TILE_SIZE *4,gp.TILE_SIZE *4);
        resistant5 = setImage("/effects/resistant/5",gp.TILE_SIZE *4,gp.TILE_SIZE *4);
        resistant6 = setImage("/effects/resistant/6",gp.TILE_SIZE *4,gp.TILE_SIZE *4);
    }

    public void drawEffect(Graphics2D g2, Entity entity){

        int screenX = entity.worldX - gp.player.worldX + gp.player.screenX;
        int screenY = entity.worldY - gp.player.worldY + gp.player.screenY;

        if(entity.weak){
            if(Math.abs(gp.player.worldX-entity.worldX)<gp.SCREEN_WIDTH /2+gp.TILE_SIZE && Math.abs(gp.player.worldY-entity.worldY)<gp.SCREEN_HEIGHT /2+gp.TILE_SIZE){

                BufferedImage image = weak1;

                if(entity.weakCounter>10 && entity.weakCounter<=20){
                    image=weak2;
                }else if(entity.weakCounter<=30){
                    image=weak3;
                }else if(entity.weakCounter<=40){
                    image=weak4;
                }else if(entity.weakCounter<=50){
                    image=weak5;
                }else if(entity.weakCounter<=60){
                    image=weak6;
                }

                g2.drawImage(image,screenX,screenY-20,null);
            }
        }

        if(entity.resistant){
            entity.resistantCounter++;
            if(Math.abs(gp.player.worldX-entity.worldX)<gp.SCREEN_WIDTH /2+gp.TILE_SIZE *4 && Math.abs(gp.player.worldY-entity.worldY)<gp.SCREEN_HEIGHT /2+gp.TILE_SIZE *4){

                BufferedImage image;

                if(entity.resistantCounter<=10){
                    image=resistant1;
                }else if(entity.resistantCounter<=20){
                    image=resistant2;
                }else if(entity.resistantCounter<=30){
                    image=resistant3;
                }else if(entity.resistantCounter<=40){
                    image=resistant4;
                }else if(entity.resistantCounter<=50){
                    image=resistant5;
                }else if(entity.resistantCounter<=60){
                    image=resistant6;
                }else if(entity.resistantCounter<=70){
                    image=resistant5;
                }else if(entity.resistantCounter<=80){
                    image=resistant4;
                }else if(entity.resistantCounter<=90){
                    image=resistant3;
                }else if(entity.resistantCounter<=100){
                    image=resistant2;
                }else if(entity.resistantCounter<=110){
                    image=resistant1;
                }else{
                    image=resistant1;
                    entity.resistantCounter=0;
                }

                setG2Alpha(g2,0.3f);

                if(!entity.sleep)
                    g2.drawImage(image,screenX+(entity.down1.getHeight()/2)-image.getHeight()/2,screenY+(entity.down1.getHeight()/2)-image.getHeight()/2,null);

                setG2Alpha(g2,1f);
            }
        }
    }

    public static void setG2Alpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public BufferedImage setImage(String path,int width,int height){
        UtilityTool utility = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path+".png")));
            image = utility.scaleImage(image,width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

}
