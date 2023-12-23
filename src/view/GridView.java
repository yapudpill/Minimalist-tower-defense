package src.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

import src.model.Cell;
import src.model.Enemy;
import src.model.PathCell;
import src.model.TowerCell;
import src.util.Direction;

/**
 * A <code>JPanel</code> that displays the game grid, where the tower are placed
 * and enemies are shown.
 */
public class GridView extends JPanel implements ActionListener {
    private final int gridWidth, gridHeight;
    public ArrayList <PathCellView> panels = new ArrayList<>();
    public Enemy enemy;
    Timer timer;

    Image image;

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
                    PathCellView pathCellView = new PathCellView((PathCell)cell);
                    add(pathCellView);
                    if (((PathCell) cell).hasEnemy){
                        panels.add(pathCellView);
                    }
                }
                else{
                    add(new CellView());
                }
            }
        }
        enemy = new Enemy("test_green",0,0);
        image = enemy.image.getImage();
        timer = new Timer(10,this);
        timer.start();
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
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (JPanel jPanel : panels){
            enemy.x = jPanel.getX();
            enemy.y = jPanel.getY();
            g2d.drawImage(image,enemy.x,enemy.y,null);
        }

    }
    @Override
    public void actionPerformed (ActionEvent e){
        enemy.x+=10;
        repaint();
    }


}
