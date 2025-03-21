package model.towers;

import model.enemies.Enemy;
import util.Coordinate;

public class Bullet {
    public final Coordinate pos;
    private final Enemy target;
    private final double speed; // in cells per second
    private final int damage;
    private double range;

    public Bullet(double speed, Coordinate initialPos, Enemy target, int damage, double range) {
        this.pos = initialPos;
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
    }

    /**
     * Updates the position of this bullets, verifying if the target have been
     * touched or if it is already deag.
     *
     * return = 0 : keep on grid
     * return < 0 : remove without reward
     * return > 0 : remove with reward
     *
     * @param frameRate
     * @return whether or not this bullet should be kept on the grid
     */
    public int update(int frameRate) {
        double step = frameRate * speed / 1000;

        if (!target.isOnGrid() || range - step <= 0) {
            return -1;
        }

        if (pos.distance(target.pos) - step <= 0) {
            target.health -= damage;
            if (!target.isAlive()) {
                return target.reward;
            }
            return -1;
        }

        // Thales's theorem
        double coef = step / pos.distance(target.pos);
        double dx = coef * (target.pos.x - pos.x);
        double dy = coef * (target.pos.y - pos.y);
        pos.translate(dx, dy);
        range -= step;
        return 0;
    }
}
