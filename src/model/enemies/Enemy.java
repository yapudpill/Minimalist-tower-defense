package src.model.enemies;

import static src.util.Direction.END_OF_PATH;

import src.util.Coordinate;
import src.util.Direction;

/**
 * An abstract representation of an enemy. Other types of enemies must extend
 * this class
 */
public abstract class Enemy {
    private static final double EPSILON = 1e-2;

    /**
     * Speed of this enemy, in cells per second
     */
    public final int reward;
    public final Coordinate pos;
    public int health;
    private final double speed;
    private Direction direction;
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
        this.remainingCell = remainingCell;
    }

    /**
     * Updates this enemy by advancing in its <code>direction</code>.
     *
     * @param frameRate time since last update, in milliseconds
     */
    public void update(int frameRate) {
        double step = frameRate * speed / 1000;
        pos.translate(direction, step);
        if (changedCell()) {
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

    /**
     * @return <code>true</code> if this enemy is alive and haven't reached the
     *         end of the path
     */
    public boolean isOnGrid() {
        return isAlive() && direction != END_OF_PATH;
    }

    /**
     * @return <code>true</code> if this enemy changed cell since it last turned
     */
    public boolean changedCell() {
        Coordinate nearest = new Coordinate(Math.round(pos.x), Math.round(pos.y));
        return pos.distance(nearest) < EPSILON;
    }

    /**
     * @return the direction this enemy is following
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the enemy and resets <code>changedCell</code> to
     * <code>false</code>.
     *
     * @param direction the new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
        pos.translate(direction, EPSILON);
    }

    /**
     * @return the number of cell between this enemy and the end of the path
     */
    public int getRemainingCell() {
        return remainingCell;
    }
}
