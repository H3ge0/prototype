package main;

import entity.Entity;

public class CollisionHandler {

    GamePanel gp;

    public CollisionHandler(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.map[entityRightCol][entityTopRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.map[entityRightCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.map[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.map[entityRightCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
        }

    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i]!=null){
                //Get entity's collision box co-ords
                entity.collisionBox.x += entity.worldX;
                entity.collisionBox.y += entity.worldY;

                //Get entity's collision box co-ords
                gp.obj[i].collisionBox.x += gp.obj[i].worldX;
                gp.obj[i].collisionBox.y += gp.obj[i].worldY;

                switch(entity.direction){
                    case "up" -> entity.collisionBox.y-=entity.speed;
                    case "down" -> entity.collisionBox.y+=entity.speed;
                    case "left" -> entity.collisionBox.x-=entity.speed;
                    case "right" -> entity.collisionBox.x+=entity.speed;
                }

                if(entity.collisionBox.intersects(gp.obj[i].collisionBox)) {
                    if(gp.obj[i].collisionOn)
                        entity.collision = true;
                    if (player)
                        index = i;
                }

                //Set entity's collision box co-ords to normal
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                //Set entity's collision box co-ords to normal
                gp.obj[i].collisionBox.x = gp.obj[i].collisionBoxDefaultX;
                gp.obj[i].collisionBox.y = gp.obj[i].collisionBoxDefaultY;

            }
        }

        return index;
    }

    public int checkEntity(Entity entity,Entity[] target){
        int index = 999;

        for(int i=0;i<target.length;i++){
            if(target[i]!=null&&target[i]!=entity){
                //Get entity's collision box co-ords
                entity.collisionBox.x += entity.worldX;
                entity.collisionBox.y += entity.worldY;

                //Get entity's collision box co-ords
                target[i].collisionBox.x += target[i].worldX;
                target[i].collisionBox.y += target[i].worldY;

                switch(entity.direction){
                    case "up" -> entity.collisionBox.y-=entity.speed;
                    case "down" -> entity.collisionBox.y+=entity.speed;
                    case "left" -> entity.collisionBox.x-=entity.speed;
                    case "right" -> entity.collisionBox.x+=entity.speed;
                }

                if(entity.collisionBox.intersects(target[i].collisionBox)) {
                    entity.collision = true;
                    index = i;
                }

                //Set entity's collision box co-ords to normal
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                //Set entity's collision box co-ords to normal
                target[i].collisionBox.x = target[i].collisionBoxDefaultX;
                target[i].collisionBox.y = target[i].collisionBoxDefaultY;

            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity){
        boolean contactPlayer=false;

        //Get entity's collision box co-ords
        entity.collisionBox.x += entity.worldX;
        entity.collisionBox.y += entity.worldY;

        //Get player's collision box co-ords
        gp.player.collisionBox.x += gp.player.worldX;
        gp.player.collisionBox.y += gp.player.worldY;

        switch(entity.direction){
            case "up" -> entity.collisionBox.y-=entity.speed;
            case "down" -> entity.collisionBox.y+=entity.speed;
            case "left" -> entity.collisionBox.x-=entity.speed;
            case "right" -> entity.collisionBox.x+=entity.speed;
        }

        if(entity.collisionBox.intersects(gp.player.collisionBox)){
            entity.collision = true;
            contactPlayer=true;
        }

        //Set entity's collision box co-ords to normal
        entity.collisionBox.x = entity.collisionBoxDefaultX;
        entity.collisionBox.y = entity.collisionBoxDefaultY;

        //Set entity's collision box co-ords to normal
        gp.player.collisionBox.x = gp.player.collisionBoxDefaultX;
        gp.player.collisionBox.y = gp.player.collisionBoxDefaultY;

        return contactPlayer;
    }

}
