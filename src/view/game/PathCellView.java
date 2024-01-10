package src.view.game;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.model.cells.PathCell;
import src.view.Palette;

/**
 * A <code>JPanel</code> that displays a path cell.
 */
public class PathCellView extends JPanel {
    private final PathCell cell;
    private final boolean isSpawn;

    /**
     * Creates a new <code>PathCellView</code> that displays the specified
     * <code>PathCell</code>.
     *
     * <p>
     * The color the the background is set to <code>Palette.PATH_FILL</code>.
     * The arrow is drawn with color <code>Palette.ARROW_FILL</code>.
     * </p>
     *
     * @param cell the <code>PathCell</code> object to display
     */
    public PathCellView(PathCell cell, boolean isSpawn) {
        this.cell = cell;
        this.isSpawn = isSpawn;
        setBackground(Palette.PATH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(); // This object is expected to be square, so height = width

        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setColor(Palette.ARROW);

        if (isSpawn) {
            g2D.fillOval(3*w/8, 3*w/8, w/4, w/4);
        }

        switch (cell.direction) {
            case END_OF_PATH: g2D.drawOval(w/4, w/4, w/2, w/2); return;

            case RIGHT: break;
            case DOWN: g2D.rotate(Math.PI/2, w/2, w/2); break;
            case LEFT: g2D.rotate(Math.PI, w/2, w/2); break;
            case UP : g2D.rotate(3*Math.PI/2, w/2, w/2); break;
        }

        // Draw the arrow pointing right
        g2D.drawLine(w/2, w/2, w, w/2);     // Middle line
        g2D.drawLine(7*w/8, 3*w/8, w, w/2); // Top line
        g2D.drawLine(7*w/8, 5*w/8, w, w/2); // Bottom line
    }
}
