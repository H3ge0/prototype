package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;
    public int soundEffect = 0;

    public Projectile(GamePanel gp) {
        super(gp);
        name="";
    }

    public void setProjectile(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX=worldX;
        this.worldY=worldY;
        this.direction=direction;
        this.alive=alive;
        this.user=user;
        this.hp=this.maxHp;
    }

    @Override
    public void update(){
        if(user==gp.player){
            int monsterIndex = gp.collisionHandler.checkEntity(this,gp.monsters);
            if(monsterIndex!=999){
                gp.player.attackMonster(monsterIndex,this,attack*gp.player.level,knockBackPower);
                generateParticle(user.currentProjectile,gp.monsters[gp.currentMapNum][monsterIndex]);
                alive=false;
            }
        } else{
            boolean contactPlayer = gp.collisionHandler.checkPlayer(this);
            if(contactPlayer){
                attackPlayer(attack);
                generateParticle(user.currentProjectile,user.currentProjectile);
                alive=false;
            }
        }

        switch (direction){
            case "up" -> worldY-=speed;
            case "down" -> worldY+=speed;
            case "left" -> worldX-=speed;
            case "right" -> worldX+=speed;
        }

        hp--;
        if(hp<=0){
            alive=false;
        }

        increaseSpriteCounter();
    }

    public boolean hasEnergy(Entity user){
        return false;
    }

    public void subtractEnergy(Entity user){}

}
