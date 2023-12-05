package src.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import src.model.PathCell;
import src.util.Palette;

public class PathCellView extends JPanel {
    private final PathCell cell;

    public PathCellView(PathCell cell) {
        this.cell = cell;
        setBackground(Palette.PATH_FILL);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int w = getWidth(); // This object is expected to be square

        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setColor(Palette.ARROW_FILL);

        switch (cell.direction) {
            case END_OF_PATH: g2D.drawOval(w/4, w/4, w/2, w/2); return;

            case RIGHT: break;
            case DOWN: g2D.rotate(Math.PI/2, w/2, w/2); break;
            case LEFT: g2D.rotate(Math.PI, w/2, w/2); break;
            case UP : g2D.rotate(3*Math.PI/2, w/2, w/2); break;
        }

        drawArrow(g2D);
    }

    private void drawArrow(Graphics2D g2D) {
        int w = getWidth();
        g2D.drawLine(w/2, w/2, w, w/2);     // Middle line
        g2D.drawLine(7*w/8, 3*w/8, w, w/2); // Top line
        g2D.drawLine(7*w/8, 5*w/8, w, w/2); // Bottom line
    }
}
