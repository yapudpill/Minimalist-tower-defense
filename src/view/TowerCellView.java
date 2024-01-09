package src.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import src.controller.MarathonControl;
import src.model.BasicTower;
import src.model.CanonTower;
import src.model.SniperTower;
import src.model.Tower;
import src.model.TowerCell;
import src.util.Coordinate;

/**
 * A <code>JPanel</code> that displays a tower cell.
 */
public class TowerCellView extends JPanel {
    private final TowerCell cell;
    private final int x, y;

    /**
     * Creates a new <code>TowerCellView</code> that displays the specified
     * <code>TowerCell</code>.
     *
     * <p>
     * The color the the background is set to <code>Palette.TOWER_FILL</code>.
     * Borders are added with color <code>Palette.BACKGROUND</code>.
     * </p>
     *
     * @param cell the <code>TowerCell</code> object to display
     */
    public TowerCellView(TowerCell cell, int x, int y, MarathonControl control) {
        this.cell = cell;
        this.x = x;
        this.y = y;

        setBackground(Palette.TOWER_CELL);
        setBorder(new LineBorder(Palette.BACKGROUND, 5));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                control.addTower(new Coordinate(x, y));
            }

            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        Tower tower = cell.tower;

        Coordinate targetPos = (tower == null ? null : tower.getTargetPos());
        double angle = (targetPos == null ? 0 : Math.atan2(targetPos.y - y, targetPos.x - x));

        if (tower instanceof BasicTower) {
            drawBasic(g2D, angle);
        } else if (tower instanceof CanonTower) {
            drawCanon(g2D, angle);
        } else if (tower instanceof SniperTower) {
            drawSniper(g2D, angle);
        }
    }

    /**
     * Draws a basic tower
     *
     * @param g2D   the <code>Graphics</code> context in which to paint
     * @param angle the angle of the canon head
     */
    private void drawBasic(Graphics2D g2D, double angle) {
        int halfCell = getWidth()/2;

        g2D.setColor(Palette.BASIC_TOWER_OUTER);
        g2D.fillRect(halfCell/2, halfCell/2, halfCell, halfCell);

        g2D.setColor(Palette.BASIC_TOWER_INNER);
        g2D.rotate(angle, halfCell, halfCell);
        g2D.fillOval(3*halfCell/4, 3*halfCell/4, halfCell/2, halfCell/2);
        g2D.fillRect(halfCell, 7*halfCell/8, 3*halfCell/8, halfCell/4);
    }

    /**
     * Draws a canon tower
     *
     * @param g2D   the <code>Graphics</code> context in which to paint
     * @param angle the angle of the canon head
     */
    private void drawCanon(Graphics2D g2D, double angle) {
        int halfCell = getWidth()/2;

        g2D.setColor(Palette.CANON_TOWER_OUTER);
        g2D.fillOval(halfCell/2, halfCell/2, halfCell, halfCell);

        g2D.setColor(Palette.CANON_TOWER_INNER);
        int innerSize = getWidth()/4;
        double innerMedian = Math.sqrt(3) / 2 * innerSize;
        int[] innerPointsX = { (int) (halfCell-innerMedian/3),
                               (int) (halfCell-innerMedian/3),
                               (int) (halfCell+2*innerMedian/3) };
        int[] innerPointsY = { halfCell - innerSize/2, halfCell + innerSize/2, halfCell };
        g2D.rotate(angle, halfCell, halfCell);
        g2D.fillPolygon(innerPointsX, innerPointsY, 3);
    }

    /**
     * Draws a sniper tower
     *
     * @param g2D   the <code>Graphics</code> context in which to paint
     * @param angle the angle of the canon head
     */
    private void drawSniper(Graphics2D g2D, double angle) {
        int halfCell = getWidth()/2;

        g2D.setColor(Palette.SNIPER_TOWER_OUTER);
        double outerMedian = Math.sqrt(3) / 2 * halfCell;
        int[] outerPointsX = { halfCell/2, 3*halfCell/2, halfCell };
        int[] outerPointsY = { (int) (halfCell+outerMedian/3),
                               (int) (halfCell+outerMedian/3),
                               (int) (halfCell-2*outerMedian/3) };
        g2D.fillPolygon(outerPointsX, outerPointsY, 3);

        g2D.setColor(Palette.SNIPER_TOWER_INNER);
        int innerSize = getWidth()/4;
        double innerMedian = Math.sqrt(3) / 2 * innerSize;
        int[] innerPointsX = { (int) (halfCell-innerMedian/3),
                               (int) (halfCell-innerMedian/3),
                               (int) (halfCell+2*innerMedian/3) };
        int[] innerPointsY = { halfCell - innerSize/2, halfCell + innerSize/2, halfCell };
        g2D.rotate(angle, halfCell, halfCell);
        g2D.fillPolygon(innerPointsX, innerPointsY, 3);
    }
}
