package src.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

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

    int delay = 0;

    public String [] debut = new String[]{"test_green","test_red"};

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
        timer = new Timer(10,this);
        timer.start();
        enemy = generateEnemy(delay);
        image = enemy.image.getImage();
    }

    public Enemy generateEnemy (int temps){
        Enemy e;
        if (temps <= 120){
            int f = new Random().nextInt(debut.length-1);
            e = new Enemy(debut[f],100,1);
            int g = new Random().nextInt(panels.size());
            e.x = panels.get(g).getX();
            e.y = panels.get(g).getY();
            e.cell = panels.get(g).getCell();
            return e;
        }
        else{
            int f = new Random().nextInt(debut.length-1);
            e =  new Enemy(debut[f],200 + temps/20,1+((temps+1)/temps));
            int g = new Random().nextInt(panels.size()-1);
            e.x = panels.get(g).getX();
            e.y = panels.get(g).getY();
            e.cell = panels.get(g).getCell();
            return e;
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
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image,enemy.x,enemy.y,null);
    }
    @Override
    public void actionPerformed (ActionEvent e){
        if (enemy.cell.direction.equals(Direction.UP)){
            enemy.y-=enemy.speed;
        } else if (enemy.cell.direction.equals(Direction.DOWN)) {
            enemy.y += enemy.speed;
        } else if (enemy.cell.direction.equals(Direction.RIGHT)){
            enemy.x+=enemy.speed;
        }else if (enemy.cell.direction.equals(Direction.LEFT)){
                enemy.x -=enemy.speed;
            }
        delay = timer.getDelay()/1000;
        repaint();
    }


}
