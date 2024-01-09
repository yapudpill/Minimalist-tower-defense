package src.model;

import src.util.Coordinate;
import src.util.Direction;

public class BasicEnemy extends Enemy {

    /**
     * Creates a new <code>BasicEnemy</code> with <code>speed = 0.8</code>,
     * <code>health = 2</code>, <code>reward = 5</code>, and the specified initial
     * position and direction.
     *
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public BasicEnemy(Coordinate initialPos, Direction initialDir) {
        super(1.1, 3, 5, initialPos, initialDir);
    }
}
