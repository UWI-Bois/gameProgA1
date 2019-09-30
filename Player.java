import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public abstract class Player extends Sprite
{
	protected int XSTEP;		// amount of pixels to move in one keystroke
	protected int YSTEP;		// amount of pixels to jump in one keystroke

	protected boolean facingLeft;
	protected boolean facingRight;

	protected Environment environment;

	

	public Player (GamePanel p) {
		super(p);
		facingLeft = true;
		facingRight = false;
		environment = new Environment(p);
		System.out.println("Player created!");
	}

	abstract void draw (Graphics2D g2);
	abstract void erase (Graphics2D g2);

	public Rectangle2D.Double getBoundingRectangle() {return new Rectangle2D.Double (x, y, width, height);}

	abstract void moveLeft();
	abstract void moveRight();
}