package src.util;

/**
 * A minimalist cartesian coordinate system with <code>double</code> coordinates.
 */
public class Coordinate {
    public double x, y;

    /**
     * Creates a new <code>Coordinate</code> with the specified values.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets <code>x</code> and <code>y</code> to the specified values.
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Slides <code>x</code> and <code>y</code> by the specified values.
     *
     * @param dx the x-axis movement
     * @param dy the y-axis movement
     */
    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * @param other the specified point to be measured against this object
     * @return the distance between this <code>Coordinate</code> and the specified
     *         <code>Coordinate</code>
     */
    public double distance(Coordinate other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() == this.getClass()) {
            Coordinate cds = (Coordinate) obj;
            return cds.x == this.x && cds.y == this.y;
        }
        return false;
    }
}
