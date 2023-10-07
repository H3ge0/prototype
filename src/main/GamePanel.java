package main;

import entity.Player;
import object.Object;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int originalTileSize = 17; //17 daha guzel duruyo
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

    KeyHandler keyH = new KeyHandler();
    SoundHandler music = new SoundHandler();
    SoundHandler soundEffect = new SoundHandler();
    public UIHandler uiHandler = new UIHandler(this);
    ObjectHandler objectHandler = new ObjectHandler(this);
    public CollisionHandler collisionH = new CollisionHandler(this);
    TileManager tileManager = new TileManager(this);
    public Player player = new Player(this, keyH);
    public Object[] obj = new Object[20];
    Thread gameThread;

    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        objectHandler.setObjects();
        playMusic(0);
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = ((Graphics2D) g);

        //Tile
        tileManager.draw(g2);

        //Object
        for (Object object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }

        //Player
        player.draw(g2);

        //UI
        uiHandler.draw(g2);

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
