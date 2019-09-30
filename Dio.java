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
import java.util.ArrayList;
import java.util.List;

/*
this class will represent the main enemy boss man
	- a red Rectangle
	- can jump
	- can move left and right
*/
public class Dio extends Player
{
	private List<Missile> missiles;
	GamePanel panel;
	
	private static int score = 0;
	private static int health = 10;

	private static int DY;
	private static int DX;

	private AudioHandler audioHandler;
	private boolean canShoot;

	private String clack = "clack.au";
	private String hitBat = "hitBat.au";
	private String oof = "oof.wav";

	private Jo jo;

    public Dio(GamePanel p, Jo j){
		super(p);
		panel = p;
		DY = 10;
		DX = 10;
		XSTEP = 6;
		YSTEP = 20;
		super.name = "Dio";
		super.width = 40;
		super.height = 60;
		super.x = 1190;
		super.y = 40;
		canShoot = false;
		jo = j;

		this.missiles = new ArrayList<>();
		
		audioHandler = new AudioHandler("minion");
		initAudio();
        System.out.println("Dio created! " + this.toString());
	}

	public List<Missile> getMissiles(){return this.missiles;}

	public boolean getCanShoot(){return this.canShoot;}
	public void setCanShoot(boolean v){this.canShoot = v;}

	public void fire(){
		this.canShoot = true;
		int xV = x + width;
		if(facingRight) xV = xV*-1;
		missiles.add(new Missile(
			panel,
			xV, 
			y+height / 2
		));
		// play sound
		System.out.println("ORA!");
	}
	
	private void initAudio(){
		this.audioHandler.loadClip(this.oof);
		this.audioHandler.loadClip(this.clack);
		this.audioHandler.loadClip(this.hitBat);
        System.out.println("initAudio for Dio:" + audioHandler.toString());
    }

    public void draw (Graphics2D g2) {
		g2.setColor (Color.RED);
		g2.fill (new Rectangle2D.Double (x, y, width, height));
    }
    public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Rectangle2D.Double (x, y, width, height));
	}

	public void idle(){
		if (!panel.isVisible ()) return;
		facingLeft = false;
		facingRight = false;
	}
	
	public void land(){
		if (!panel.isVisible ()) return;

		y = y + YSTEP;

		if(y > 0){
			y = this.environment.getGround();
		}
	}
	
	public void jump(){
		if (!panel.isVisible ()) return;

		y = y - YSTEP;

		if(y < 0){
			y = this.environment.getGround();
		}
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

		boolean goLeft = false;
		boolean goRight = false;

		if(y >= environment.getGround()){
			// the minion has landed, look for jo now
			//x += DX;
			if(x - jo.getX() < 0){ // jo is to your right
				goRight = true;
				goLeft = false;
				moveRight();
			}
			else{ // jo is to your left
				goRight = false;
				goLeft = true;
				moveLeft();
			}
		}
		else y = y + DY; // fall to the ground before seeking jo

		boolean hitPlayer = playerHitsBall();

		if (hitPlayer || isOffScreen()) {
			if (hitPlayer) {
				jo.setHealth(jo.getHealth()-1);
				// String s = jo.printStats();
				System.out.println(jo.printStats());
				//playClip (1);			// play clip if jo hits minion
				audioHandler.getClip(oof).play();
			}
			else {					// play clip if minion falls out at bottom
				//playClip (2);
				audioHandler.getClip(clack).play();
			}

			try {					// take a rest if jo hits minion or
				Thread.sleep (500);		//   minion falls out of play.
			}
			catch (InterruptedException e) {};

			//setPosition ();				// re-set position of minion
		}
		
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
	
	public int getHealth(){return this.health;}
	public int getScore(){return this.score;}
	public void setHealth(int v){this.health = v;}
	public void setScore(int v){this.score = v;}

	public String printStats(){
		String s = "HP: "
			+ this.health
			+ "\nScore: "
			+ this.score;

		//System.out.println(s);
		return s;
	}
	
}