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
    public final int reward;
    public final Coordinate pos;
    public int health;
    private final double speed;
    private Direction direction;
    private boolean changedCell;
    private int remainingCell;

    /**
     * Creates a new <code>Enemy</code> with the specified statistics.
     *
     * @param speed      the speed of this enemy, in cells per second
     * @param health     the number of health points of this enemy
     * @param reward     the reward awarded when this enemy is killed
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public Enemy(double speed, int health, int reward, Coordinate initialPos, Direction initialDir, int remainingCell) {
        this.speed = speed;
        this.health = health;
        this.pos = initialPos;
        this.direction = initialDir;
        this.reward = reward;
        this.changedCell = false;
        this.remainingCell = remainingCell;
    }

    /**
     * Updates this enemy by advancing in its <code>direction</code>.
     *
     * @param frameRate time since last update, in milliseconds
     */
    public void update(int frameRate) {
        Coordinate old = new Coordinate(pos);
        double step = frameRate * speed / 1000;
        switch (direction) {
            case UP:    pos.translate(0, -step); break;
            case LEFT:  pos.translate(-step, 0); break;
            case DOWN:  pos.translate(0, step); break;
            case RIGHT: pos.translate(step, 0); break;
            default:    break;
        }
        changedCell = (int) old.x != (int) pos.x || (int) old.y != (int) pos.y;
        if (changedCell) {
            remainingCell--;
        }
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

    public boolean changedCell() {
        return changedCell;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        changedCell = false;
    }

    public int getRemainingCell() {
        return remainingCell;
    }
}
