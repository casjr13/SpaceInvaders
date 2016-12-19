import java.util.*;

/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The Laser class extends GameObject. This class can be instantiated to make a
 * laser object.
 */
public class Laser extends GameObject {
    /**
     * This constructor creates a GameObject object with laser attributes.
     */
    public Laser(int x, int y, int width, int height) {
        super(x, y, width, height);
    } // end constructor


    /**
     * This overrides the toString method.
     *
     * @return str - A string representation of the laser object.
     */
    public String toString() {
        String str = "Laser at ("+x+", "+y+")";
        return str;
    } // end toString method

    
    /**
     * The update method subtracts LASER_SPEED to the y variable of the laser.
     * If the laser is now out of bounds or it intersects any of the shields,
     * the laser is removed from the list. If it does hit a shield, then the
     * shield is damaged.
     */
    public void update() {
        y -= LASER_SPEED;
        List<Shield> sh = new ArrayList<>(GameBoard.shields);
        if(y<0) {
            GameBoard.lasers.remove(this);
        }
        for(Shield s: sh) {
            if(intersects(s)) {
                GameBoard.lasers.remove(this);
                s.damage();
            }
        }
    } // end update method
} // end Laser class