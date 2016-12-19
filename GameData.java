/**
 * Christopher Salinas Jr
 * CS 251L 003
 *
 * Holds some constants for the game in a single location for easy
 * access and modification.
 */
public interface GameData {
    /** Width of a laser fired by player's ship. */
    int LASER_WIDTH = 2;
    /** Height of a laser fired by player's ship. */
    int LASER_HEIGHT = 10;
    /** How far a laser moves in a single step. */
    int LASER_SPEED = 20;

    /** Width of a missile fired by an alien. */
    int MISSILE_WIDTH = 5;
    /** Height of a missile fired by an alien. */
    int MISSILE_HEIGHT = 5;
    /** How far a missile moves in a single step. */
    int MISSILE_SPEED = 5;

    /** Width of an alien. */
    int ALIEN_WIDTH = 20;
    /** Height of an alien. */
    int ALIEN_HEIGHT = 20;
    /** How far an alien moves in a single step. */
    int ALIEN_SPEED = 10;
    /** Number of columns of aliens. */
    int COL = 5;
    /** Number of rows of aliens. */
    int ROW = 5;

    /** Width of the ship. */
    int SHIP_WIDTH = 30;
    /** Height of the ship. */
    int SHIP_HEIGHT = 18;
    /** How far the ship moves in a single step. */
    int SHIP_SPEED = 5;
    /** Initial x-value of the ship. */
    int SHIP_X = 285;

    /** Width of the shield. */
    int SHIELD_WIDTH = 50;
    /** Height of the shield. */
    int SHIELD_HEIGHT = 15;

    /** Width of game area. */
    int GAME_BOARD_WIDTH = 600;
    /** Height of game area. */
    int GAME_BOARD_HEIGHT = 400;
}