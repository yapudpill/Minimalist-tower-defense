package model.enemies;

import util.Coordinate;
import util.Direction;

public class TankEnemy extends Enemy {

    /**
     * Creates a new <code>TankEnemy</code> with <code>speed = 0.6</code>,
     * <code>health = 4</code>, <code>reward = 5</code>, and the specified initial
     * position and direction.
     *
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public TankEnemy(Coordinate initialPos, Direction initialDir, int remainingCell) {
        super(0.8, 4, 10, initialPos, initialDir, remainingCell);
    }
}
