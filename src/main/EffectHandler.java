package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EffectHandler {

    GamePanel gp;

    BufferedImage weak1,weak2,weak3,weak4,weak5,weak6;

    public EffectHandler(GamePanel gp){
        this.gp = gp;

        getWeakEffectImages();
    }

    public void getWeakEffectImages(){
        weak1 = setImage("/effects/weak/1",gp.tileSize,gp.tileSize);
        weak2 = setImage("/effects/weak/2",gp.tileSize,gp.tileSize);
        weak3 = setImage("/effects/weak/3",gp.tileSize,gp.tileSize);
        weak4 = setImage("/effects/weak/4",gp.tileSize,gp.tileSize);
        weak5 = setImage("/effects/weak/5",gp.tileSize,gp.tileSize);
        weak6 = setImage("/effects/weak/6",gp.tileSize,gp.tileSize);
    }

    public void drawEffect(Graphics2D g2, Entity entity){

        int screenX = entity.worldX - gp.player.worldX + gp.player.screenX;
        int screenY = entity.worldY - gp.player.worldY + gp.player.screenY;

        if(entity.weak){
            if(Math.abs(gp.player.worldX-entity.worldX)<gp.screenWidth/2+gp.tileSize && Math.abs(gp.player.worldY-entity.worldY)<gp.screenHeight/2+gp.tileSize){

                BufferedImage image = weak1;

                if(entity.weakCounter<=20){
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
