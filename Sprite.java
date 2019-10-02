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
    protected int width; // size of entity
	protected int height;
    protected String name;

    protected boolean visible;

    protected Graphics2D g2;
	protected GamePanel panel;
	protected Dimension dimension;
    protected Color backgroundColor;

    protected boolean facingLeft;
	protected boolean facingRight;
	protected boolean movingLeft, movingRight;

    protected int score;
	protected int worth;
	protected int health;
    
    protected Environment environment;

    public Sprite(GamePanel p){
        visible = true;
        facingRight = false;
		movingLeft = movingRight = false;
        panel = p;
        environment = p.getEnvironment();
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
        dimension = panel.getSize();
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
    
    public static int getRandomInt(int max, int min){
        int val = 0;
        val = (int) (Math.random() * ((max-min) + 1) + min);
        return val;
    }

    public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, width, height);
	}

    

}