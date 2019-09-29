import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GamePanel extends JPanel implements Runnable, KeyListener 
{					
	private Jo jo = null;
	private Minion minion = null;
	private Environment envi = null;
	private String stats;

	AudioClip playSound = null;

	private Thread gameThread;
	boolean isRunning;

	private String imgPath = "Assets/images/environment/";
	private String auPath = "Assets/sounds/";
	private Image bgImage;

	private int width = 1280;
	private int height = 720;

	// private Dimension dimension;

	public GamePanel () {
		// dimension = this.getSize();
		// System.out.println("d.width = " + dimension.width + "\nd.height = " + dimension.height );

		setBackground(Color.CYAN);
		addKeyListener(this);			// respond to key events
		setFocusable(true);
    	requestFocus();    			// the GamePanel now has focus, so receives key events

		loadClips ();

		gameThread = null;
		isRunning = false;

		bgImage = loadImage (imgPath + "background.png");
		//System.out.println("loaded bg!");
		System.out.println("GP created!");
	}

	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				gameUpdate();
				gameRender();
				Thread.sleep (100);	// increase value of sleep time to slow down minion
			}
		}
		catch(InterruptedException e) {}
	}

	// implementation of KeyListener interface

	public void keyPressed (KeyEvent e) {

		if (jo == null)
			return;

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			jo.moveLeft();
		}
		else if (keyCode == KeyEvent.VK_RIGHT) {
			jo.moveRight();
		}
		else if (keyCode == KeyEvent.VK_UP) {
			jo.jump();
		}
	}

	public void keyReleased (KeyEvent e) {
		if (jo == null)
			return;

		int keyCode = e.getKeyCode();

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

	public void loadClips() {

		try {
			playSound = Applet.newAudioClip (
					getClass().getResource(auPath + "Background.wav"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && playSound != null)
			playSound.play();

	}

	public Image loadImage(String imageName) {
		//return new ImageIcon(fileName).getImage();
		ImageIcon ii = null;
		Image i = null;
		try {
			ii = new ImageIcon(imageName);
			i = ii.getImage();
			System.out.println("Loaded Image!: " + imageName);
		} catch (Exception e) {
			System.out.println("error loading image: " + imageName);
			System.out.println(e.getMessage());
		}
		return i;
	}

	public void gameUpdate () {
		minion.move();
	}

	public void gameRender () {				// draw the game objects

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(bgImage, 0, 0, width, height, null);		// draw the background image
		minion.draw(g2);					// draw the minion
		jo.draw(g2);					// draw the jo
		//stats = jo.printStats();
	}	

	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			isRunning = true;
			jo = new Jo (this);
			minion = new Minion (this, jo);
			playSound.loop();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void endGame() {					// end the game thread

		if (isRunning) {
			isRunning = false;
			playSound.stop();
		}
	}

}