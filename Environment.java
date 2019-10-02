import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer; 
import java.util.TimerTask; 

public class Environment extends Sprite
{
    private ImageHandler imageHandler;
    private AudioHandler audioHandler;
    private String folderName;
    private int ground;

    // image file names
    private String bgImage = "background.png";
    // sound file names
    private String bgm = "bgm.wav";

    protected Timer timer;
    protected  static int timerCount = 0;
    protected TimerTask timerTask;
    protected boolean canDio;
    protected int dioTimer = 5; // time to wait before dio moves

    public Environment(GamePanel p){
        super(p);
        name = "environment";
        folderName = name; // accessible through imagehandler class
        width = 1280;
        height = 720;
        ground = 500;
        canDio = false;
        timer = new Timer();
        timerTask = new TimerTask(){
        
            @Override
            public void run() {
                // TODO Auto-generated method stub
                timerCount++;
                System.out.println("timercount: " + timerCount);
                checkDio();
                spawnMinion();
            }
        };

        imageHandler = new ImageHandler(folderName);
        audioHandler = new AudioHandler(folderName);
        initImages();
        initAudio();
    }

    public void startTimer(){timer.scheduleAtFixedRate(timerTask, 0, 1*1000);}

    public int getTimerCount(){return this.timerCount;}

    public boolean getCanDio(){return this.canDio;}
    public void setCanDio(boolean v){this.canDio = v;}

    public void checkDio(){if(this.timerCount >= dioTimer) canDio = true;}
    public void spawnMinion(){
        if(timerCount % 3 == 0){
            // spawn minions? perhaps some kinda jokey thing?
        }
    }

    private void initImages(){
        this.imageHandler.loadImage(bgImage);
        System.out.println("initImages for Environment:" + imageHandler.toString());
    }

    private void initAudio(){
        this.audioHandler.loadClip(bgm);
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


    // main for testing
    // public static void main(String[] args) {
    //     GamePanel p = new GamePanel();
    //     Environment e = new Environment(p);
    //     System.out.println(e.toString());
    //     e.audioHandler.playClip("bgm.wav");
    //     for (int i = 0; i < 200; i++) {
    //         //System.out.println(e.getTimerCount());
    //     }
    //     System.out.println("2mins: " + e.checkDio());
    //     System.out.println("end e test");
    // }
}