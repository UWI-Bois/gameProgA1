import java.util.Set;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GamePanel extends JPanel implements Runnable, KeyListener 
{                   
    private Jo jo = null;
    private Dio dio = null;
    // private Minion minion = null;
    private Environment environment;
    private int tSpeed; // thread sleep int

    private Thread gameThread;
    boolean isRunning;

    public GamePanel () {
        environment = new Environment(this);
        setBackground(Color.CYAN);
        addKeyListener(this);           // respond to key events
        setFocusable(true);
        requestFocus();             // the GamePanel now has focus, so receives key events
        tSpeed = 100;
        gameThread = null;
        isRunning = false;
        System.out.println("GP created!");
    }

    // implementation of Runnable interface

    public void run () {
        try {
            isRunning = true;
            while (isRunning) {
                gameUpdate();
                gameRender();
                if(environment.getTimerCount() == environment.dioTimer){
                    environment.getAudioHandler().getClip(environment.bgm).stop();
                    dio.getAudioHandler().getClip(dio.zaWarudo).play();
                    environment.getAudioHandler().getClip(environment.giorno).loop();
                }
                Thread.sleep (tSpeed); // increase value of sleep time to slow down minion
            }
        }
        catch(InterruptedException e) {}
    }

    // implementation of KeyListener interface

    public void keyPressed (KeyEvent e) {

        if (jo == null)
            return;

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_SPACE && !jo.getCanShoot()){
                jo.setCanShoot(true);
                jo.fire();
               //System.out.println("pressed space");
        }
            
        if (keyCode == KeyEvent.VK_LEFT) {
            jo.movingLeft = true;
            jo.movingRight = false;
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            jo.movingRight = true;
            jo.movingLeft = false;
        }
        else if (keyCode == KeyEvent.VK_UP) {
            jo.jump();
        }
    }

    public void keyReleased (KeyEvent e) {
        if (jo == null)
            return;

        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_SPACE){
            jo.setCanShoot(false);
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            jo.idle();
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            jo.idle();
        }
        else if (keyCode == KeyEvent.VK_UP) {
            jo.land();
        }
    }

    public void keyTyped (KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            jo.jump();
        }
    }

    private void updateMissiles() {
        Set<Map.Entry<Integer, Missile>> set = jo.missiles.entrySet();
		for(Map.Entry<Integer, Missile> m : set)
		{
            Missile missile = m.getValue();
            if (missile.isVisible()) {
                missile.move();
            }
            else if(missile.isDead) {
                jo.missiles.remove(missile.getId());
                System.out.println("removed deaed missile");
            } 
            else {
                jo.missiles.remove(missile.getId());
                System.out.println("removed missile");
            }
		}
    }
    
    
    
    private void updateJo() {
        if(jo.movingLeft) jo.moveLeft();
        if(jo.movingRight) jo.moveRight();
    }

    private void drawMissiles(Graphics2D g2){
        Set<Map.Entry<Integer, Missile>> set = jo.missiles.entrySet();
		for(Map.Entry<Integer, Missile> m : set)
		{
			m.getValue().draw(g2);
		}
    }

    

    public void gameUpdate () {
        // minion.move();
        environment.spawnMinions();
        environment.updateMinions();
        
        if(environment.getCanDio()) dio.move();
        //dio.move();
        updateJo();
        updateMissiles();
    }

    public void gameRender () {             // draw the game objects

        Graphics2D g2 = (Graphics2D) getGraphics(); // get the graphics context for the panel
        g2.drawImage(
            environment.getImageHandler().getImage("background.png"), 
            0, 0, 
            environment.width, environment.height, null
        );      // draw the background image
        // minion.draw(g2);                // draw the minion
        environment.drawMinions(g2);
        jo.draw(g2);
        if(!dio.isDead) dio.draw(g2);
        drawMissiles(g2);
        drawGUI(g2);
        //stats = jo.printStats();
    }   

    public void startGame() {               // initialise and start the game thread 

        if (gameThread == null) {
            isRunning = true;
            environment.startTimer(); 
            jo = new Jo (this);
            dio = new Dio (this);
            // minion = new Minion (this);
            //minions = new ConcurrentHashMap<Integer, Minion>();
            environment.getAudioHandler().getClip("bgm.wav").loop();
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void endGame() {                 // end the game thread

        if (isRunning) {
            // h o df ff f
            isRunning = false;
            environment.getAudioHandler().getClip("giorno.wav").stop();
            //playSound.stop();
        }
    }

    public Environment getEnvironment(){return this.environment;}
    public Jo getJo(){return this.jo;}
    public Dio getDio(){return this.dio;}
    //public Minion getMinion(){return this.minion;}

    public void drawGUI(Graphics2D g2){
        Font f = new Font ("Impact", Font.PLAIN, (40));
        g2.setFont(f);
        g2.setColor(Color.BLACK);
        g2.drawString("Time: " + Integer.toString(environment.getTimerCount()),10,70);
        g2.drawString("HP: " + Integer.toString(jo.getHealth()),10,130);
        g2.drawString("Score: " + Integer.toString(jo.getScore()),10,190);
        g2.drawString("DIO HP: " + Integer.toString(dio.getHealth()),500,70);

    }
    

}