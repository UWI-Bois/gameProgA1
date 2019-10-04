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
	private int id;
	
	protected int startNextSpawn;
    private static int idCounter = 0;

	Graphics2D g2;
	private GamePanel panel;
	private Dimension dimension;
	private Color backgroundColor;

	public AudioHandler audioHandler;
	public ImageHandler imageHandler;

	private Jo jo;
	private Environment environment;

	public String clack = "clack.au";
	public String oof = "oof.wav";

	public String explode = "explode.gif";
	public String spunk = "spunk.png";

	private Color color;

	public Minion (GamePanel p) {
		super(p);
		super.name = "minion";
		setSize();
		DX = 5;
		DY = 7;
		y = 80;
		damage = 1;
		id = idCounter;
		idCounter++;
		startNextSpawn = 10;
		health = getRandomInt(1, 6); // return a random int between 0 and 4
		worth = health;
		facingLeft = facingRight = false;

		panel = p;
		jo = p.getJo();
		environment = p.getEnvironment();
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		setPosition();					// set initial position of minion

		audioHandler = new AudioHandler(name);
		imageHandler = new ImageHandler(name);
		initAudio();
		initImages();

		System.out.println("minion created!" + this.toString());
	}

	public int getId(){return this.id;}

	private void initAudio(){
		this.audioHandler.loadClip(this.oof);
		this.audioHandler.loadClip(this.clack);
        System.out.println("initAudio for Minion:" + audioHandler.toString());
	}
	
	private void initImages(){
        this.imageHandler.loadImage(explode);
        this.imageHandler.loadImage(spunk);
        System.out.println("initImages for minion:" + imageHandler.toString());
    }

	private void setSize () {
		// spawn locations based on bgimage: 95,95 - 60,130 // 190,95 - 215,130
		int w1 = getRandomInt(30, 80);	
		int h1 = getRandomInt(40, 90);	
		width = w1;
		height = h1;
	}

	public void setColor(Color c){ this.color = c;}
	public Color getColor(){ return this.color;}

	public AudioHandler getAudioHandler(){return this.audioHandler;}

	private void setPosition () {
		// spawn locations based on bgimage: 95,95 - 60,130 // 190,95 - 215,130
		int r = getRandomInt(1, 8);	// roll a dice.
		if(environment.getTimerCount() > startNextSpawn){
			if(r == 1 || r == 2) spawnArea1();
			else if (r == 3 || r == 4) spawnArea2();
			else if (r == 5 || r == 6) spawnArea3();
			else if (r == 7 || r == 8) spawnArea4();
		}
		else{
			if(r % 2 == 0) spawnArea1();
		else spawnArea2();	
		}
	}

	private void spawnArea1(){
		x = 870;
	}
	private void spawnArea2(){
		x = 1000;
	}
	private void spawnArea3(){
		x = 235;
	}
	private void spawnArea4(){
		x = 355;
	}

	public void draw (Graphics2D g2) {
		g2.setColor (color);
		//g2.drawImage(ballImage, x, y, width, height, null);
		g2.fill (new Ellipse2D.Double (x, y, width, height));
	}

	public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Ellipse2D.Double (x, y, width, height));
	}


	public boolean playerHitsMinion () {

		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectPlayer = jo.getBoundingRectangle();
		
		if (rectBall.intersects(rectPlayer))
			return true;
		else
			return false;
	}

	public boolean isOffScreen () {
		if (y + height > dimension.height) return true;
		else return false;
	}


	public void move () {

		if (!panel.isVisible ()) return;

		// boolean goLeft = false;
		// boolean goRight = false;

		findJo();

		boolean hitPlayer = playerHitsMinion();
		// boolean hitMissile = 

		if (hitPlayer || isOffScreen()) {
			if (hitPlayer) {
				jo.setHealth(jo.getHealth()-this.damage);
				// String s = jo.printStats();
				//System.out.println(jo.printStats());
				//playClip (1);			// play clip if jo hits minion
				audioHandler.getClip(oof).play();
				this.yeet();
			}
			else {					// play clip if minion falls out at bottom
				//playClip (2);
				audioHandler.getClip(clack).play();
			}

			try {					// take a rest if jo hits minion or
				Thread.sleep (100);		//   minion falls out of play.
			}
			catch (InterruptedException e) {};

			//setPosition ();				// re-set position of minion
		}
		
	}

	public void findJo(){
		if(y >= environment.getGround()){
			// the minion has landed, look for jo now
			//x += DX;
			if(x - jo.getX() < 0){ // jo is to your right
				// goRight = true;
				// goLeft = false;
				moveRight();
			}
			else{ // jo is to your left
				// goRight = false;
				// goLeft = true;
				moveLeft();
			}
		}
		else y = y + DY; // fall to the ground before seeking jo
	}

	public void moveLeft () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x - DX;

		if (x < 0) {					// hits left wall
			x = 0;
			//playClip (1);
		}
    }
    public void moveRight () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x + DX;

		if (x + width >= dimension.width) {		// hits right wall
			x = dimension.width - width;
			//playClip (1);
		}

		facingRight = true;
		facingLeft = false;
	}
	
	@Override
	public String toString(){
		String s = super.toString();
		s += "id: " + this.id;
		return s;
	}
}