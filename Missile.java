

public class Missile extends Sprite
{
    //private final int BOARD_WIDTH = 390;
    private Environment environment;
    private int MISSILE_SPEED = 5;
    private int boardWidth;

    public Missile(Environment e){
        super();
        width = 20;
        height = 20;
        environment = e;
        boardWidth = e.width;
        //initMissile();
        System.out.println("missile created!");
    }

    public int getSpeed(){return this.MISSILE_SPEED;}
    public void setSpeed(int speed){this.MISSILE_SPEED = speed;}

    public void initMissile(){
        //loadImage("images/missile.png");
        //getImageDimensions();
    }

    public void move(){
        x += MISSILE_SPEED;
        if(x > boardWidth){
            visible = true;
        }
    }
}