import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The Ship class extends GameObject and implements the Shooter interface. This
 * class can be instantiated to make a ship. The ship can fire lasers using the
 * fire method.
 */
public class Ship extends GameObject implements Shooter {
    /* Class variables */
    private BufferedImage ship;
    public static int xDir = 5;

    /**
     * This constructor creates a GameObject object with ship attributes. It
     * instantiates a BufferedImage.
     * 
     * @param int x
     * @param int y
     * @param int width
     * @param int height
     */
    public Ship(int x, int y, int width, int height) {
        super(x, y, width, height);
        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream("Ship.png");
        try {
            ship = ImageIO.read(in);
        } catch (IOException ex) {
            System.out.println("ERROR: Cannot find Ship.png!");
        }
    } // end constructor


    /**
     * Create a new "bullet" and return it.
     *
     * @return Laser object.
     */
    public Laser fire() {
        return new Laser((x+(width/2)-(LASER_WIDTH/2)),
            (y-(LASER_HEIGHT)), LASER_WIDTH, LASER_HEIGHT);
    } // end fire method


    /**
     * This overrides the toString method.
     *
     * @return str - A string representation of the ship object.
     */
    public String toString() {
        String str = "Ship at ("+x+", "+y+")";
        return str;
    } // end toString method


    /**
     * The draw method returns the BufferedImage of the ship.
     *
     * @return BufferedImage ship
     */
    public BufferedImage draw() {
        return ship;
    } // end draw method


    /**
     * The left method subtracts xDir from x if the position is within the game
     * board.
     */
    public void left() {
        int p = x-xDir;
        if(p>0 && p<(GAME_BOARD_WIDTH-30)) {
            x -= xDir;
        }
    } // end left method


    /**
     * The right method adds xDir to x if the position is within the game
     * board.
     */
    public void right() {
        int p = x+xDir;
        if(p>0 && p<(GAME_BOARD_WIDTH-30)) {
            x += xDir;
        }
    } // end right method
} // end Ship class