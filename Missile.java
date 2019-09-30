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

public class Missile extends Sprite
{
    private int MISSILE_SPEED = 5;
    private int boardWidth;
    private Color color = null;
    private Rectangle2D shape = null;
    private Environment environment;
    
    public Missile(GamePanel p, int x, int y){
        super(p);
        environment = new Environment(p);
        super.x = x;
        super.y = y;
        width = 20;
        height = 20;
        boardWidth = environment.width;
        this.color = Color.CYAN;
        this.shape = new Rectangle2D.Double (x, y, width, height);
        initMissile();
        System.out.println("missile created!");
    }

    public int getSpeed(){return this.MISSILE_SPEED;}
    public void setSpeed(int speed){this.MISSILE_SPEED = speed;}

    public void initMissile(){
        g2.setColor(this.color);
		g2.fill (this.shape);
    }

    public void draw (Graphics2D g2) {
		g2.setColor (color);
		g2.fill (shape);
    }

    public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Rectangle2D.Double (x, y, width, height));
	}

    public void move(){
        x += MISSILE_SPEED;
        if(x > boardWidth){
            visible = true;
        }
    }
}