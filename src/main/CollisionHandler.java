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

        int entityLeftCol = entityLeftWorldX/gp.TILE_SIZE;
        int entityRightCol = entityRightWorldX/gp.TILE_SIZE;
        int entityTopRow = entityTopWorldY/gp.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY/gp.TILE_SIZE;

        int tileNum1, tileNum2;

        //For KnockBack
        String direction = entity.direction;
        if(entity.knockBack){
            direction=entity.knockBackDirection;
        }

        switch (direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileManager.maps[gp.currentMapNum].mapData[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.maps[gp.currentMapNum].mapData[entityRightCol][entityTopRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileManager.maps[gp.currentMapNum].mapData[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.maps[gp.currentMapNum].mapData[entityRightCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileManager.maps[gp.currentMapNum].mapData[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.maps[gp.currentMapNum].mapData[entityLeftCol][entityBottomRow];
                if (gp.tileManager.tiles[tileNum1].collision || gp.tileManager.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileManager.maps[gp.currentMapNum].mapData[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.maps[gp.currentMapNum].mapData[entityRightCol][entityBottomRow];
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

        for(int i = 0; i<gp.objects[gp.currentMapNum].length; i++){
            if(gp.objects[gp.currentMapNum][i]!=null){
                //Get entity's collision box co-ords
                entity.collisionBox.x += entity.worldX;
                entity.collisionBox.y += entity.worldY;

                //Get entity's collision box co-ords
                gp.objects[gp.currentMapNum][i].collisionBox.x += gp.objects[gp.currentMapNum][i].worldX;
                gp.objects[gp.currentMapNum][i].collisionBox.y += gp.objects[gp.currentMapNum][i].worldY;

                switch(direction){
                    case "up" -> entity.collisionBox.y-=entity.speed;
                    case "down" -> entity.collisionBox.y+=entity.speed;
                    case "left" -> entity.collisionBox.x-=entity.speed;
                    case "right" -> entity.collisionBox.x+=entity.speed;
                }

                if(entity.collisionBox.intersects(gp.objects[gp.currentMapNum][i].collisionBox)) {
                    if(gp.objects[gp.currentMapNum][i].collisionOn)
                        entity.collision = true;
                    if (player)
                        index = i;
                }

                //Set entity's collision box co-ords to normal
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                //Set entity's collision box co-ords to normal
                gp.objects[gp.currentMapNum][i].collisionBox.x = gp.objects[gp.currentMapNum][i].collisionBoxDefaultX;
                gp.objects[gp.currentMapNum][i].collisionBox.y = gp.objects[gp.currentMapNum][i].collisionBoxDefaultY;

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

        for(int i = 0; i<target[gp.currentMapNum].length; i++){
            if(target[gp.currentMapNum][i]!=null&&target[gp.currentMapNum][i]!=entity){
                //Get entity's collision box co-ords
                entity.collisionBox.x += entity.worldX;
                entity.collisionBox.y += entity.worldY;

                //Get entity's collision box co-ords
                target[gp.currentMapNum][i].collisionBox.x += target[gp.currentMapNum][i].worldX;
                target[gp.currentMapNum][i].collisionBox.y += target[gp.currentMapNum][i].worldY;

                switch(direction){
                    case "up" -> entity.collisionBox.y-=entity.speed;
                    case "down" -> entity.collisionBox.y+=entity.speed;
                    case "left" -> entity.collisionBox.x-=entity.speed;
                    case "right" -> entity.collisionBox.x+=entity.speed;
                }

                boolean isInteractiveTile=false;

                for(InteractiveTile iTile:gp.interactiveTiles[gp.currentMapNum]){
                    if(iTile!=null){
                        if (iTile == target[gp.currentMapNum][i]) {
                            isInteractiveTile = true;
                            break;
                        }
                    }
                }

                if(entity.collisionBox.intersects(target[gp.currentMapNum][i].collisionBox)) {
                    if(isInteractiveTile)
                        thisCollision=target[gp.currentMapNum][i].collisionOn;
                    else
                        thisCollision=true;
                    index = i;
                }

                //Set entity's collision box co-ords to normal
                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                //Set entity's collision box co-ords to normal
                target[gp.currentMapNum][i].collisionBox.x = target[gp.currentMapNum][i].collisionBoxDefaultX;
                target[gp.currentMapNum][i].collisionBox.y = target[gp.currentMapNum][i].collisionBoxDefaultY;

                if(entity.name!=null && target[gp.currentMapNum][i].name!=null){
                    if(entity.name.equals("Ipog") && (target[gp.currentMapNum][i].name.equals("Part1") || target[gp.currentMapNum][i].name.equals("Part2")))
                        thisCollision=false;
                }


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
