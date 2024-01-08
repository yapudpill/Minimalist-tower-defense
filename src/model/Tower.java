package src.model;

/**
 * An abstract representation of a tower. Other types of enemies must extend
 * this class
 */
public abstract class Tower {
    public final int damage, range, cost, cooldown;

    /**
     * Creates a new <code>Tower</code> with the specified statistics.
     *
     * @param damage   the number of health points that an enemy loses when hit by
     *                 this tower
     * @param range    the maximal distance at which a target can be chosen
     * @param cost     the cost to place this tower in the grid
     * @param cooldown the time between two shots, in milliseconds
     */
    public Tower(int damage, int range, int cost, int cooldown) {
        this.damage = damage;
        this.range = range;
        this.cost = cost;
        this.cooldown = cooldown;
    }
}
