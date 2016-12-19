/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The Shield class extends GameObject. This class can be instantiated to make a
 * Shield object.
 */
public class Shield extends GameObject {
    /* Class Variable */
    public int health = 15;

    /**
     * This constructor creates a GameObject object with Shield attributes.
     */
    public Shield(int x, int y, int width, int height) {
        super(x, y, width, height);
    } // end constructor


    /**
     * This overrides the toString method.
     *
     * @return str - A string representation of the Shield object.
     */
    public String toString() {
        String str = "Shield at ("+x+", "+y+")";
        return str;
    } // end toString method


    /**
     * The getHealth method returns the health variable.
     *
     * @return health
     */
    public int getHealth() {
        return health;
    } // end health method


    /**
     * The setHealth sets the change paramater to the health variable.
     *
     * @param change
     */
    public void setHealth(int change) {
        health = change;
    } // end setHealth method


    /**
     * The damage method subtracts one health and if the health is less than or
     * equal to zero, then the Shield is removed.
     */
    public void damage() {
        health--;
        if(health<=0) {
            GameBoard.shields.remove(this);
        }
    } // end damage methods
} // end Laser class