package src.model.cells;

import src.util.Direction;

/**
 * Represents a cell of a path that enemies follow.
 */
public class PathCell extends Cell {
    /**
     * The direction pointing to the next path cell.
     */
    public final Direction direction;

    /**
     * Creates a new <code>PathCell</code> with the specified direction.
     *
     * @param direction the direction pointing to the next path cell
     */
    public PathCell(Direction direction) {
        this.direction = direction;
    }
}
