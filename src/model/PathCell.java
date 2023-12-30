package src.model;

import src.util.Direction;
import src.view.PathCellView;

import java.util.ArrayList;

/**
 * Represents a cell of the path that the enemies follow.
 */
public class PathCell extends Cell {
    /**
     * The direction pointing to the next path cell.
     */
    public final Direction direction;
    public boolean spawn;
    public PathCell nextCell;
    public Enemy enemy;


    /**
     * Creates a new <code>PathCell</code> with the specified direction.
     *
     * @param direction - The direction pointing to the next path cell
     */
    public PathCell(Direction direction, boolean spawn) {
        this.direction = direction;
        this.spawn = spawn;
    }


}
