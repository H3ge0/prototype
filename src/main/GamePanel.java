package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize*scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol*tileSize; // 768
    public final int screenHeight = maxScreenRow*tileSize; // 576

    //World Settings

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 60;
    int displayFPS = FPS;

    SoundHandler music = new SoundHandler();
    SoundHandler soundEffect = new SoundHandler();
    public KeyHandler keyH = new KeyHandler(this);
    public UIHandler uiH = new UIHandler(this);
    ObjectHandler objectH = new ObjectHandler(this);
    public EventHandler eventH = new EventHandler(this);
    public CollisionHandler collisionH = new CollisionHandler(this);
    TileManager tileManager = new TileManager(this);
    public Player player = new Player(this, keyH);
    public Entity[] npcs = new Entity[10];
    public Entity[] obj = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();
    Thread gameThread;

    //Gamestate
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        objectH.setObjects();
        objectH.setNPCs();
        gameState=titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Game Loop
    @Override
    public void run() {

        double drawInterval = (double)1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread!=null){

            currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/drawInterval;
            timer += currentTime-lastTime;
            lastTime = currentTime;

            if(delta>=1){
                update();
                repaint();
                drawCount++;
                delta--;
            }

            if(timer>=1000000000){
                displayFPS = drawCount;
                drawCount=0;
                timer=0;
            }

        }
    }

    public void update(){
        if (gameState==playState){
            //Player
            player.update();
            //Npcs
            for(Entity entity:npcs){
                if(entity!=null)
                    entity.update();
            }
        } else if(gameState==pauseState){
            
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = ((Graphics2D) g);

        //Debug
        long drawStart = 0;
        if(keyH.debugMode){
            drawStart = System.nanoTime();
        }

        //Title Screen
        if(gameState==titleState){
            uiH.draw(g2);
        }
        //Other Stuff
        else {
            //Tile
            tileManager.draw(g2);

            //Add all entities to the list
            entityList.add(player);
            for(Entity e:npcs){
                if(e!=null){
                    entityList.add(e);
                }
            }
            for(Entity e:obj){
                if(e!=null){
                    entityList.add(e);
                }
            }

            //Sort entities by height
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            //Draw entities
            for(Entity entity:entityList){
                entity.draw(g2);
            }

            //Clean entityList
            entityList.clear();
            /*for(int i=0;i<entityList.size();i++){
                entityList.remove(i);
            }*/

            //UI
            uiH.draw(g2);
        }

        //Debug
        if(keyH.debugMode){
            long drawEnd = System.nanoTime();
            long passed = drawEnd-drawStart;
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20f));
            g2.drawString("Draw Time:"+passed,10,400);
            System.out.println("Draw Time:"+passed);
        }

        g2.dispose();
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
