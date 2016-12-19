import javax.swing.*;


/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * The SpaceInvaders class contains the main method that instantiates and runs
 * the space invaders game.
 */
public class SpaceInvaders {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable () {
            public void run() {
                JFrame frame = new InvadersGameFrame();
                frame.setVisible(true);
            } // end run method
        });
    } // end main method
} // end SpaceInvaders class