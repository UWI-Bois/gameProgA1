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

public class Sprite
{
    protected int x;    // pos on the board
    protected int y;
    protected int DX;		// amount of pixels to move in one keystroke
	protected int DY;		// amount of pixels to jump in one keystroke
    protected int width; // size of entity
	protected int height;
    protected String name;

    protected boolean visible;
	protected boolean isDead;
	protected boolean isFlying;

    protected Graphics2D g2;
	protected GamePanel panel;
	protected Dimension dimension;
    protected Color backgroundColor;

    protected boolean facingLeft;
	protected boolean facingRight;
	protected boolean movingLeft, movingRight, jumping;

    protected int score;
	protected int worth;
	protected int health;
    
    protected Environment environment;

    public Sprite(GamePanel p){
        visible = true;
        facingRight = facingLeft = false;
		movingLeft = movingRight = jumping = false;
        panel = p;
        environment = p.getEnvironment();
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
        dimension = panel.getSize(); 
        isDead = false;
        //environment = new Environment(p);
        System.out.println("sprite created!");
    }
    
    public void setSize(int w, int h){
        this.width = w;
        this.height = h;
}

    public int getX(){return this.x;}

    public int getY(){return this.y;}

    public int getWidth() {return this.width;}

    public int getHeight() {return this.height;}

    public boolean isVisible(){return this.visible;}

    public void setVisible(boolean v){this.visible = v;}

    public String getName(){return this.name;}
    
    public void setName(String s){this.name = s;}

    public int getHealth(){return this.health;}
	public int getScore(){return this.score;}
	public void setHealth(int v){this.health = v;}
	public void setScore(int v){this.score = v;}
	public void addScore(int v){this.score += v;}
	public int getWorth(){return this.worth;}

    @Override
	public String toString(){
		String s = new String();
		s = 
			this.name + 
			" x:" + this.x +
            " y:" + this.y +
            " health :" + this.health +
            " worth: " + this.worth +
            " score: " + this.score;
		return s;
    }
    
    public static int getRandomInt(int min, int max){
		int val = -1;
		Random r = new Random();
		val = min + r.nextInt(max - min + 1);
		//System.out.println("getRandomInt(" + max +"," + min +")" + " says: val = " + val);
        return val;
    }

    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
    }
    
    public void yeet(){
		x = -100;
		y = -100;
		DY = 0;
        DX = 0;
        isDead = true;
    }
    
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

		isFlying = true;
		y = y - DY;

		if (y < environment.getFlyBoundary()) {
			//y = this.environment.getGround();
			y = environment.getFlyBoundary();
		}
	}
	public void moveLeft () {
        if(isDead) return;
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
        if(isDead) return;

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

    

}