import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Environment extends Sprite
{
    private ImageHandler imageHandler;
    //private AudioHandler audioHandler;
    private String folderName;
    private int ground;
    public Environment(){
        name = "environment";
        folderName = name; // accessible through imagehandler class
        width = 1280;
        height = 720;
        ground = 500;
        imageHandler = new ImageHandler(folderName);
        initImages();
    }

    private void initImages(){
        String bg = "background.png";
        this.imageHandler.loadImage(bg);

        //System.out.println("initImages for Environment:" + imageHandler.toString());
    }

    public int getGround(){return this.ground;}
    public void setGround(int y){this.ground = y;}

    public String toString(){
        String s = "Environment:\n"
            + super.toString()
            + "\nGround (y) = " + this.ground
            + "\nname = folderName = " + name
            + "\nimages: " + imageHandler.toString();
        return s;
    }


    //main for testing
    public static void main(String[] args) {
        Environment e = new Environment();
        System.out.println(e.toString());
        System.out.println("end e test");
    }
}