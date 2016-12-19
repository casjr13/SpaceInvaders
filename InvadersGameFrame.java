import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The InvadersGameFrame class extends JFrame and is the main window that holds
 * all of the JPanels that show the game. This class implements GameData for
 * the constant variables and it also implements ActionListener to listen for
 * timer "ticks".
 */
public class InvadersGameFrame extends JFrame implements GameData, ActionListener {
    /* Initialize all of the panels */
    public JPanel panel;
    public JPanel stats;
    public JPanel board;
    public JPanel button;
    /* Initialize all of the timers */
    public static Timer timer;
    public static Timer timer2;
    public static Timer2 alienTime;
    public static int mills = 600;
    /* Initialize Game Logic Controller */
    public static GameLogic game = new GameLogic();
    /* Set up font */
    public static Font FONT = new Font("Courier New", Font.BOLD, 14);

    /**
     * This constructor creates the JFrame with the title of the game. The
     * font is set to an LCD font, panels are constructed, panels are added to
     * to the main Panel in their respective places. The two timers are
     * instantiated, but not started. The default close operation is set to
     * exit on close, setting the window to not allow resizing, setting the 
     * JFrames icon, setting the main panel to the content pane, and finally
     * packing everything together. The position is then set to be in the
     * center of the screen.
     */
    public InvadersGameFrame() {
        super("Space Invaders");

        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream("LCD.ttf");
        try {
            try {
                FONT = FONT.createFont(Font.TRUETYPE_FONT, in);
            } catch (FontFormatException ex){
                ex.printStackTrace();
            }
            FONT = FONT.deriveFont(Font.BOLD, 12);
        } catch (IOException ex) {
            System.out.println("ERROR: Cannot find font!");
        }

        panel = new JPanel(new BorderLayout());
        stats = new Stats();
        board = new GameBoard();
        button = new Button();

        panel.add(stats, BorderLayout.PAGE_START);
        panel.add(board, BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);
        
        timer = new Timer(35, this);
        timer.setInitialDelay(0);
        timer.stop();
        alienTime = new Timer2();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Alien.down);
        setContentPane(panel);
        pack();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int)(dim.getWidth()/2)-(getWidth()/2),
            (int)(dim.getHeight()/2)-(getHeight()/2));
    } // end constructor


    /**
     * The actionPerformed method calls the methods in charge of the game logic
     * if the game is currently being played and repaints the screen.
     * 
     * @param ActionEvent e
     */
    public void actionPerformed(ActionEvent e) {
        if(GameBoard.game) {
            game.updateMissiles();
            game.updateLasers();
            game.updateShip();
            game.checkProjectiles();
            game.checkShipDeath();
            game.checkAlienDeath();
        }
        repaint();
    } // end actionPerformed method


    /**
     * The play method starts the timer for timer one (for game play such as
     * ship, lasers, checking for intersections, etc...) and the second timer
     * (for alien movements and missile movements).
     */
    public static void play() {
        timer.start();
        alienTime.play();
    } // end play method
    

    /**
     * The pause method stops the timer for timer one (for game play such as
     * ship, lasers, checking for intersections, etc...) and the second timer
     * (for alien movements and missile movements).
     */
    public static void pause() {
        timer.stop();
        alienTime.pause();
    } // end pause method




    /**
     * The Timer2 class implements ActionListener. This class is responsible
     * for updating the aliens and missiles movements.
     */
    public class Timer2 implements ActionListener {
        /* Class Variables */
        private int even = 0;

        /**
         * This constructor creates the timer.
         */
        public Timer2() {
            timer2 = new Timer(mills, this);
            timer2.setInitialDelay(0);
            timer2.stop();
        } // end constructor


        /**
         * The actionPerformed method calls the updateAliens method and
         * shootMissile method for the GameLogic object if the game is
         * currently being played and repaints the screen.
         * 
         * @param ActionEvent e
         */
        public void actionPerformed(ActionEvent e) {
            Alien.state = !Alien.state;
            if(GameBoard.game) {
                game.updateAliens();
                if(even%2 == 0) {
                    game.shootMissile();
                }
                even++;
            }
            repaint();
        } // end actionPerformed method

        
        /**
         * The play method starts the timer
         */
        public void play() {
            timer2.start();
        } // end play method
    
        
        /**
         * The pause method stops the timer
         */
        public void pause() {
            timer2.stop();
        } // end pause method
    } // end Timer2 class
} // end InvadersGameFrame class