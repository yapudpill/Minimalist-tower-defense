package model;

import util.Direction;

public class PathCell extends Cell {
    public final Direction direction;

    public PathCell(Direction direction) {
        this.direction = direction;
    }
}
