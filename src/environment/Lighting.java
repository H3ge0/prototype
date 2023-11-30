package environment;

import main.GamePanel;
import tile.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayNumber=1;
    public int dayCounter=0;
    public float filterAlpha = 0f;

    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp){
        this.gp=gp;
        setLightSource();
    }

    public void setLightSource(){
        darknessFilter=new BufferedImage(gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        int circleRadius;

        if(gp.player.currentLightSource!=null){
            circleRadius=gp.player.currentLightSource.lightRadius;
        }else {
            circleRadius=200;
        }

        int centerX = gp.player.screenX+gp.TILE_SIZE /2;
        int centerY = gp.player.screenY+gp.TILE_SIZE /2;

        Color[] color = new Color[12];
        float[] fraction = new float[12];

        color[0] = new Color(0,0,0.1f,0.1f);
        color[1] = new Color(0,0,0.1f,0.42f);
        color[2] = new Color(0,0,0.1f,0.52f);
        color[3] = new Color(0,0,0.1f,0.61f);
        color[4] = new Color(0,0,0.1f,0.69f);
        color[5] = new Color(0,0,0.1f,0.76f);
        color[6] = new Color(0,0,0.1f,0.82f);
        color[7] = new Color(0,0,0.1f,0.87f);
        color[8] = new Color(0,0,0.1f,0.91f);
        color[9] = new Color(0,0,0.1f,0.94f);
        color[10] = new Color(0,0,0.1f,0.96f);
        color[11] = new Color(0,0,0.1f,0.98f);

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,(float)circleRadius/2,fraction,color);

        g2.setPaint(gPaint);

        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        g2.dispose();
    }

    public void update(){
        if(gp.player.lightUpdated){
            setLightSource();
            gp.player.lightUpdated=false;
        }

        switch(dayState){
            case day -> {
                dayCounter++;
                if(dayCounter>1800){
                    dayState = dusk;
                    dayCounter=0;
                }
            }
            case dusk -> {
                filterAlpha+=0.001f;
                if(filterAlpha>1f){
                    filterAlpha=1f;
                    dayState=night;
                }
            }
            case night -> {
                dayCounter++;
                if(dayCounter>1800){
                    dayState = dawn;
                    dayCounter=0;
                }
            }
            case dawn -> {
                filterAlpha-=0.0008f;
                if(filterAlpha<0f){
                    filterAlpha=0f;
                    dayState=day;
                    dayNumber++;
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        if(gp.currentArea == Map.WORLD1){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }

        if(gp.currentArea ==Map.WORLD1 || gp.currentArea ==Map.DUNGEON) {
            g2.drawImage(darknessFilter,0,0,null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setColor(Color.white);
        g2.setFont(gp.uiHandler.fixedsys.deriveFont(50f));
        g2.drawString(dayNumber+". gün",750,540);
    }

    public void reset(){
        dayNumber=1;
        dayCounter=0;
        filterAlpha = 0f;
        dayState = day;
    }

}
