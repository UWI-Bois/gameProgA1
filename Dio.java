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
import java.util.Timer; 
import java.util.TimerTask; 

/*
this class will represent the main enemy boss man
	- a red Rectangle
	- can jump
	- can move left and right
*/
public class Dio extends Player {

	private String clack = "clack.au";
	private String hitBat = "hitBat.au";
	private String oof = "oof.wav";

	private Jo jo;

	public Dio(GamePanel p) {
		super(p, "dio");
		DY = 15;
		DX = 10;
		super.width = 40;
		super.height = 60;
		super.x = 1190;
		super.y = 40;
		jo = p.getJo();

		initAudio(); // make an abstract class in player
		//System.out.println("Dio created! " + this.toString());
	}

	private void initAudio() {
		this.audioHandler.loadClip(this.oof);
		this.audioHandler.loadClip(this.clack);
		this.audioHandler.loadClip(this.hitBat);
		System.out.println("initAudio for Dio:" + audioHandler.toString());
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.fill(new Rectangle2D.Double(x, y, width, height));
	}

	public boolean playerHitsMinion() {

		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectPlayer = jo.getBoundingRectangle();

		if (rectBall.intersects(rectPlayer))
			return true;
		else
			return false;
	}

	public boolean isOffScreen() {
		if (y + height > dimension.height)
			return true;
		else
			return false;
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

	
}