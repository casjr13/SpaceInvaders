import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The Alien class extends GameObject and implements the Shooter interface. 
 * This class can be instantiated to make an alien. The alien can fire missiles
 * using the fire method. This class consists of methods 
 */
public class Alien extends GameObject implements Shooter {
    /* Images for aliens and two int variables for changes in position. */
    public static BufferedImage down;
    public static BufferedImage up;
    public static int xDir = ALIEN_SPEED;
    public static int yDir = ALIEN_SPEED;

    /* Boolean for checking which way the alien's arms are facing. */
    public static boolean state = true;


    /**
     * This constructor creates a GameObject object with alien attributes. It
     * instantiates two buffered images.
     *
     * @param int x
     * @param int y
     * @param int width
     * @param int height
     */
    public Alien(int x, int y, int width, int height) {
        super(x, y, width, height);
        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream("Alien Down.png");
        InputStream in2 = cl.getResourceAsStream("Alien Up.png");
        try {
            down = ImageIO.read(in);
            up = ImageIO.read(in2);
        } catch (IOException ex) {
            System.out.println("ERROR: Cannot find an image for Alien!");
        }
    } // end constructor


    /**
     * Create a new "bullet" and return it.
     *
     * @return Missile object.
     */
    public Missile fire() {
        Missile missile = new Missile((x+(width/2)-(MISSILE_WIDTH/2)),
            (y+height), MISSILE_WIDTH, MISSILE_HEIGHT);
        return missile;
    } // end fire method


    /**
     * This overrides the toString method.
     *
     * @return str - A string representation of the alien object.
     */
    public String toString() {
        String str = "Alien at ("+x+", "+y+")";
        return str;
    } // end toString method


    /**
     * The draw method returns the correct buffered image according to the
     * state of the image (whether the alien's arms should be facing up or
     * down).
     *
     * @return BufferedImage
     */
    public BufferedImage draw(){
        BufferedImage a;
        if(state) {
            a = down;
        } else {
            a = up;
        }
        return a;
    } // end draw method


    /**
     * The checkPosition method returns true if the alien is at the edge of the
     * game board. If the alien is at the bottom of the alien space (90px
     * above the bottom of the game board), the game is over.
     *
     * @return boolean
     */
    public boolean checkPosition() {
        boolean change = false;
        int p = x+xDir;
        if(p<=0 || p>=(GAME_BOARD_WIDTH-30)) {
            xDir = -xDir;
            change = true;
        }
        int gameOver = y+yDir+ALIEN_HEIGHT;
        if(gameOver>GAME_BOARD_HEIGHT-90) {
            GameBoard.game = false;
            GameBoard.gameOver = true;
        }
        return change;
    } // end checkPosition


    /**
     * The updateX method adds xDir to the variable, x.
     */
    public void updateX() {
        x += xDir;
    } // end updateX method


    /**
     * The updateY method adds yDir to the variable, y.
     */
    public void updateY() {
        y += yDir;
    } // end updateY method
} // end Alien class