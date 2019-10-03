import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
import java.util.Map;

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

    // private Minion minion;
    private ConcurrentHashMap<Integer, Minion> minions;
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
        // if(x < 0) MISSILE_SPEED = MISSILE_SPEED*-1;
        damage = 1;
        boardWidth = environment.width;
        this.color = Color.ORANGE;
        this.shape = new Rectangle2D.Double (x, y, width, height);
        initMissile();
        this.id = idCounter;
        idCounter++;
        minions = environment.getMinions();
        jo = p.getJo();
        dio = p.getDio();

        if(jo.facingLeft){
            MISSILE_SPEED = MISSILE_SPEED*-1;
        }
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
        if(x > boardWidth || x < 0){
            visible = false; // might have to change to true
            isDead = true;
        }
        

        Set<Map.Entry<Integer, Minion>> set = minions.entrySet();
		for(Map.Entry<Integer, Minion> m : set)
		{
            Minion minion = m.getValue();
            boolean hitMinion = missileHitsMinion(minion);
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
        }

        boolean hitDio = missileHitsDio();
        if(hitDio && !dio.isDead){
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

    public boolean missileHitsMinion (Minion minion) {
        boolean value = false;
        Rectangle2D.Double rectMissile = this.getBoundingRectangle();
        Rectangle2D.Double rectMinion = minion.getBoundingRectangle();
        if (rectMissile.intersects(rectMinion)) value = true;
        else value = false;
        return value;
    }

    public boolean missileHitsDio () {
		Rectangle2D.Double rectMinion = dio.getBoundingRectangle();
		Rectangle2D.Double rectMissile = this.getBoundingRectangle();
		if (rectMissile.intersects(rectMinion)) return true;
		else return false;
    }
    
    public void yeetMissile(){
        // x = -200;
        // y = -200;
        isDead = true;
        visible = false;
    }

    public Rectangle2D.Double getBoundingRectangle() {return new Rectangle2D.Double (x, y, width, height);}

}