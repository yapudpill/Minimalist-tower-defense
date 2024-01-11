package src.model.towers;

import java.util.ArrayList;
import java.util.Iterator;

import src.model.enemies.Enemy;
import src.util.Coordinate;

/**
 * An abstract representation of a tower. Other types of enemies must extend
 * this class
 */
public abstract class Tower {
    public final int damage, range, cost, cooldown;
    public final Coordinate pos;
    public final ArrayList<Bullet> bullets;
    protected int nextShootTime;
    protected Enemy target;

    /**
     * Creates a new <code>Tower</code> with the specified statistics.
     *
     * @param damage   the number of health points that an enemy loses when hit by
     *                 this tower
     * @param range    the maximal distance at which a target can be chosen
     * @param cost     the cost to place this tower in the grid
     * @param cooldown the time between two shots, in milliseconds
     */
    public Tower(int damage, int range, int cost, int cooldown, Coordinate pos) {
        this.damage = damage;
        this.range = range;
        this.cost = cost;
        this.cooldown = cooldown;
        this.pos = pos;
        bullets = new ArrayList<>();
        nextShootTime = 0;
        target = null;
    }

    /**
     * Selects an enemy to target among the ones oh the grid.
     *
     * @param enemies the enemies on the grid
     * @return        the selected target
     */
    abstract Enemy chooseTarget(ArrayList<Enemy> enemies);

    /**
     * Makes the tower shoot if possible and updates all its bullets, returning
     * and eventual gold rewards for killing enemies.
     *
     * @param frameRate the time since the last update, in milliseconds
     * @param enemies   the enemies on the grid
     * @return          the amount of gold earned by the bullets of this tower
     */
    public int update(int frameRate, ArrayList<Enemy> enemies) {
        shoot(frameRate, enemies);
        return updateBullets(frameRate);
    }

    /**
     * Choses a target and, if possible, generate a bullet targeting it.
     *
     * @param frameRate the time since the last update, in milliseconds
     * @param enemies   the enemies on the grid
     */
    private void shoot(int frameRate, ArrayList<Enemy> enemies) {
        target = chooseTarget(enemies);
        nextShootTime -= frameRate;
        if (nextShootTime <= 0 && target != null && validTarget(target)) {
            nextShootTime = cooldown;
            bullets.add(new Bullet(5, new Coordinate(pos), target, damage, range));
        }
    };

    /**
     * @param enemy the enemy to test
     * @return      <code>true</code> if the enemy is in the range of this tower
     */
    protected boolean validTarget(Enemy enemy) {
        return enemy.isOnGrid() && this.pos.distance(enemy.pos) <= range;
    }

    /**
     * Makes all bullets to advance, removes the useless ones from the list
     * returning eventual gold rewards from killing enemies.
     *
     * @param frameRate the time since the last update, in milliseconds
     * @return          the amount of gold earned by the bullets of this tower
     */
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
    };

    /**
     * @return the position of the target of this tower
     */
    public Coordinate getTargetPos() {
        return target == null ? null : target.pos;
    }
}
