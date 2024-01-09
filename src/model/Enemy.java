package src.model;

import static src.util.Direction.END_OF_PATH;

import src.util.Coordinate;
import src.util.Direction;

/**
 * An abstract representation of an enemy. Other types of enemies must extend
 * this class
 */
public abstract class Enemy {

    /**
     * Speed of this enemy, in cells per second
     */
    public final double speed;
    public final int reward;
    public final Coordinate pos;
    public int health;
    public Direction direction;

    /**
     * Creates a new <code>Enemy</code> with the specified statistics.
     *
     * @param speed      the speed of this enemy, in cells per second
     * @param health     the number of health points of this enemy
     * @param reward     the reward awarded when this enemy is killed
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public Enemy(double speed, int health, int reward, Coordinate initialPos, Direction initialDir) {
        this.speed = speed;
        this.health = health;
        this.pos = initialPos;
        this.direction = initialDir;
        this.reward = reward;
    }

    /**
     * Updates this enemy by advancing in its <code>direction</code>.
     *
     * @param frameRate time since last update, in milliseconds
     */
    public void update(int frameRate) {
        double translation = frameRate * speed / 1000;
        switch (direction) {
            case UP:    pos.translate(0, -translation); break;
            case LEFT:  pos.translate(-translation, 0); break;
            case DOWN:  pos.translate(0, translation); break;
            case RIGHT: pos.translate(translation, 0); break;
            default:    break;
        }
    }

    /**
     * Determines if this enemy needs to turn. It is determined by looking if
     * this enemy crossed an intersection.
     *
     * @return whether or not this enemy needs to turn
     */
    public boolean needToTurn() {
        switch (direction) {
            case UP:    return decimal(pos.y) >= 0.5;
            case LEFT:  return decimal(pos.x) >= 0.5;
            case DOWN:  return decimal(pos.y) <= 0.5;
            case RIGHT: return decimal(pos.x) <= 0.5;
            default:    return false;
        }
    }

    private static double decimal(double n) {
        return Math.abs(n - Math.abs(n));
    }

    /**
     * @return <code>true</code> if the <code>health</code> of this enemy is
     *         strictly positive
     */
    public boolean isAlive() {
        return health > 0;
    }

    public boolean isOnGrid() {
        return isAlive() && direction != END_OF_PATH;
    }
}
