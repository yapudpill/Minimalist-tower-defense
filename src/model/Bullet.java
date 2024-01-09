package src.model;

import src.util.Coordinate;
import src.util.Direction;

public class Bullet {
    public final Coordinate pos;
    private final Enemy target;
    private final double speed; // in cells per second
    private final int damage;

    public Bullet(double speed, Coordinate initialPos, Enemy target, int damage) {
        this.pos = initialPos;
        this.target = target;
        this.speed = speed;
        this.damage = damage;
    }

    /**
     * TODO: better description
     * return = 0 : keep on grid
     * return < 0 : remove without reward
     * return > 0 : remove with reward
     *
     * @param frameRate
     * @return
     */
    public int update(int frameRate) {
        if (hasNoTarget()) {
            return -1;
        }

        double step = frameRate * speed / 1000;
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
        return 0;
    }

    public boolean hasNoTarget() {
        return !target.isAlive() || target.direction == Direction.END_OF_PATH;
    }
}
