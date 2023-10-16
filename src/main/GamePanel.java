package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize*scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol*tileSize; // 960
    public final int screenHeight = maxScreenRow*tileSize; // 576

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

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
    public Entity[] obj = new Entity[20];
    public Entity[] monsters = new Entity[20];
    public InteractiveTile[] iTiles = new InteractiveTile[50];
    public ArrayList<Entity> projectiles = new ArrayList<>();
    public ArrayList<Entity> particles = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    Thread gameThread;

    //Gamestate
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int charInfoState = 4;
    public final int settingsState = 5;

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
        objectH.setMonsters();
        objectH.setInteractiveTiles();
        gameState=titleState;

        tempScreen = new BufferedImage(screenWidth,screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        //setFullScreen();
    }

    public void setFullScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screenWidth2 = (int) width;
        screenHeight2 = (int) height;
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
                drawToTempScreen();
                drawToJPanel();
                delta--;
                drawCount++;
            }

            if(timer>=1000000000){
                displayFPS = drawCount;
                drawCount=0;
                timer=0;
            }

        }
    }

    public void update(){
        if (player.hp<=0){
            System.exit(0);
        }
        if (gameState==playState){
            //Player
            player.update();
            //Npcs
            for(Entity entity:npcs){
                if(entity!=null)
                    entity.update();
            }
            //Monsters
            for(int i=0;i<monsters.length;i++){
                if(monsters[i]!=null){
                    if(monsters[i].alive && !monsters[i].dying)
                        monsters[i].update();
                    if(!monsters[i].alive){
                        monsters[i].checkDrop();
                        monsters[i]=null;
                    }
                }
            }
            //Projectiles
            for(int i=0;i<projectiles.size();i++){
                if(projectiles.get(i)!=null){
                    if(projectiles.get(i).alive)
                        projectiles.get(i).update();
                    if(!projectiles.get(i).alive)
                        projectiles.remove(i);
                }
            }
            //Projectiles
            for(int i=0;i<particles.size();i++){
                if(particles.get(i)!=null){
                    if(particles.get(i).alive)
                        particles.get(i).update();
                    if(!particles.get(i).alive)
                        particles.remove(i);
                }
            }
            //InteractiveTiles
            for(InteractiveTile iTile:iTiles){
                if(iTile!=null)
                    iTile.update();
            }
        } else if(gameState==pauseState){
            //Nothing
        }
    }

    public void drawToTempScreen(){

        g2.setColor(Color.black);
        g2.fillRect(0,0,screenWidth,screenHeight);

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
            //Tiles
            tileManager.draw(g2);

            //InteractiveTiles
            for(InteractiveTile iTile:iTiles){
                if(iTile!=null)
                    iTile.draw(g2);
            }

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
            for(Entity e:monsters){
                if(e!=null){
                    entityList.add(e);
                }
            }
            for(Entity e:projectiles){
                if(e!=null){
                    entityList.add(e);
                }
            }
            for(Entity e:particles){
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

            //UI
            uiH.draw(g2);
        }

        //Debug
        if(keyH.debugMode){
            long drawEnd = System.nanoTime();
            long passed = drawEnd-drawStart;
            g2.setColor(Color.white);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.drawString("Draw Time:"+passed,10,400);
            g2.drawString("Invincible Counter:"+player.invincibleCounter,10,450);
        }
    }

    public void drawToJPanel(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
        g.dispose();
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
