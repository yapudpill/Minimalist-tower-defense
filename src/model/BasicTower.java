package src.model;

public class BasicTower extends Tower {

    /**
     * Creates a new <code>BasicTower</code> with <code>damage = 1</code>,
     * <code>range = 5</code>, <code>cost = 40</code> and
     * <code>cooldown = 1500</code>.
     */
    public BasicTower() {
        super(1, 5, 40, 1500);
    }
}
