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
    private String imgPath = "Assets/images/";
    protected String auPath = "Assets/sounds/";

    protected boolean visible;

    public Sprite(){
        visible = true;
        System.out.println("sprite created!");
    }
    
    public void setSize(int w, int h){
        this.width = w;
        this.height = h;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isVisible(){
        return this.visible;
    }

    public void setVisible(boolean v){
        this.visible = v;
    }

    public String getName(){
        return this.name;
    }
    
    public void setName(String s){
        this.name = s;
    }

    public void setImgPath(String s){
        this.imgPath = s;
    }
    
    public String getImgPath(){
        return this.imgPath;
    }

    @Override
	public String toString(){
		String s = new String();
		s = 
			this.name + 
			" x:" + this.x +
			" y:" + this.y;
		return s;
    }
    
    public static int getRandomInt(int max, int min){
        // max++;
        int val = 0;
        // int temp = min-1;
        // if(temp > 0){
        //     min = temp;
        // }
        val = (int) (Math.random() * ((max-min) + 1) + min);
        return val;
    }

}