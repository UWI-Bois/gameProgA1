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
this class will represent the player/MC(main character).
	- a blue Rectangle
	- can shoot a projectile in the direction he is facing 
	- can jump
	- can move left and right
*/
public class Jo extends Player
{
	private List<Missile> missiles;
	GamePanel panel;

	private AudioHandler audioHandler;
	private boolean canShoot;

    public Jo(GamePanel p){
		super(p);
		panel = p;
		XSTEP = 6;
		YSTEP = 20;
		super.name = "Jo";
		super.width = 40;
		super.height = 60;
		super.x = 1200;
		super.y = environment.getGround();
		canShoot = false;

		this.missiles = new ArrayList<>();
		
		audioHandler = new AudioHandler("jo");
		//initAudio();
        System.out.println("Jo created! " + this.toString());
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
			y+height / 4
		));
		// play sound
		System.out.println("ORA!");
	}
	
	private void initAudio(){
		//this.audioHandler.loadClip(oof);
        System.out.println("initAudio for Minion:" + audioHandler.toString());
    }

    public void draw (Graphics2D g2) {
		g2.setColor (Color.BLUE);
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
    
    public void moveLeft () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x - XSTEP;

		if (x < 0) {					// hits left wall
			x = 0;
			//playClip (1);
		}
    }
    public void moveRight () {

		if (!panel.isVisible ()) return;

		// erase();					// no need to erase with background image

		x = x + XSTEP;

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