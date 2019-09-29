import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
/*
    each folder in clips should have its own clip handler
    this class will keep track of the path, and store all the clips in a hashmap
    also, any other utility functions needed for clips.

    usage eg (Environment class): 
    AudioHandler audioHandler = new AudioHandler(environment);
    bg = "background.png";
    audioHandler.loadClip(bg);
    g2.drawClip(audioHandler.getClip(bg));
*/
public class AudioHandler
{  
    private String path = "Assets/sounds/";
    private String folderName;

    private LinkedHashMap<String, AudioClip> clips;
    //AudioClip playSound = null;

    public AudioHandler(String folderName){
        //opened = false;
        this.folderName = folderName; 
        this.path = this.path + folderName + "/";
        this.clips = new LinkedHashMap<String, AudioClip>(); // does this work?
        System.out.println("AudioHandler created~!");
    }
    
    public AudioHandler(){
        //opened = false;
        //this.folderName = folderName; 
        //this.path += "/";
        this.clips = new LinkedHashMap<String, AudioClip>(); // does this work?
        System.out.println("AudioHandler created~!");
    }

    public String getPath(){ return this.path;}
    public void setPath(String path){ this.path = path;}

    public void loadClip(String fileName) {
		try {
            AudioClip value = Applet.newAudioClip (
                getClass().getResource(path+fileName)
            );
			this.clips.put(fileName, value);

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}
	}

	public void playClip (String fileName) {
        try {
            this.clips.get(fileName).play();
            System.out.println("played clip: " + fileName);
        } catch (Exception e) {
            System.out.println ("Error playing sound file: " + e);
        }
	}
    
    public AudioClip getClip(String fileName){return this.clips.get(fileName);}

    public String toString(){
        String list = "list";
        list = this.clips.toString();
        String s = "folderName = " + this.folderName
            + "\npath = " + this.path
            + "\nclips = " + list + "\n";
        return s;
    }

    public static void main(String[] args) {
        AudioHandler a = new AudioHandler(); 

        // String fileName2 = "hitWall.au";
        // a.loadClip(fileName2);
        // a.playClip(fileName2);

        String fileName = "fallOff.au";
        a.loadClip(fileName);
        a.playClip(fileName);

        // String fileName1 = "fallOff.au";
        // a.loadClip(fileName1);
        // a.playClip(fileName1);

        System.out.println(a.toString());
    }

}