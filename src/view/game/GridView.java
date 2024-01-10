package src.view.game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;

import src.controller.GameControl;
import src.model.Grid;
import src.model.cells.Cell;
import src.model.cells.PathCell;
import src.model.cells.TowerCell;
import src.model.enemies.BasicEnemy;
import src.model.enemies.Enemy;
import src.model.enemies.FastEnemy;
import src.model.enemies.TankEnemy;
import src.model.towers.BasicTower;
import src.model.towers.Bullet;
import src.model.towers.CanonTower;
import src.model.towers.SniperTower;
import src.model.towers.Tower;
import src.util.Direction;
import src.view.Palette;

/**
 * A <code>JPanel</code> that displays the game grid, where the tower are placed
 * and enemies are shown.
 */
public class GridView extends JPanel {
    private final Grid grid;

    /**
     * Creates a new <code>GridView</code> that displays the specified cell grid.
     *
     * @param grid    the cell grid to display
     * @param control the controller of this game
     */
    public GridView(Grid grid, GameControl control) {
        this.grid = grid;
        setLayout(new GridLayout(grid.height, grid.width));

        // Add all the cells to the view
        for (int y = 0; y < grid.height; y++) {
            for (int x = 0; x < grid.width; x++) {
                Cell c = grid.getCell(x, y);
                if (c instanceof TowerCell) {
                    add(new TowerCellView((TowerCell) c, x, y, control));
                } else if (c instanceof PathCell) {
                    add(new PathCellView((PathCell) c, grid.isSpawn(x, y)));
                } else {
                    add(new EmptyCellView());
                }
            }
        }
    }

    // Note: We need to override paintChildren because with paintComponent the
    //       enemies would appear under the grid
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        paintEnemies((Graphics2D) g.create());
        paintBullets((Graphics2D) g.create());
    }

    private void paintEnemies(Graphics2D g2D) {
        int cellSize = getWidth() / grid.width;
        g2D.translate(cellSize/2, cellSize/2); // The model coordinates are shifted by half a cell

        for (Enemy enemy : grid.enemies) {
            int gridx = (int) (enemy.pos.x * cellSize);
            int gridy = (int) (enemy.pos.y * cellSize);

            if (enemy instanceof BasicEnemy) {
                g2D.setColor(Palette.BASIC_ENEMY);
                g2D.fillOval(gridx - cellSize/4, gridy - cellSize/4, cellSize/2, cellSize/2);
            } else if (enemy instanceof TankEnemy) {
                g2D.setColor(Palette.TANK_ENEMY);
                g2D.fillRect(gridx - cellSize/4, gridy - cellSize/4, cellSize/2, cellSize/2);
            } else if (enemy instanceof FastEnemy) {
                g2D.setColor(Palette.FAST_ENEMY);
                fillTriangle(g2D, gridx, gridy, cellSize/2, enemy.getDirection());
            }
        }
    }

    private void paintBullets(Graphics2D g2D) {
        int cellSize = getWidth() / grid.width;
        g2D.translate(cellSize/2, cellSize/2); // The model coordinates are shifted by half a cell
        int diameter = cellSize / 8;

        for (TowerCell cell : grid.towerCells) {
            Tower t = cell.tower;
            if (t == null) {
                continue;
            } else if (t instanceof BasicTower) {
                g2D.setColor(Palette.BASIC_TOWER_OUTER);
            } else if (t instanceof CanonTower) {
                g2D.setColor(Palette.CANON_TOWER_OUTER);
            } else if (t instanceof SniperTower) {
                g2D.setColor(Palette.SNIPER_TOWER_OUTER);
            }

            for (Bullet bullet : t.bullets) {
                int gridx = (int) (bullet.pos.x * cellSize);
                int gridy = (int) (bullet.pos.y * cellSize);
                g2D.fillOval(gridx - diameter / 2, gridy - diameter / 2, diameter, diameter);
            }
        }
    }

    /**
     * Fills a rectangle with the specified center and side size, pointing to the
     * specified direction.
     *
     * @param g2D  the <code>Graphics</code> context in which to paint
     * @param x    the x-coordinate of the center
     * @param y    the y-coordinate of the center
     * @param size the size of triangle side
     * @param dir  the direction the triangle points as
     */
    private void fillTriangle(Graphics2D g2D, int x, int y, int size, Direction dir) {
        double angle;
        switch (dir) {
            case DOWN: angle = Math.PI/2; break;
            case LEFT: angle = Math.PI; break;
            case UP : angle = 3*Math.PI/2; break;
            default: angle = 0; break;
        }
        g2D.rotate(angle, x, y);

        double median = Math.sqrt(3) / 2 * size;
        int[] pointsX = {(int) (x-median/3), (int) (x-median/3), (int)(x+2*median/3)};
        int[] pointsY = {y - size / 2, y + size / 2, y};
        g2D.fillPolygon(pointsX, pointsY, 3);

        g2D.rotate(-angle, x, y);
    }

    /**
     * Selects a preferred size such as the cells are square
     */
    @Override
    public Dimension getPreferredSize() {
        Component parent = getParent();
        int pw = parent.getWidth();
        int ph = parent.getHeight();

        // Magic formula, I don't know why it works, please do not touch
        if (pw * grid.height <= ph * grid.width) {
            return new Dimension(pw, pw * grid.height / grid.width);
        }
        return new Dimension(ph * grid.width / grid.height, ph);
    }
}
