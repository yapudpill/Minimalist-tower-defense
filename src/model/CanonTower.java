package src.model;

import java.util.ArrayList;

import src.util.Coordinate;

public class CanonTower extends Tower {

    /**
     * Creates a new <code>CanonTower</code> with <code>damage = 2</code>,
     * <code>range = 3</code>, <code>cost = 60</code> and
     * <code>cooldown = 2000</code>.
     */
    public CanonTower(Coordinate pos) {
        super(2, 3, 60, 2000, pos);
    }

    @Override
    Enemy chooseTarget(ArrayList<Enemy> enemies) {
        if (target != null && target.isOnGrid() && inRange(target)) {
            return target;
        }
        for (Enemy enemy : enemies) {
            if (inRange(enemy) && enemy.isAlive()) {
                return enemy;
            }
        }
        return target;
    }
}
