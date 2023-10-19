package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

    public boolean canChange =false;

    public InteractiveTile(GamePanel gp,int col,int row) {
        super(gp);
        collisionOn=true;
    }

    public boolean isCorrectItem(Entity entity){
        return false;
    }

    @Override
    public void update() {

    }

    public void attack(int i){}
    public void generateITileParticle(int i){}
}
