package src.model;

import java.util.ArrayList;
import java.util.Iterator;

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

    abstract Enemy chooseTarget(ArrayList<Enemy> enemies);

    public int update(int frameRate, ArrayList<Enemy> enemies) {
        shoot(frameRate, enemies);
        return updateBullets(frameRate);
    }

    private void shoot(int frameRate, ArrayList<Enemy> enemies) {
        target = chooseTarget(enemies);
        nextShootTime -= frameRate;
        if (nextShootTime <= 0 && target != null && target.isAlive()) {
            nextShootTime = cooldown;
            bullets.add(new Bullet(5, new Coordinate(pos), target, damage));
        }
    };

    protected boolean inRange(Enemy enemy) {
        return this.pos.distance(enemy.pos) <= range;
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
    };

    public Coordinate getTargetPos() {
        return target == null ? null : target.pos;
    }
}
