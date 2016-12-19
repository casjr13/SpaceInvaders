import java.util.*;

/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The missile class extends GameObject. This class can be instantiated to make
 * a missile object.
 */
public class Missile extends GameObject {
    /**
     * This constructor creates a GameObject object with missile attributes.
     */
    public Missile(int x, int y, int width, int height) {
        super(x, y, width, height);
    } // end constructor


    /**
     * This overrides the toString method.
     *
     * @return str - A string representation of the missile object.
     */
    public String toString() {
        String str = "Missile at ("+x+", "+y+")";
        return str;
    } // end toString method


    /**
     * The update method adds MISSILE_SPEED to the y variable of the missile.
     * If the missile is now out of bounds or it intersects any of the three
     * shields, the missile is removed from the list. If it does hit a shield,
     * then the shield is damaged.
     */
    public void update() {
        y += MISSILE_SPEED;
        List<Shield> sh = new ArrayList<>(GameBoard.shields);
        for(Shield s: sh) {
            if(y<0 || intersects(s)) {
                GameBoard.missiles.remove(this);
                if(intersects(s)) {
                    s.damage();
                }
            }
        }
    } // end update method
} // end Missile class