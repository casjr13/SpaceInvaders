import java.awt.*;

/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The GameObject class is an abstract class that implements the Object2D and
 * GameData interfaces. This class contains the basic functions of a game
 * object.
 */
public abstract class GameObject implements Object2D, GameData {
    /** Class Variables */
    Rectangle gameBoard = new Rectangle(0, 0, GAME_BOARD_WIDTH,
        GAME_BOARD_HEIGHT);
    Rectangle rect;
    int x;
    int y;
    int width;
    int height;


    /**
     * This constructor creates the bounding rectangle for the game object, and
     * initializes class fields.
     */
    public GameObject(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    } // end constructor

    /** @return x coordinate of upper left corner of object. */
    public int getX() {
        return x;
    } // end getX method


    /** @return y coordinate of upper left corner of object. */
    public int getY() {
        return y;
    } // end getY method


    /** @return object width. */
    public int getWidth() {
        return width;
    } // end getWidth method


    /** @return object height. */
    public int getHeight() {
        return height;
    } // end getHeight method


    /**
     * Get the bounding rectangle for the object.
     *
     * @return Bounding rectangle.
     */
    public Rectangle getBoundingRectangle() {
        rect = new Rectangle(x, y, width, height);
        return rect;
    } // end getBoundingRectangle method


    /**
     * Does this object intersect another?
     * 
     * @param other The other object to check.
     * @return True if objects intersect.
     */
    public boolean intersects(Object2D other) {
        rect = new Rectangle(x, y, width, height);
        return rect.intersects(other.getBoundingRectangle());
    } // end intersects method


    /** 
     * Is any part of the object outside of the game board?
     *
     * @return True if part of object is out of bounds.
     */
    public boolean isOutOfBounds() {
        boolean outBounds = false;
        rect = new Rectangle(x, y, width, height);
        if(!gameBoard.contains(rect)) {
            outBounds = true;
        }
        return outBounds;
    } // end isOutOfBounds method
} // end GameObject class