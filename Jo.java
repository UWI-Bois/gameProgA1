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

/*
this class will represent the player/MC(main character).
	- a blue Rectangle
	- can shoot a projectile in the direction he is facing 
	- can jump
	- can move left and right
*/
public class Jo extends Player
{

    public Jo(GamePanel p){
		super(p, "jo");
		DX = 6;
		DY = 20;
		super.width = 40;
		super.height = 60;
		super.x = 1200;
		super.y = environment.getGround();
		//initAudio();
        //System.out.println("Jo created! " + this.toString());
	}
	
	private void initAudio(){
		//this.audioHandler.loadClip(oof);
        System.out.println("initAudio for " + name + audioHandler.toString());
    }

    public void draw (Graphics2D g2) {
		g2.setColor (Color.BLUE);
		g2.fill (new Rectangle2D.Double (x, y, width, height));
    }
	
}