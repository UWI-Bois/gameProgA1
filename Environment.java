import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Environment extends Sprite
{
    private ImageHandler imageHandler;
    private AudioHandler audioHandler;
    private String folderName;
    private int ground;
    private int flyBoundary;

    // image file names
    private String bgImage = "background.png";
    // sound file names
    public String bgm = "bgm.wav";
    public String giorno = "giorno.wav";
    public String jojo = "jojo.wav";

    protected Timer timer;
    protected static int timerCount = 0;
    protected TimerTask timerTask;
    protected boolean canDio;
    protected boolean canGiorno;
    protected int dioTimer = 5; // time to wait before dio moves
    protected ConcurrentHashMap<Integer, Minion> minions;

    public Environment(GamePanel p){
        super(p);
        name = "environment";
        folderName = name; // accessible through imagehandler class
        width = 1280;
        height = 720;
        ground = 500;
        flyBoundary = 300;
        canDio = false;
        timer = new Timer();
        timerTask = new TimerTask(){
        
            @Override
            public void run() {
                // TODO Auto-generated method stub
                timerCount++;
                System.out.println("timercount: " + timerCount);
                checkDio();
                //spawnMinions();
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
            System.out.println("action is coming");
        }
    }

    public void spawnMinions(){
        if(timerCount % 5 == 0){
            // spawn minions? perhaps some kinda jokey thing?
            int r = getRandomInt(1, 6); // roll d dice
            //if(r == 1 || r == 2) spawn1(); 
            spawn1();
            System.out.println("spawn1");
            // spawn2();
            // spawn3();
        }
        else return;
    }

    public void spawn1(){ // spawn a fastboi
        Minion m1, m2;
        m1 = new Minion(panel);
        m2 = new Minion(panel);
        m1.DX = getRandomInt(5, 10);
        m2.DX = getRandomInt(5, 10);
        minions.put(m1.getId(), m1);
        minions.put(m2.getId(), m2);
        minions.toString();
    }
    
    public void spawn2(){ // spawn 4 slowbois
        Minion m1, m2, m3, m4;
        m1 = m2 = new Minion(panel);
        m1.DX = getRandomInt(10, 16);
        m2.DX = getRandomInt(10, 16);
        minions.put(m1.getId(), m1);
        minions.put(m2.getId(), m2);
    }

    private void initImages(){
        this.imageHandler.loadImage(bgImage);
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
            } else {
                minions.remove(minion.getId());
            }
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