package src.model;

import src.util.Coordinate;
import src.util.Direction;

public class FastEnemy extends Enemy {

    /**
     * Creates a new <code>FastEnemy</code> with <code>speed = 1.1</code>,
     * <code>health = 2</code>, <code>reward = 5</code>, and the specified initial
     * position and direction.
     *
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public FastEnemy(Coordinate initialPos, Direction initialDir) {
        super(1.5, 3, 5, initialPos, initialDir);
    }
}
