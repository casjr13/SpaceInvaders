import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The Stats class extends JPanel, and holds three panels containing text of
 * all the game statistics.
 */
public class Stats extends JPanel {
    /* The class variables instantiate three panels and the font. */
    public static final Font FONT = InvadersGameFrame.FONT;
    public static LivesPanel lives = new LivesPanel();
    public static AlienPanel alien = new AlienPanel();
    public static ScorePanel score = new ScorePanel();


    /**
     * The constructor adds each panel to their place within the Stats panel.
     */
    public Stats(){
        super(new BorderLayout());
        add(lives, BorderLayout.LINE_START);
        add(alien, BorderLayout.CENTER);
        add(score, BorderLayout.LINE_END);
    } // end constructor


    /**
     * The updateInfo method calls each update method within the three panels.
     * 
     * @param int numLives - An int representing the number of lives to lose.
     * @param int numAlien - An int representing the number of new aliens killed
     * @param int numScore - An int representing the amount of points to add.
     */
    public static void updateInfo(int numLives, int numAlien, int numScore) {
        lives.update(numLives);
        alien.update(numAlien);
        score.update(numScore);
    } // end updateInfo method




    /**
     * The Lives class is nested within the Stats class. This class extends 
     * JPanel, and contains the text that displays the number of lives left.
     */
    public static class LivesPanel extends JPanel {
        /* Class Variables */
        public static int lives = 3;
        private static JLabel strLives = new JLabel("Lives:", SwingConstants.CENTER);
        private static JLabel numLives = new JLabel(""+lives, SwingConstants.CENTER);


        /**
         * The constructor sets the background color, and adds the labels to
         * the panel. Each label's font is set along with the foreground color.
         */
        public LivesPanel() {
            super();
            setBackground(Color.BLACK);
            add(strLives);
            add(numLives);
            
            strLives.setFont(FONT);
            strLives.setForeground(Color.WHITE);

            numLives.setFont(FONT);
            numLives.setForeground(Color.GREEN);
        } // end constructor

        
        /**
         * The update method subtracts the number of lives specified and
         * checks to see if the game is over. It then changes the text within
         * the label.
         *
         * @param int live - An int representing the number of lives to lose.
         */
        public static void update(int live) {
            lives -= live;
            if(lives <= 0) {
                GameBoard.game = false;
                GameBoard.gameOver = true;
            }
            numLives.setText(""+lives);
        } // end update method
    } // end Lives class




    /**
     * The Aliens class is nested within the Stats class. This class extends 
     * JPanel, and contains the text that displays the number of aliens killed.
     */
    public static class AlienPanel extends JPanel {
        /* Class Variables */
        public static int alien = 0;
        private static JLabel strAlien = new JLabel("Aliens killed:", SwingConstants.CENTER);
        private static JLabel numAlien = new JLabel(""+alien, SwingConstants.CENTER);


        /**
         * The constructor sets the background color, and adds the labels to
         * the panel. Each label's font is set along with the foreground color.
         */
        public AlienPanel() {
            super();
            setBackground(Color.BLACK);
            add(strAlien);
            add(numAlien);
            
            strAlien.setFont(FONT);
            strAlien.setForeground(Color.WHITE);

            numAlien.setFont(FONT);
            numAlien.setForeground(Color.GREEN);
        } // end constructor


        /**
         * The update method adds the number of aliens killed specified changes
         * the text within the label.
         *
         * @param int killed - An int representing the number of aliens killed
         *                     to add.
         */
        public static void update(int killed) {
            alien += killed;
            numAlien.setText(""+alien);
        } // end update method
    } // end Alien class




    /**
     * The Score class is nested within the Stats class. This class extends 
     * JPanel, and contains the text that displays the score.
     */
    public static class ScorePanel extends JPanel {
        /* Class Variables */
        public static int score = 0;
        private static JLabel strScore = new JLabel("Score:", SwingConstants.CENTER);
        private static JLabel numScore = new JLabel(""+score, SwingConstants.CENTER);


        /**
         * The constructor sets the background color, and adds the labels to
         * the panel. Each label's font is set along with the foreground color.
         */
        public ScorePanel() {
            super();
            setBackground(Color.BLACK);
            add(strScore);
            add(numScore);
            
            strScore.setFont(FONT);
            strScore.setForeground(Color.WHITE);

            numScore.setFont(FONT);
            numScore.setForeground(Color.GREEN);
        } // end constructor


        /**
         * The update method adds the amount of points specified to add, then
         * changes the text within the label.
         *
         * @param int scores - An int representing the amount of points to add.
         */
        public static void update(int scores) {
            score += scores;
            numScore.setText(""+score);
        } // end update method
    } // end Score class
} // end Stats class