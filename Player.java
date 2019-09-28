import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public abstract class Player extends Sprite{

	// private static final int XSIZE = 40;		// width of the bat
	// private static final int YSIZE = 40;		// height of the bat
	// private static final int XSTEP = 10;		// amount of pixels to move in one keystroke
	// private static final int YSTEP = 7;		// amount of pixels to jump in one keystroke
	// private static final int YPOS = 535;		// vertical position of the bat


	protected Graphics2D g2;
	protected GamePanel panel;
	protected Dimension dimension;
	protected Color backgroundColor;

	// private int x;
	// private int y;
	// private boolean facingLeft;
	// private boolean facingRight;
	// AudioClip hitWallSound = null;

	public Player (GamePanel p) {
		super();
		//super.name = "Jo";
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		dimension = panel.getSize();

		//super.panel = p;
        //super.Graphics g = panel.getGraphics ();
        //super.g2 = (Graphics2D) g;
        //super.backgroundColor = panel.getBackground ();
		//super.dimension = panel.getSize();

		//loadClips();

		// Random random = new Random();
		// super.x = random.nextInt (dimension.width - XSIZE);
		// super.y = YPOS;

		// batLeft = panel.loadImage("batLeft.gif");
		// batRight = panel.loadImage("batRight.gif");

		// facingLeft = false;
		// facingRight = true;
		System.out.println("Player created!");
	}

	abstract void draw (Graphics2D g2);
	abstract void erase (Graphics2D g2);

	abstract Rectangle2D.Double getBoundingRectangle();

	abstract void moveLeft();
	abstract void moveRight();
}