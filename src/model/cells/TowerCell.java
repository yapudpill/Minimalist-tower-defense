package src.model.cells;

import java.util.ArrayList;

import src.model.enemies.Enemy;
import src.model.towers.Tower;

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
