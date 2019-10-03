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
	protected AudioHandler audioHandler;
	protected ImageHandler imageHandler;
	protected boolean canShoot;
	
	protected ConcurrentHashMap<Integer,Missile> missiles;
	protected GamePanel panel;

	public Player (GamePanel p, String name) {
		super(p);
		panel = p;
		canShoot = false;
		super.name = name;
		missiles = new ConcurrentHashMap<Integer,Missile>();
		audioHandler = new AudioHandler(name);
		imageHandler = new ImageHandler(name);
		System.out.println("Player created! Name: " + name);
	}

	public abstract void draw (Graphics2D g2);
	
	public Rectangle2D.Double getBoundingRectangle() {return new Rectangle2D.Double (x, y, width, height);}

	// missile stuff
	//public abstract void fire();

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
	public ImageHandler getImageHandler(){return this.imageHandler;}
    public AudioHandler getAudioHandler(){return this.audioHandler;}
	///abstract void initAudio();
}