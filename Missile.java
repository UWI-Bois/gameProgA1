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
    private int MISSILE_SPEED;
    private int damage;
    private int boardWidth;
    private Color color = null;
    private Rectangle2D shape = null;
    private Environment environment = null;
    private int id;
    private static int idCounter = 0;

    private Minion minion;
    private Jo jo;
    private Dio dio;
    
    public Missile(GamePanel p, int x, int y){
        super(p);
        environment = p.getEnvironment();
        super.x = x;
        super.y = y;
        width = 20;
        height = 20;
        MISSILE_SPEED = 10;
        damage = 1;
        boardWidth = environment.width;
        this.color = Color.ORANGE;
        this.shape = new Rectangle2D.Double (x, y, width, height);
        initMissile();
        this.id = idCounter;
        idCounter++;
        minion = p.getMinion();
        jo = p.getJo();
        dio = p.getDio();
        //System.out.println("missile created!");
    }

    public int getSpeed(){return this.MISSILE_SPEED;}
    public void setSpeed(int speed){this.MISSILE_SPEED = speed;}
    public int getDamage(){return this.damage;}
    public void setDamage(int dmg){this.damage = dmg;}

    public void initMissile(){
        g2.setColor(this.color);
        g2.fill (this.shape);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(new Rectangle2D.Double(x, y, width, height));
    }

    public void erase (Graphics2D g2) {
        g2.setColor (backgroundColor);
        g2.fill (new Rectangle2D.Double (x, y, width, height));
    }

    public void move(){
        x += MISSILE_SPEED;
        //System.out.println(x);
        if(x > boardWidth){
            visible = true; // might have to change to true
        }
        boolean hitMinion = missileHitsMinion();
        boolean hitDio = missileHitsDio();
        if(hitMinion){
            this.yeetMissile();
            int hp = minion.getHealth();
            hp-=this.getDamage();
            minion.setHealth(hp);
            // minion dies
            if(hp <= 0){
                minion.yeet();
                jo.addScore(minion.getWorth());
                System.out.println("Minion Slain!\nJo Stats:\n" + jo.toString() + "\nMinion Stats: " + minion.toString());
            }
        }
        if(hitDio){
            this.yeetMissile();
            dio.getAudioHandler().getClip(dio.wry).play();
            int hp = dio.getHealth();
            hp-=this.getDamage();
            dio.setHealth(hp);
            // dio dies
            if(hp <= 0){
                dio.yeet();
                jo.addScore(dio.getWorth());
                System.out.println("Dio Slain!\nJo Stats:\n" + jo.toString() + "\ndio Stats: " + dio.toString());
            }
        }
    }

    public int getId(){return this.id;}

    public boolean missileHitsMinion () {

		Rectangle2D.Double rectMinion = minion.getBoundingRectangle();
		Rectangle2D.Double rectMissile = this.getBoundingRectangle();
		
		if (rectMissile.intersects(rectMinion))
			return true;
		else
			return false;
    }
    public boolean missileHitsDio () {

		Rectangle2D.Double rectMinion = dio.getBoundingRectangle();
		Rectangle2D.Double rectMissile = this.getBoundingRectangle();
		
		if (rectMissile.intersects(rectMinion))
			return true;
		else
			return false;
    }
    
    public void yeetMissile(){
        x = -200;
        y = -200;
        isDead = true;
    }

    public Rectangle2D.Double getBoundingRectangle() {return new Rectangle2D.Double (x, y, width, height);}

}