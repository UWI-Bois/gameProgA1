import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Environment extends Sprite
{
    private ImageHandler imageHandler;
    private AudioHandler audioHandler;
    public String folderName;
    public int ground;
    public int flyBoundary;

    // image file names
    public String bgImage = "background.png";
    public String gameOver = "gameOver.jpg";
    public String gameStart = "gameStart.png";
    // sound file names
    public String bgm = "bgm.wav";
    public String giorno = "giorno.wav";
    public String jojo = "jojo.wav";

    public Timer timer;
    public static int timerCount = 0;
    public TimerTask timerTask;
    public boolean canDio;
    public boolean canGiorno;
    public int dioTimer = 60; // time to wait before dio moves
    public boolean spawnTimer; // time to wait before dio moves
    public ConcurrentHashMap<Integer, Minion> minions;

    public Environment(GamePanel p){
        super(p);
        name = "environment";
        folderName = name; // accessible through imagehandler class
        width = 1280;
        height = 720;
        ground = 500;
        flyBoundary = 300;
        canDio = false;
        spawnTimer = false;
        timer = new Timer();
        timerTask = new TimerTask(){
        
            @Override
            public void run() {
                // TODO Auto-generated method stub
                timerCount++;
                if(timerCount % 5 != 0) spawnTimer = false;
                // System.out.println("timercount: " + timerCount);
                checkDio();
            }
        };

        minions = new ConcurrentHashMap<Integer, Minion>();
        imageHandler = new ImageHandler(folderName);
        audioHandler = new AudioHandler(folderName);
        initImages();
        initAudio();
    }

    public int getFlyBoundary(){return this.flyBoundary;}

    public void startTimer(){timer.scheduleAtFixedRate(timerTask, 0, 1*1000);}

    public int getTimerCount(){return this.timerCount;}

    public boolean getCanDio(){return this.canDio;}
    public void setCanDio(boolean v){this.canDio = v;}
    public boolean getCanGiorno(){return this.canGiorno;}
    public void setCanGiorno(boolean v){this.canGiorno = v;}

    public void checkDio(){
        if(this.timerCount >= dioTimer){
            canDio = true;
            //System.out.println("action is coming");
        }
    }

    public void spawnMinions(){
        if(!spawnTimer && timerCount % 3 == 0){
            // spawn minions? perhaps some kinda jokey thing?
            int r = getRandomInt(1, 6); // roll d dice
            if(timerCount % 6 == 0){
                if(r == 1 || r == 2){
                    spawn1(); 
                    spawn3(); 
                } 
                else if(r == 3 || r == 4) {
                    spawn2();
                    spawn3(); 
                }
                else if(r == 5 || r == 6) {
                    spawn1();
                    spawn2();
                }
            }
            else {
                if(r == 1 || r == 2) spawn1(); 
                else if(r == 3 || r == 4) spawn2(); 
                else if(r == 5 || r == 6) spawn3();
            }
            spawnTimer = true;
        }
        else return;
    }

    public void spawn1(){ // spawn fastbois hardbois
        Color color = Color.CYAN;
        int max;
        if(timerCount > 30) max = 4;
        else max = 2;

        for (int i = 0; i < max; i++) {
            Minion var = new Minion(panel);
            var.DY = getRandomInt(20, 30);
            var.DX = getRandomInt(20, 30);
            var.health = 1;
            var.damage = 2;
            var.setColor(color);
            minions.put(var.getId(), var);
        }
        minions.toString();
    }
    
    public void spawn2(){ // spawn thiccbois
        Color color = Color.PINK;
        int max;
        if(timerCount > 30) max = 4;
        else max = 2;
        

        for (int i = 0; i < max; i++) {
            Minion var = new Minion(panel);
            var.DY = getRandomInt(6, 10);
            var.DX = getRandomInt(10, 15);
            var.health = 4;
            var.damage = 1;
            var.setColor(color);
            minions.put(var.getId(), var);
        }
        minions.toString();
    }
    
    public void spawn3(){ // spawn bois
        Color color = Color.MAGENTA;
        int max;
        if(timerCount > 30) max = 4;
        else max = 2;

        for (int i = 0; i < max; i++) {
            Minion var = new Minion(panel);
            var.DY = getRandomInt(6, 10);
            var.DX = getRandomInt(10, 15);
            var.health = getRandomInt(2, 6);
            var.damage = getRandomInt(1, 2);
            var.setColor(color);
            minions.put(var.getId(), var);
        }
        minions.toString();
    }
    

    private void initImages(){
        this.imageHandler.loadImage(bgImage);
        this.imageHandler.loadImage(gameOver);
        this.imageHandler.loadImage(gameStart);
        System.out.println("initImages for Environment:" + imageHandler.toString());
    }

    private void initAudio(){
        this.audioHandler.loadClip(bgm);
        this.audioHandler.loadClip(jojo);
        this.audioHandler.loadClip(giorno);
        System.out.println("initAudio for Environment:" + audioHandler.toString());
    }

    public ImageHandler getImageHandler(){return this.imageHandler;}
    public AudioHandler getAudioHandler(){return this.audioHandler;}

    public int getGround(){return this.ground;}
    public void setGround(int y){this.ground = y;}

    public String toString(){
        String s = "Environment:\n"
            + super.toString()
            + "\nGround (y) = " + this.ground
            + "\nname = folderName = " + name
            + "\nimages: " + imageHandler.toString()
            + "\nclips: " + audioHandler.toString();
        return s;
    }

    public void drawMinions(Graphics2D g2){
        Set<Map.Entry<Integer, Minion>> set = minions.entrySet();
		for(Map.Entry<Integer, Minion> m : set)
		{
			m.getValue().draw(g2);
		}
    }

    public void updateMinions() {
        Set<Map.Entry<Integer, Minion>> set = minions.entrySet();
		for(Map.Entry<Integer, Minion> m : set)
		{
            Minion minion = m.getValue();
            if (minion.isVisible() || !minion.isDead) {
                minion.move();
            } 
            else if(minion.isDead) {
                minions.remove(minion.getId());
            }
            else {
                minions.remove(minion.getId());
            }
        }
    }
    
    public void clearMinions() {
        Set<Map.Entry<Integer, Minion>> set = minions.entrySet();
		for(Map.Entry<Integer, Minion> m : set)
		{
            Minion minion = m.getValue();
            minions.remove(minion.getId());
        }
    }

    public ConcurrentHashMap<Integer, Minion> getMinions(){return this.minions;}

    // main for testing

    // public static void main(String[] args) {
    //     GamePanel p = new GamePanel();
    //     Environment e = new Environment(p);
    //     System.out.println(e.toString());
    //     int n;
    //     //e.audioHandler.playClip("bgm.wav");
    //     int max = 10;
    //     int min = 1;
    //     for (int i = 0; i < 200; i++) {
    //         n = getRandomInt(min, max);
    //         if(n == min || n == max) System.out.println("n: " + n);
    //         // System.out.println(e.getTimerCount());
            
    //     }
    //     System.out.println("end e test");
    // }
}