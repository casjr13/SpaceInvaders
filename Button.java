import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The Button class extends JPanel and implements ActionListener. This class
 * holds two panels and a button. There is a panel consisting of a JLabel that
 * says "Space Invaders" on the left; A panel consisting of a JLabel that
 * displays the level; and a button in the middle. The button starts the game
 * when the game is not going on, and pause/resume when the game is going on.
 */
public class Button extends JPanel implements ActionListener {
    /* Create a universal font and JButton. */
    public static final Font FONT = InvadersGameFrame.FONT;
    public static JButton button = new JButton("Resume");
    
    /* Create JPanel and JLabels to display level */
    public static JPanel level = new JPanel();
    public static JLabel strLevel = new JLabel("Level: ");
    public static JLabel numLevel = new JLabel(""+GameLogic.level);
    
    /* Create JPanel and JLabels to display "Space Invaders" */
    public static JPanel spaceInvaders = new JPanel();
    public static JLabel spaceInvadersL = new JLabel("Space Invaders");
    
    /* Should the button say start/resume or pause */
    public static boolean start = true;


    /**
     * The constructor constructs the panel to add all of the components in it.
     */
    public Button(){
        super(new BorderLayout());

        /* add componenets to JPanels */
        level.add(strLevel);
        level.add(numLevel);
        spaceInvaders.add(spaceInvadersL);

        /* add JPanels and JButton to the main panel. Set background color. */
        add(spaceInvaders, BorderLayout.LINE_START);
        add(button, BorderLayout.CENTER);
        add(level, BorderLayout.LINE_END);
        setBackground(Color.BLACK);

        /* set button font, background color, foreground color, and border. */
        button.setFont(FONT);
        button.setBackground(Color.GREEN);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder());

        /* Construct JPanel with appropriate colors, fonts, and size. */
        level.setBackground(Color.BLACK);
        level.setPreferredSize(new Dimension(150, 25));
        strLevel.setFont(FONT);
        strLevel.setBackground(Color.BLACK);
        strLevel.setForeground(Color.WHITE);
        numLevel.setFont(FONT);
        numLevel.setBackground(Color.BLACK);
        numLevel.setForeground(Color.GREEN);

        /* Construct JPanel with appropriate colors, fonts, and size. */
        spaceInvaders.setBackground(Color.BLACK);
        spaceInvaders.setPreferredSize(new Dimension(150, 25));
        spaceInvadersL.setFont(FONT);
        spaceInvadersL.setBackground(Color.BLACK);
        spaceInvadersL.setForeground(Color.ORANGE);

        /* Remove focus painted (outline of text on button) and add the
           ActionListener to the button. */
        button.setFocusPainted(false);
        button.addActionListener(this);
    } // end constructor


    /**
     * The actionPerformed method specifies what to do when the button is
     * clicked. If the current text is "Start", then it changes to "Pause" and
     * changes the colors of the button. Or vice-versa.
     * 
     * @param ActionEvent e
     */
    public void actionPerformed(ActionEvent e) {
        /* Since button says start/resume: change to pause, play timer, set
           background to Black, set foreground to Green, tell the program that
           the game is going on, it is no longer at the begin screen, and
           change start. */
        if(start) {
            InvadersGameFrame.play();
            button.setText("Pause");
            button.setBackground(Color.BLACK);
            button.setForeground(Color.GREEN);
            GameBoard.game = true;
            GameBoard.begin = false;
            start = !start;
        } else {
            /* Since button says pause: change to resume and pause timer (if
               game is going on). Or it will change to start (if game is not
               going on), set background to Dark Grey, set foreground to Green,
               and if the game is over or the game is won, then the stats and
               game are reset. */
            if(GameBoard.game) {
                button.setText("Resume");
                InvadersGameFrame.pause();
            } else {
                button.setText("Start");
                if(GameBoard.gameOver || GameBoard.gameWon) {
                    Stats.LivesPanel.lives = 3;
                    Stats.ScorePanel.score = 0;
                    Stats.AlienPanel.alien = 0;
                    // this is to call setText implicitly
                    Stats.updateInfo(0,0,0);
                    InvadersGameFrame.game.restart();
                    numLevel.setText(""+GameLogic.level);
                }
            }
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.GREEN);
            start = !start;
        }
    } // end actionPerformed method
} // end Button class