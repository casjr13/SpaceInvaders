import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Christopher Salinas Jr
 * CS 251L 003
 * 
 * The GameLogic class hold methods for changing the position of missiles,
 * lasers, and aliens. It checks to see if there are intersections between the
 * following: lasers v. aliens; missiles v. ship; and lasers v. missiles. It is
 * also in charge of changing levels and reseting the game when the game is over.
 */
public class GameLogic implements GameData {
    /* Class variable for the level */
    public static int level = 1;


    /**
     * The shootMissile method creates a temporary list of aliens in reverse
     * order to iterate over. This allows the bottom-most alien to check if it
     * is within the width of the ship. Therefore, the alien will shoot a
     * missile if it has the ship in its line of fire.
     */
    public void shootMissile() {
        java.util.List<Alien> temp = new ArrayList<>(GameBoard.aliens);
        Collections.reverse(temp);
        int shipX = GameBoard.ship.getX();
        int shipX2 = shipX+GameBoard.ship.getWidth();
        for(Alien a: temp) {
            int alienMid = a.getX()+(a.getWidth()/2);
            if(alienMid>shipX && alienMid<shipX2) {
                GameBoard.missiles.add(a.fire());
                break;
            }
        }
    } // end shootMissile method
    

    /**
     * The updateAliens method checks the position of the alien, and if it is
     * at the edge of the screen, all aliens will move downward one "step".
     * Then the aliens move horizontally one step.
     */
    public void updateAliens() {
        boolean change = true;
        for(Alien a: GameBoard.aliens) {
            if(a.checkPosition()) {
                for(Alien s: GameBoard.aliens) {
                    s.updateY();
                }
                change = false;
                break;
            }
        }
        for(Alien a: GameBoard.aliens) {
            if(change) {
                a.updateX();
            }
        }
    } // end updateAliens method


    /**
     * The updateShip method moves the ship according to keys being pressed.
     */
    public void updateShip() {
        if(GameBoard.left) {
            GameBoard.ship.left();
        } else if(GameBoard.right) {
            GameBoard.ship.right();
        }
    } // end updateAliens method


    /**
     * The updateMissiles method creates a temporary list of missiles to
     * iterate over. The missiles' position is then updated.
     */
    public void updateMissiles() {
        java.util.List<Missile> temp = new ArrayList<>(GameBoard.missiles);
        for(Missile m: temp) {
            m.update();
        }
    } // end updateMissiles method


    /**
     * The updateLasers method creates a temporary list of lasers to
     * iterate over. The lasers' position is then updated.
     */
    public void updateLasers() {
        java.util.List<Laser> temp = new ArrayList<>(GameBoard.lasers);
        for(Laser l: temp) {
            l.update();
        }
    } // end updateLasers method


    /**
     * The checkProjectiles method creates two temporary lists, one for the
     * lasers and one for the missiles. There is a double for loop to check
     * if there is an intersection between any two laser and missile. If there
     * is an intersection, that missile and the laser is destroyed and 1000
     * points are added to the score.
     */
    public void checkProjectiles() {
        java.util.List<Laser> temp = new ArrayList<>(GameBoard.lasers);
        java.util.List<Missile> temp2 = new ArrayList<>(GameBoard.missiles);

        for(Laser l: temp) {
            for(Missile m: temp2) {
                if(l.intersects(m)) {
                    GameBoard.missiles.remove(m);
                    GameBoard.lasers.clear();
                    Stats.updateInfo(0, 0, 1000);
                }
            }
        }
    } // end checkProjectiles method


    /**
     * The checkShipDeath method creates a temporary list of missiles and
     * checks each missile for an intersection with the ship. If it does, all
     * lasers and missiles are cleared and the ship is reset to the middle of
     * the game board. There is a loss of a life as well.
     */
    public void checkShipDeath() {
        java.util.List<Missile> temp = new ArrayList<>(GameBoard.missiles);
        for(Missile m: temp) {
            if(m.intersects(GameBoard.ship)) {
                Stats.updateInfo(1, 0, 0);
                GameBoard.ship = new Ship(SHIP_X, GAME_BOARD_HEIGHT-23, SHIP_WIDTH,
                    SHIP_HEIGHT);
                GameBoard.lasers.clear();
                GameBoard.missiles.clear();
                break;
            }
        }
    } // end checkShipDeath method


    /**
     * The checkAlienDeath method creates to temporary lists, one for lasers
     * and one for aliens. Each alien removed, it will add 100 points and add 1
     * death to the statistics. If there are no more aliens, advance to the
     * next level.
     */
    public void checkAlienDeath() {
        java.util.List<Laser> temp = new ArrayList<>(GameBoard.lasers);
        java.util.List<Alien> temp2 = new ArrayList<>(GameBoard.aliens);
        int start = GameBoard.aliens.size();

        for(Laser l: temp) {
            for(Alien a: temp2) {
                if(l.intersects(a)) {
                    GameBoard.aliens.remove(a);
                    GameBoard.lasers.remove(l);
                }
            }
        }

        int remaining = GameBoard.aliens.size();
        for(int c = 0; c<(start-remaining); c++) {
            Stats.updateInfo(0, 1, 100);
        }

        if(GameBoard.aliens.size()==0) {
            nextLevel();
        }
    } // end checkAlienDeath method


