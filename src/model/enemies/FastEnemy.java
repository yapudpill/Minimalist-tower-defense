package model.enemies;

import util.Coordinate;
import util.Direction;

public class FastEnemy extends Enemy {

    /**
     * Creates a new <code>FastEnemy</code> with <code>speed = 1.1</code>,
     * <code>health = 2</code>, <code>reward = 5</code>, and the specified initial
     * position and direction.
     *
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public FastEnemy(Coordinate initialPos, Direction initialDir, int remainingCell) {
        super(1.5, 2, 5, initialPos, initialDir, remainingCell);
    }
}
