import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;


/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The GameBoard class extends JPanel and implements GameData and KeyListener.
 * This class is the area that will hold the game objects and respond to keys
 * being pressed.
 */
public class GameBoard extends JPanel implements GameData, KeyListener {
    /* Create an ArrayList of Aliens and position of first Alien */
    public static java.util.List<Alien> aliens = new ArrayList<>();
    private int alienX = 10;
    private int alienY = 10;

    /* Create an ArrayList of Lasers and Missiles */
    public static java.util.List<Laser> lasers = new ArrayList<>();
    public static java.util.List<Missile> missiles = new ArrayList<>();
    
    /* Create the Ship */
    public static Ship ship = new Ship(SHIP_X, GAME_BOARD_HEIGHT-23, SHIP_WIDTH,
        SHIP_HEIGHT);

    /* Create a list of shields */
    public static java.util.List<Shield> shields = new ArrayList<>();
    public static Shield shield1 = new Shield(125,
        (GAME_BOARD_HEIGHT-80-(SHIELD_HEIGHT/2)), SHIELD_WIDTH, SHIELD_HEIGHT);
    public static Shield shield2 = new Shield(275,
        (GAME_BOARD_HEIGHT-80-(SHIELD_HEIGHT/2)), SHIELD_WIDTH, SHIELD_HEIGHT);
    public static Shield shield3 = new Shield(425,
        (GAME_BOARD_HEIGHT-80-(SHIELD_HEIGHT/2)), SHIELD_WIDTH, SHIELD_HEIGHT);
    
    /* Instantiate three booleans for keeping track of game states */
    public static boolean game = false; // Is the game going on?
    public static boolean gameOver = false; // Is the game over?
    public static boolean gameWon = false; // Has the user won?
    public static boolean begin = true; // Is it the beginning of the game?
    
    /* Create BufferedImages and String values of the image names */
    private BufferedImage stars;
    private BufferedImage over;
    private BufferedImage wonDown;
    private BufferedImage wonUp;
    private BufferedImage start;
    private String imageName = "Stars.jpg";
    private String overName = "Game Over.png";
    private String wonDownName = "Won-Down.png";
    private String wonUpName = "Won-Up.png";
    private String startName = "Start.png";

    /* Key Listening Constants */
    public static boolean left = false;
    public static boolean right = false;

    /**
     * The constructor uses a ClassLoader and InputStreams to load images from
     * directory and uses a try-catch clause to catch possible exceptions. This
     * constructor also sets the background color, preferred size, and adds a
     * KeyListener. It also adds aliens to the ArrayList at certain position.
     */
    public GameBoard(){
        super();

        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream(imageName);
        InputStream o = cl.getResourceAsStream(overName);
        InputStream wd = cl.getResourceAsStream(wonDownName);
        InputStream wu = cl.getResourceAsStream(wonUpName);
        InputStream s = cl.getResourceAsStream(startName);
        try {
            stars = ImageIO.read(in);
            over = ImageIO.read(o);
            wonDown = ImageIO.read(wd);
            wonUp = ImageIO.read(wu);
            start = ImageIO.read(s);
        } catch (IOException ex) {
            System.out.println("ERROR: Cannot find an image!");
        }
        
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT));
        addKeyListener(this);

        for(int x=0; x<COL; x++) {
            for(int y=0; y<ROW; y++) {
                Alien a = new Alien(alienX, alienY, ALIEN_WIDTH, ALIEN_HEIGHT);
                aliens.add(a);
                alienY += ALIEN_HEIGHT+10;
            }
            alienY = 10;
            alienX += ALIEN_WIDTH+10;
        }

        shields.add(shield1);
        shields.add(shield2);
        shields.add(shield3);
    } // end constructor


    /**
     * The paintComponent method overrides the paintComponent method in the
     * JComponent class.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Draw the Background Image, "Stars.jpg," first. */
        g.drawImage(stars, 0, 0, getWidth(), getHeight(), null);

        
        if(game) {
            // Draw ship with its attributes.
            g.drawImage(ship.draw(), ship.getX(), ship.getY(), ship.getWidth(),
                ship.getHeight(), null);
            
            // Draw each Alien in the list.
            for(Alien a: aliens) {
                g.drawImage(a.draw(), a.getX(), a.getY(), a.getWidth(),
                    a.getHeight(), null);
            }
        
            // Set color to Red and draw each laser in the list.
            g.setColor(Color.RED);
            for(Laser l: lasers) {
                g.fillRect(l.getX(), l.getY(), l.getWidth(), l.getHeight());
            }
        
            // Set color to Gray and draw each Missile in the list.
            g.setColor(Color.LIGHT_GRAY);
            for(Missile m: missiles) {
                g.fillRect(m.getX(), m.getY(), m.getWidth(), m.getHeight());
            }

            // Draw green shield and black text within it.
            for(Shield s: shields) {
                g.setColor(Color.GREEN);
                g.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
                g.setColor(Color.BLACK);
                g.drawString(""+s.getHealth(), s.getX()+18, s.getY()+12);
            }

            requestFocusInWindow(); //Set focus to this panel.
        } else {
            // Since game is not going on, change button's text to this.
            Button.button.setText("Click Here To Start New Game!");

            if(begin) { // Draw image saying "Press Start" and setText to Start
                Button.button.setText("Start");
                g.drawImage(start, (getWidth()/2)-135, (getHeight()/2)-100,
                    271, 200, null);
            } else if(gameOver) { // Draw image saying "Game Over"
                g.drawImage(over, (getWidth()/2)-128, (getHeight()/2)-76,
                    257, 152, null);
            } else if(gameWon) {
                // Draw image saying "You won" with animated alien.
                BufferedImage c;
                if(Alien.state) {
                    c = wonDown;
                } else {
                    c = wonUp;
                }
                g.drawImage(c, (getWidth()/2)-200, 0,
                    400, 400, null);
            }
        }
    } // end paintComponent method


    /**
     * The keyPressed method checks to see if the left key, right key, or
     * space bar was pressed and has the ship react if the game is unpaused.
     * Pressing 'c' will clear lasers, and pressing 'm' will clear missiles.
     *
     * @param Keyevent e
     */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT && !Button.start) {
            left = true;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !Button.start) {
            right = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !Button.start) {
            if(lasers.size()==0) {
                lasers.add(ship.fire());
            }
        }
        if(e.getKeyChar() == 'c' && !Button.start) {
            lasers.clear();
        }
        if(e.getKeyChar() == 'm' && !Button.start) {
            missiles.clear();
        }
    } // end keyPressed method


    /**
     * The keyReleased method sets this to be focusable.
     *
     * @param Keyevent e
     */
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT && !Button.start) {
            left = false;
        } else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !Button.start) {
            right = false;
        }
    } // end keyReleased method


    /**
     * The keyTyped method sets this to be focusable.
     *
     * @param Keyevent e
     */
    public void keyTyped(KeyEvent e) {
        setFocusable(true);
    } // end keyTyped method
} // end GameBoard class