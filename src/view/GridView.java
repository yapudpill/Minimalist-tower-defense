package src.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import src.model.Cell;
import src.model.PathCell;
import src.model.TowerCell;

public class GridView extends JPanel {
    private final int gridWidth, gridHeight;

    public GridView(Cell[][] grid) {
        gridHeight = grid.length;
        gridWidth = grid[0].length;
        setLayout(new GridLayout(gridHeight, gridWidth));

        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell instanceof TowerCell) {
                    add(new TowerCellView((TowerCell) cell));
                } else if (cell instanceof PathCell) {
                    add(new PathCellView((PathCell) cell));
                } else {
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
