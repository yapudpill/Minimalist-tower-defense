package model.towers;

import java.util.ArrayList;

import model.enemies.Enemy;
import util.Coordinate;

public class CanonTower extends Tower {

    /**
     * Creates a new <code>CanonTower</code> with <code>damage = 2</code>,
     * <code>range = 3</code>, <code>cost = 60</code> and
     * <code>cooldown = 2000</code>.
     */
    public CanonTower(Coordinate pos) {
        super(3, 2, 60, 2000, pos);
    }

    @Override
    Enemy chooseTarget(ArrayList<Enemy> enemies) {
        if (target != null && validTarget(target)) {
            return target;
        }
        for (Enemy enemy : enemies) {
            if (validTarget(enemy)) {
                return enemy;
            }
        }
        return target;
    }
}
