package src.model;

import java.util.ArrayList;

/**
 * Represents a cell where a tower can be placed and manages the shooting
 * mechanic.
 */
public class TowerCell extends Cell {
    public Tower tower;

    public int update(int frameRate, ArrayList<Enemy> enemies) {
        if (tower != null) {
            return tower.update(frameRate, enemies);
        }
        return 0;
    }
}
