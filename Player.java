import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Sprite
{
	protected int DX;		// amount of pixels to move in one keystroke
	protected int DY;		// amount of pixels to jump in one keystroke

	protected boolean facingLeft;
	protected boolean facingRight;

	protected AudioHandler audioHandler;
	protected ImageHandler imageHandler;
	protected boolean canShoot;
	
	protected List<Missile> missiles;
	protected GamePanel panel;

	protected Environment environment;

	public Player (GamePanel p, String name) {
		super(p);
		panel = p;
		facingLeft = true;
		facingRight = false;
		environment = new Environment(p);
		canShoot = false;
		super.name = name;
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
		if (!panel.isVisible())
			return;
		facingLeft = false;
		facingRight = false;
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
		this.canShoot = true;
		int xV = x + width;
		if (facingRight)
			xV = xV * -1;
		missiles.add(new Missile(panel, xV, y + height / 2));
		// play sound
		System.out.println("ORA!");
	}

	public List<Missile> getMissiles() {return this.missiles;}

	public boolean getCanShoot() {return this.canShoot;}

	public void setCanShoot(boolean v) {this.canShoot = v;}

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

	// audio stuff
	///abstract void initAudio();
}