package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentHandler {

    GamePanel gp;
    Lighting lighting;

    public EnvironmentHandler(GamePanel gp){
        this.gp = gp;
        //setUp();
    }

    public void setUp(){
        lighting = new Lighting(gp);
    }

    public void update(){
        lighting.update();
    }

    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }

}