    /**
     * The nextLevel method adds one to the level, subtracts 50 milliseconds
     * from timer2 (the aliens update interval), and the aliens' direction is
     * reset to the right. If the user's next level is 1-10, then the level
     * label is updated, an extra col is added to the aliens (if an even level),
     * all missiles are cleared, and if the level is odd, a life is
     * added. If the user's next level is 11, then timer2 is reset, game is no
     * longer going on, and the game is won.
     */
    public void nextLevel() {
        level++;
        Alien.xDir = ALIEN_SPEED;
        InvadersGameFrame.mills -= 50;
        InvadersGameFrame.timer2.setDelay(InvadersGameFrame.mills);
        if(level<=10) {
            Button.numLevel.setText(""+level);
            GameBoard.missiles.clear();
            int col = COL+(level/2);
            int row = ROW;
            int alienX = 10;
            int alienY = 10;

            for(int x=0; x<col; x++) {
                for(int y=0; y<row; y++) {
                    Alien a = new Alien(alienX, alienY, ALIEN_WIDTH, ALIEN_HEIGHT);
                    GameBoard.aliens.add(a);
                    alienY += ALIEN_HEIGHT+10;
                }
                alienY = 10;
                alienX += ALIEN_WIDTH+10;
            }

            if(level%2 != 0) {
                Stats.updateInfo(-1, 0, 0);
            }
        } else {
            InvadersGameFrame.mills = 600;
            InvadersGameFrame.timer2.setDelay(InvadersGameFrame.mills);
            int remaining = Stats.LivesPanel.lives;
            for(int c = 0; c<remaining; c++) {
                Stats.updateInfo(0, 0, 10000);
            }
            GameBoard.game = false;
            GameBoard.gameWon = true;
        }   
    } // end nextLevel method


    /**
     * The restart method checks if the game is not going on and it is not at
     * the beginning screen, then the game is set to no winner, the game is not
     * over, and the beginning screen should be the next thing to show up. The
     * level is set back to one, each shield is back to its original state, the
     * aliens are reset to level 1 status and all lasers and missiles are
     * cleared from the screen. In order for the stats to clear at the
     * beginning screen, the screen must be repainted one more time.
     */
    public void restart() {
        if(!GameBoard.game && !GameBoard.begin) {
            if(GameBoard.gameOver || GameBoard.gameWon) {
                GameBoard.gameWon = false;
                GameBoard.gameOver = false;
                GameBoard.begin = true;
                level = 1;
                InvadersGameFrame.mills = 600;
                InvadersGameFrame.timer2.setDelay(InvadersGameFrame.mills);
                GameBoard.shield1 = new Shield(125,
                    (GAME_BOARD_HEIGHT-80-(SHIELD_HEIGHT/2)), SHIELD_WIDTH,
                    SHIELD_HEIGHT);
                GameBoard.shield2 = new Shield(275,
                    (GAME_BOARD_HEIGHT-80-(SHIELD_HEIGHT/2)), SHIELD_WIDTH,
                    SHIELD_HEIGHT);
                GameBoard.shield3 = new Shield(425,
                    (GAME_BOARD_HEIGHT-80-(SHIELD_HEIGHT/2)), SHIELD_WIDTH,
                    SHIELD_HEIGHT);
                GameBoard.shields.clear();
                GameBoard.shields.add(GameBoard.shield1);
                GameBoard.shields.add(GameBoard.shield2);
                GameBoard.shields.add(GameBoard.shield3);
                for(Shield s: GameBoard.shields) {
                    s.setHealth(15);
                }
            }
            GameBoard.aliens.clear();
            GameBoard.lasers.clear();
            GameBoard.missiles.clear();
            GameBoard.ship = new Ship(SHIP_X, GAME_BOARD_HEIGHT-23, SHIP_WIDTH,
                SHIP_HEIGHT);

            int alienX = 10;
            int alienY = 10;
            for(int x=0; x<COL; x++) {
                for(int y=0; y<ROW; y++) {
                    Alien a = new Alien(alienX, alienY, ALIEN_WIDTH, ALIEN_HEIGHT);
                    GameBoard.aliens.add(a);
                    alienY += ALIEN_HEIGHT+10;
                }
                alienY = 10;
                alienX += ALIEN_WIDTH+10;
            }
        }
        int oneSec = 0;
        if(oneSec>=1){
            InvadersGameFrame.pause();
        }
        oneSec++;
    } // end restart method
} // end Gamelogic class