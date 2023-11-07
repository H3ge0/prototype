package main;

import entity.Entity;
import tile_interactive.InteractiveTile;

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

        //For KnockBack
        String direction = entity.direction;
        if(entity.knockBack){
            direction=entity.knockBackDirection;
        }

        switch (direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.map[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.map[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.map[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.map[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.map[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
        }

    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;

        //For KnockBack
        String direction = entity.direction;
        if(entity.knockBack){
            direction=entity.knockBackDirection;
        }

        for(int i=0;i<gp.obj[gp.currentMap].length;i++){
            if(gp.obj[gp.currentMap][i]!=null){
                //Get entity's collision box co-ords
                entity.collisionBox.x += entity.worldX;
                entity.collisionBox.y += entity.worldY;

                //Get entity's collision box co-ords
                gp.obj[gp.currentMap][i].collisionBox.x += gp.obj[gp.currentMap][i].worldX;
                gp.obj[gp.currentMap][i].collisionBox.y += gp.obj[gp.currentMap][i].worldY;

                switch(direction){
                    case "up" -> entity.collisionBox.y-=entity.speed;
                    case "down" -> entity.collisionBox.y+=entity.speed;
                    case "left" -> entity.collisionBox.x-=entity.speed;
                    case "right" -> entity.collisionBox.x+=entity.speed;
                }

                if(entity.collisionBox.intersects(gp.obj[gp.currentMap][i].collisionBox)) {
                    if(gp.obj[gp.currentMap][i].collisionOn)
                        entity.collision = true;
                    if (player)
                        index = i;
                }

                //Set entity's collision box co-ords to normal
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                //Set entity's collision box co-ords to normal
                gp.obj[gp.currentMap][i].collisionBox.x = gp.obj[gp.currentMap][i].collisionBoxDefaultX;
                gp.obj[gp.currentMap][i].collisionBox.y = gp.obj[gp.currentMap][i].collisionBoxDefaultY;

            }
        }

        return index;
    }

    public int checkEntity(Entity entity,Entity[][] target){
        int index = 999;

        boolean thisCollision = false;

        //For KnockBack
        String direction = entity.direction;
        if(entity.knockBack){
            direction=entity.knockBackDirection;
        }

        for(int i=0;i<target[gp.currentMap].length;i++){
            if(target[gp.currentMap][i]!=null&&target[gp.currentMap][i]!=entity){
                //Get entity's collision box co-ords
                entity.collisionBox.x += entity.worldX;
                entity.collisionBox.y += entity.worldY;

                //Get entity's collision box co-ords
                target[gp.currentMap][i].collisionBox.x += target[gp.currentMap][i].worldX;
                target[gp.currentMap][i].collisionBox.y += target[gp.currentMap][i].worldY;

                switch(direction){
                    case "up" -> entity.collisionBox.y-=entity.speed;
                    case "down" -> entity.collisionBox.y+=entity.speed;
                    case "left" -> entity.collisionBox.x-=entity.speed;
                    case "right" -> entity.collisionBox.x+=entity.speed;
                }

                boolean isInteractiveTile=false;

                for(InteractiveTile iTile:gp.iTiles[gp.currentMap]){
                    if(iTile!=null){
                        if (iTile == target[gp.currentMap][i]) {
                            isInteractiveTile = true;
                            break;
                        }
                    }
                }

                if(entity.collisionBox.intersects(target[gp.currentMap][i].collisionBox)) {
                    if(isInteractiveTile)
                        thisCollision=target[gp.currentMap][i].collisionOn;
                    else
                        thisCollision=true;
                    index = i;
                }

                //Set entity's collision box co-ords to normal
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                //Set entity's collision box co-ords to normal
                target[gp.currentMap][i].collisionBox.x = target[gp.currentMap][i].collisionBoxDefaultX;
                target[gp.currentMap][i].collisionBox.y = target[gp.currentMap][i].collisionBoxDefaultY;



                entity.collision=entity.collision||thisCollision;
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
