package src.model;

import java.util.ArrayList;
import java.util.Iterator;

import src.util.Coordinate;

/**
 * Represents a cell where a tower can be placed and manages the shooting
 * mechanic.
 */
public class TowerCell extends Cell {
    public final Coordinate pos;
    public final ArrayList<Bullet> bullets;
    private Tower tower;
    private Enemy target;
    private int nextShootTime;

    /**
     * Creates a new <code>TowerCell</code> at the specified position.
     *
     * @param pos the position is the grid of this cell
     */
    public TowerCell(Coordinate pos) {
        this.pos = pos;
        bullets = new ArrayList<>();
        tower = null;
        target = null;
        nextShootTime = 0;
    }

    public int update(int frameRate, ArrayList<Enemy> enemies) {
        if (tower != null) {
            shoot(frameRate, enemies);
            return updateBullets(frameRate);
        }
        return 0;
    }

    /**
     * Selects an enemy among the supplied list and, if possible, shoot at it.
     * If an enemy is killed by this shot, returns its <code>reward</code>.
     *
     * @param frameRate the time since the last update
     * @param enemies   the list of enemies on the grid
     * @return the amount of gold earned with this shot
     */
    public void shoot(int frameRate, ArrayList<Enemy> enemies) {
        // Choose a target
        if (target == null || !target.isOnGrid() || !inRange(target)) {
            for (Enemy enemy : enemies) {
                if (inRange(enemy) && enemy.isAlive()) {
                    target = enemy;
                    break;
                }
            }
        }

        // Shoot if possible
        nextShootTime -= frameRate;
        if (nextShootTime <= 0 && target != null && target.isAlive()) {
            nextShootTime = tower.cooldown;
            bullets.add(new Bullet(4.5, new Coordinate(pos), target, tower.damage));
        }
    }

    /**
     * @param enemy the enemy to test
     * @return whether the specified enemy is in the range of this tower
     */
    private boolean inRange(Enemy enemy) {
        return this.pos.distance(enemy.pos) <= tower.range;
    }

    private int updateBullets(int frameRate) {
        int gold = 0;
        for (Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
            Bullet b = it.next();
            int reward = b.update(frameRate);
            if (reward != 0) {
                it.remove();
            }
            if (reward > 0) {
                gold += reward;
            }
        }
        return gold;
    }

    /**
     * Sets the tower of this cell to the specified value. Also resets the
     * shoot cooldown to match the new tower.
     *
     * @param tower the tower to add to this cell
     */
    public void setTower(Tower tower) {
        this.tower = tower;
        nextShootTime = tower.cooldown;
    }

    /**
     * @return the tower contained in this cell
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * @return the position of the enemy that this tower targets, or
     *         <code>null</code> if none
     */
    public Coordinate getTargetPos() {
        return target == null ? null : target.pos;
    }
}
