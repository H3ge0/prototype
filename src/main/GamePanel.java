package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentHandler;
import tile.Map;
import tile.MiniMap;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    //Screen Settings
    final int ORIGINAL_TILE_SIZE = 16;
    public final int SCALE = 3;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public final int MAX_SCREEN_COL = 20;
    public final int MAX_SCREEN_ROW = 12;
    public final int SCREEN_WIDTH = MAX_SCREEN_COL * TILE_SIZE; // 960
    public final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE; // 576

    //World Settings
    public final int MAP_AMOUNT = 4;
    public int currentMapNum = 0;
    public int currentArea = 0;

    //Full Screen
    int screenWidthForFullScreen = SCREEN_WIDTH;
    int screenHeightForFullScreen = SCREEN_HEIGHT;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //FPS
    int FPS = 60;
    int displayFPS = FPS;

    //Handlers
    SoundHandler music = new SoundHandler();
    SoundHandler soundEffect = new SoundHandler();
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public UIHandler uiHandler = new UIHandler(this);
    public ObjectHandler objectHandler = new ObjectHandler(this);
    public EventHandler eventHandler = new EventHandler(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public EnvironmentHandler environmentHandler = new EnvironmentHandler(this);
    public EffectHandler effectHandler = new EffectHandler(this);
    CutsceneHandler cutsceneHandler = new CutsceneHandler(this);
    public EntityGenerator entityGenerator = new EntityGenerator(this);
    public PathFinder pathFinder = new PathFinder(this);
    Config config = new Config(this);
    SaveLoad saveLoad = new SaveLoad(this);
    MiniMap miniMap = new MiniMap(this);

    //Entities
    public Player player = new Player(this, keyHandler);
    public Entity[][] npcs = new Entity[MAP_AMOUNT][10];
    public Entity[][] objects = new Entity[MAP_AMOUNT][20];
    public Entity[][] monsters = new Entity[MAP_AMOUNT][20];
    public InteractiveTile[][] interactiveTiles = new InteractiveTile[MAP_AMOUNT][50];
    public Entity[][] projectiles = new Entity[MAP_AMOUNT][20];
    public ArrayList<Entity> particles = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //Thread
    Thread gameThread;

    //GameState
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOGUE_STATE = 3;
    public final int CHAR_INFO_STATE = 4;
    public final int SETTINGS_STATE = 5;
    public final int DEAD_STATE = 6;
    public final int TRANSITION_STATE = 7;
    public final int TRADE_STATE = 8;
    public final int SLEEP_STATE = 9;
    public final int CUTSCENE_STATE = 10;
    public boolean bossBattleOn = false;

    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame(){
        objectHandler.setEverything();
        environmentHandler.setUp();

        gameState=TITLE_STATE;

        tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn)
            setFullScreen();
    }

    public void resetGame(boolean restart) {
        player.coin /= 2;
        player.exp = player.nextLevelExp/2;
        objectHandler.removeTempEntities();
        bossBattleOn=false;
        player.setDefaultPosition();
        player.restoreStatus();
        player.resetCounters();
        objectHandler.setAllNPCs();
        objectHandler.setAllMonsters();

        if (restart) {
            player.setDefaultValues();
            objectHandler.setAllObjects();
            objectHandler.setAllInteractiveTiles();
            environmentHandler.lighting.reset();
        }
    }

    public void setFullScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screenWidthForFullScreen = (int) width;
        screenHeightForFullScreen = (int) height;
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
        if (gameState==PLAY_STATE){
            //Player
            player.update();
            //Npcs
            for(Entity entity:npcs[currentMapNum]){
                if(entity!=null)
                    entity.update();
            }
            //Monsters
            for(int i = 0; i<monsters[currentMapNum].length; i++){
                if(monsters[currentMapNum][i]!=null){
                    if(monsters[currentMapNum][i].alive && !monsters[currentMapNum][i].dying)
                        monsters[currentMapNum][i].update();
                    if(!monsters[currentMapNum][i].alive){
                        monsters[currentMapNum][i].checkDrop();
                        monsters[currentMapNum][i]=null;
                    }
                }
            }
            //Projectiles
            for(int i = 0; i<projectiles[currentMapNum].length; i++){
                if(projectiles[currentMapNum][i]!=null){
                    if(projectiles[currentMapNum][i].alive)
                        projectiles[currentMapNum][i].update();
                    if(!projectiles[currentMapNum][i].alive)
                        projectiles[currentMapNum][i]=null;
                }
            }
            //Particles
            for(int i=0;i<particles.size();i++){
                if(particles.get(i)!=null){
                    if(particles.get(i).alive)
                        particles.get(i).update();
                    if(!particles.get(i).alive) {
                        particles.remove(i);
                        i--;
                    }
                }
            }
            //InteractiveTiles
            for(InteractiveTile iTile: interactiveTiles[currentMapNum]){
                if(iTile!=null)
                    iTile.update();
            }
            //Environment
            environmentHandler.update();
        }
    }

    public void drawToTempScreen(){

        g2.setColor(Color.black);
        g2.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Debug
        long drawStart = 0;
        if(keyHandler.debugMode){
            drawStart = System.nanoTime();
        }

        //Title Screen
        if(gameState == TITLE_STATE){
            uiHandler.draw(g2);
        }
        //Other Stuff
        else {
            //Tiles
            tileManager.draw(g2);

            //InteractiveTiles
            for(InteractiveTile iTile: interactiveTiles[currentMapNum]){
                if(iTile!=null)
                    iTile.draw(g2);
            }

            //Add all entities to the list
            entityList.add(player);
            for(Entity e:npcs[currentMapNum]){
                if(e!=null){
                    entityList.add(e);
                }
            }
            for(Entity e: objects[currentMapNum]){
                if(e!=null){
                    entityList.add(e);
                }
            }
            for(Entity e:monsters[currentMapNum]){
                if(e!=null){
                    entityList.add(e);
                }
            }
            for(Entity e:projectiles[currentMapNum]){
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

            //Clear entityList
            entityList.clear();

            //Environment
            environmentHandler.draw(g2);

            //Map
            miniMap.drawMiniMap(g2);

            //Cutscene
            cutsceneHandler.draw(g2);

            //UI
            uiHandler.draw(g2);
        }

        //Debug
        if(keyHandler.debugMode){
            long drawEnd = System.nanoTime();
            long passed = drawEnd-drawStart;
            g2.setColor(Color.white);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.drawString("Draw Time:"+passed,10,350);
            g2.drawString("Col:"+(player.worldX+24)/48,10,400);
            g2.drawString("Row:"+(player.worldY+24)/48,10,450);
        }
    }

    public void drawToJPanel(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0, screenWidthForFullScreen, screenHeightForFullScreen,null);
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

    public void changeArea(boolean isTheSameArea){
        if(!isTheSameArea){
            stopMusic();

            if(currentArea==Map.WORLD1)
                playMusic(0);
            if(currentArea==Map.BOBO_HOUSE)
                playMusic(23);
            if(currentArea==Map.DUNGEON){
                playMusic(24);
                objectHandler.setNPCs(2);
            }
        }

        objectHandler.setMonsters(currentMapNum);
    }
}
