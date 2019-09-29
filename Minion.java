import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;


/*
	this class will represent a minion: 
		- a purple minion
		- give jo 1 point when he kills it
*/

public class Minion extends Sprite{	
	private static int DY = 5;

	Graphics2D g2;
	private GamePanel panel;
	private Dimension dimension;
	private Color backgroundColor;

	private AudioHandler audioHandler;
	private Jo jo;

	private static int health;
	private int score;

	private String clack = "clack.au";
	private String hitBat = "hitBat.au";

	public Minion (GamePanel p, Jo b) {
		super();
		super.name = "Minion";
		super.width = 30;
		super.height = 40;
		health = getRandomInt(0, 4); // return a random int between 0 and 4
		score = health;

		panel = p;
		jo = b;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		setPosition();					// set initial position of minion

		audioHandler = new AudioHandler("minion");
		initAudio();

		System.out.println("minion created!" + this.toString());
	}

	private void initAudio(){
        String bg = "clack.au";
		this.audioHandler.loadClip(bg);
		bg = "hitBat.au";
        this.audioHandler.loadClip(bg);
        System.out.println("initAudio for Minion:" + audioHandler.toString());
    }

	public void setPosition () {
		// spawn locations based on bgimage: 95,95 - 60,130 // 190,95 - 215,130
		int r = getRandomInt(0, 7);	// roll a dice.
		if(r % 2 == 0) spawn1();
		else spawn2();						// set y to top of the panel
	}

	public void spawn1(){
		x = 200;
		y = 100;
	}
	public void spawn2(){
		x = 90;
		y = 100;
	}

	public void draw (Graphics2D g2) {
		g2.setColor (Color.MAGENTA);
		//g2.drawImage(ballImage, x, y, width, height, null);
		g2.fill (new Ellipse2D.Double (x, y, width, height));
	}

	public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Ellipse2D.Double (x, y, width, height));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

	public boolean playerHitsBall () {

		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectPlayer = jo.getBoundingRectangle();
		
		if (rectBall.intersects(rectPlayer))
			return true;
		else
			return false;
	}

	public boolean isOffScreen () {
		if (y + height > dimension.height)
			return true;
		else
			return false;
	}


	public void move () {

		if (!panel.isVisible ()) return;

		y = y + DY;

		boolean hitPlayer = playerHitsBall();

		if (hitPlayer || isOffScreen()) {
			if (hitPlayer) {
				jo.setHealth(jo.getHealth()-1);
				String s = jo.printStats();
				//playClip (1);			// play clip if jo hits minion
				audioHandler.getClip(hitBat).play();
			}
			else {					// play clip if minion falls out at bottom
				//playClip (2);
				audioHandler.getClip(clack).play();
			}

			try {					// take a rest if jo hits minion or
				Thread.sleep (1000);		//   minion falls out of play.
			}
			catch (InterruptedException e) {};

			setPosition ();				// re-set position of minion
		}
		
	}

}