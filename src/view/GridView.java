package src.view;

import java.awt.*;

import javax.swing.*;

import src.model.Cell;
import src.model.PathCell;
import src.model.TowerCell;

/**
 * A <code>JPanel</code> that displays the game grid, where the tower are placed
 * and enemies are shown.
 */
public class GridView extends JPanel {
    private final int gridWidth, gridHeight;

    /**
     * Creates a new <code>GridView</code> that displays the specified cell grid.
     *
     * @param grid - The cell grid to display
     */
    public GridView(Cell[][] grid) {
        gridHeight = grid.length;
        gridWidth = grid[0].length;
        setLayout(new GridLayout(gridHeight, gridWidth));

        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell instanceof TowerCell) {
                    add(new TowerCellView((TowerCell) cell));
                } else if (cell instanceof PathCell) {
                    JPanel panel = new PathCellView((PathCell)cell);
                    if (((PathCell) cell).spawn){
                        panel.add(new EnemyView("test_red",0,0));
                    }
                    add(panel);
                }
                else{
                    add(new CellView());
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Component parent = getParent();
        int pw = parent.getWidth();
        int ph = parent.getHeight();

        if (pw * gridHeight <= ph * gridWidth) {
            return new Dimension(pw, pw * gridHeight / gridWidth);
        }
        return new Dimension(ph * gridWidth / gridHeight, ph);
    }
}
