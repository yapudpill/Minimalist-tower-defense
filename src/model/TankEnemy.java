package src.model;

import src.util.Coordinate;
import src.util.Direction;

public class TankEnemy extends Enemy {

    /**
     * Creates a new <code>TankEnemy</code> with <code>speed = 0.6</code>,
     * <code>health = 4</code>, <code>reward = 5</code>, and the specified initial
     * position and direction.
     *
     * @param initialPos the position of this enemy when spawned
     * @param initialDir the direction of this enemy when spawned
     */
    public TankEnemy(Coordinate initialPos, Direction initialDir) {
        super(1, 5, 5, initialPos, initialDir);
    }
}
