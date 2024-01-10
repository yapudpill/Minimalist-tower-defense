package src.model.towers;

import java.util.ArrayList;

import src.model.enemies.Enemy;
import src.util.Coordinate;

public class BasicTower extends Tower {

    /**
     * Creates a new <code>BasicTower</code> with <code>damage = 1</code>,
     * <code>range = 5</code>, <code>cost = 40</code> and
     * <code>cooldown = 1500</code>.
     */
    public BasicTower(Coordinate pos) {
        super(1, 5, 40, 1500, pos);
    }

    @Override
    Enemy chooseTarget(ArrayList<Enemy> enemies) {
        if (target != null && target.isOnGrid() && inRange(target)) {
            return target;
        }

        int minDist = Integer.MAX_VALUE;
        Enemy selected = null;
        for (Enemy enemy : enemies) {
            if (inRange(enemy) && enemy.isAlive() && enemy.getRemainingCell() < minDist) {
                minDist = enemy.getRemainingCell();
                selected = enemy;
            }
        }
        return selected == null ? target : selected;
    }
}
