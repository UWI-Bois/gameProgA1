import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Player extends Sprite
{
	protected int DX;		// amount of pixels to move in one keystroke
	protected int DY;		// amount of pixels to jump in one keystroke

	protected AudioHandler audioHandler;
	protected ImageHandler imageHandler;
	protected boolean canShoot;
	
	protected ConcurrentHashMap<Integer,Missile> missiles;
	protected GamePanel panel;

	public Player (GamePanel p, String name) {
		super(p);
		panel = p;
		facingLeft = true;
		canShoot = false;
		super.name = name;
		missiles = new ConcurrentHashMap<Integer,Missile>();
		audioHandler = new AudioHandler(name);
		imageHandler = new ImageHandler(name);
		System.out.println("Player created! Name: " + name);
	}

	public abstract void draw (Graphics2D g2);
	public void erase(Graphics2D g2) {
		g2.setColor(backgroundColor);
		g2.fill(new Rectangle2D.Double(x, y, width, height));
	}

	public void idle() {
		if (!panel.isVisible()) return;
		facingLeft = false;
		facingRight = false;
		movingLeft = false;
		movingRight = false;
	}

	public void land() {
		if (!panel.isVisible())
			return;

		y = y + DY;

		if (y > 0) {
			y = this.environment.getGround();
		}
	}

	public void jump() {
		if (!panel.isVisible())
			return;

		y = y - DY;

		if (y < 0) {
			y = this.environment.getGround();
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
		facingRight = false;
		facingLeft = true;
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

	public Rectangle2D.Double getBoundingRectangle() {return new Rectangle2D.Double (x, y, width, height);}

	// missile stuff
	public void fire() {
		int xV = x + width;
		if (facingRight) xV = xV * -1;
		Missile m = new Missile(panel, xV, y + height / 4);
		missiles.put(m.getId(), m);
		// play sound
		System.out.println("ORA!");
	}

	public ConcurrentHashMap<Integer,Missile> getMissiles() {return this.missiles;}

	public boolean getCanShoot() {return this.canShoot;}

	public void setCanShoot(boolean v) {this.canShoot = v;}

	public String printStats(){
		String s = "HP: "
			+ this.health
			+ "\nScore: "
			+ this.score;

		//System.out.println(s);
		return s;
	}

	// audio stuff
	///abstract void initAudio();
}