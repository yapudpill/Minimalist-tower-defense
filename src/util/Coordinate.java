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
     * Creates a new <code>Coordinate</code> by copying the values from a
     * given one.
     *
     * @param cds the <code>Coordinate</code> to copy
     */
    public Coordinate(Coordinate cds) {
        this(cds.x, cds.y);
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

    public void translate(Direction dir, double dist) {
        switch (dir) {
            case UP: this.y -= dist; break;
            case LEFT: this.x -= dist; break;
            case DOWN: this.y += dist; break;
            case RIGHT: this.x += dist; break;
            case END_OF_PATH: break;
        }
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
