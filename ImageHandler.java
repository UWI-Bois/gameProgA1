import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
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
/*
    each folder in images should have its own image handler
    this class will keep track of the path, and store all the images in a hashmap
    also, any other utility functions needed for images.

    usage eg (Environment class): 
    ImageHandler imageHandler = new ImageHandler(environment);
    bg = "background.png";
    imageHandler.loadImage(bg);
    g2.drawImage(imageHandler.getImage(bg));
*/
public class ImageHandler
{  
    private String path = "Assets/images/";
    private String folderName;
    private LinkedHashMap<String, Image> images;

    public int width;
    public int height;

    public ImageHandler(String folderName){
        this.folderName = folderName; 
        this.path = this.path + folderName + "/";
        this.images = new LinkedHashMap<String, Image>(); // does this work?
        //System.out.println("ImageHandler created~!");
    }

    public String getPath(){ return this.path;}
    public void setPath(String path){ this.path = path;}

    public void loadImage(String imageName) {
		ImageIcon ii = null;
		Image i = null;
		try {
			ii = new ImageIcon(path + imageName);
            i = ii.getImage();
            this.images.put(imageName, i);
			//System.out.println("Loaded Image!: " + imageName);
			//System.out.println("image map for " + folderName + ": " + this.images.toString());
		} catch (Exception e) {
			//System.out.println("error loading image: " + imageName);
			//System.out.println(e.getMessage());
		}
		return;
    }

    public void getImageDimensions(String imageName){
        // width
        // height
        Image image = this.getImage(imageName);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }
    
    public Image getImage(String imageName){return this.images.get(imageName);}

    public String toString(){
        String list = "list";
        list = this.images.toString();
        String s = "folderName = " + this.folderName
            + "\npath = " + this.path
            + "\nimages = " + list + "\n";
        return s;
    }

}