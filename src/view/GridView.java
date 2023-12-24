package src.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public ArrayList <PathCellView> spawns = new ArrayList<>(); /** list of spawn cells*/
    public ArrayList<Enemy> enemies = new ArrayList<>(); /** List of enemies that are currently on the board */
    private final Timer timer;
    private int spawnDelay;
    private int delay = 0;
    private PathCellView prev;

    /** Lists of enemies that the method generateEnemy uses-----------------------------*/
    public String [] debut = new String[]{"test_green","test_red"};
    /** ---------------------------------------------------------------------------------*/

    /**
     * Creates a new <code>GridView</code> that displays the specified cell grid.
     *
     * @param grid - The cell grid to display
     */
    public GridView(Cell[][] grid) {
        gridHeight = grid.length;
        gridWidth = grid[0].length;
        boolean first = true;

        setLayout(new GridLayout(gridHeight, gridWidth));
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell instanceof TowerCell) {
                    add(new TowerCellView((TowerCell) cell));
                } else if (cell instanceof PathCell) {
                    PathCellView pathCellView = new PathCellView((PathCell)cell);
                    if (first){
                        prev = pathCellView;
                        first = false;
                    }
                    else{
                        prev.getCell().nextCell = pathCellView.getCell();
                        prev.nextCellView = pathCellView;
                        prev = pathCellView;
                    }
                    add(pathCellView);
                    if (((PathCell) cell).spawn){
                        spawns.add(pathCellView);
                    }
                }
                else{
                    add(new CellView());
                }
            }
        }
        timer = new Timer(10,this);
        timer.start();
        enemies.add(generateEnemy(delay));
    }

    public Enemy generateEnemy (int msElapsed){ /** generates a random enemy depending on how much time have passed */
        Enemy e;
        if (msElapsed <= 120000){
            int f = new Random().nextInt(debut.length);
            e = new Enemy(debut[f],100,1);
            int g = new Random().nextInt(spawns.size());
            e.x = spawns.get(g).getX();
            e.y = spawns.get(g).getY();
            e.cell = spawns.get(g).getCell();
            e.cell.cellView = spawns.get(g);
            return e;
        }
        else{
            int f = new Random().nextInt(debut.length);
            e =  new Enemy(debut[f],200 + msElapsed/20000,1+((msElapsed+1)/msElapsed)); /** A CHANGER */
            int g = new Random().nextInt(spawns.size());
            e.x = spawns.get(g).getX();
            e.y = spawns.get(g).getY();
            e.cell = spawns.get(g).getCell();
            e.cell.cellView = spawns.get(g);
            return e;
        }

    }

    public boolean hasChangedCell (Enemy enemy){
        return  (Math.abs(enemy.xOnPanel) > enemy.cell.cellView.getWidth()
                || Math.abs(enemy.yOnPanel) > enemy.cell.cellView.getHeight());
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
        for (Enemy enemy: enemies){
            g2d.drawImage(enemy.image.getImage(),enemy.x,enemy.y,null);
        }
    }
    @Override
    public void actionPerformed (ActionEvent e){
        enemies.removeIf(enemy -> enemy.lifePoint == 0 || enemy.cell.direction.equals(Direction.END_OF_PATH));
        for (Enemy enemy: enemies){
            if (hasChangedCell(enemy)){
                PathCellView pathCellView = enemy.cell.cellView.nextCellView;
                enemy.cell = enemy.cell.nextCell;
                enemy.cell.cellView = pathCellView;
                enemy.xOnPanel = 0;
                enemy.yOnPanel = 0;
            }

            if (enemy.cell.direction.equals(Direction.UP)){
                enemy.y-=enemy.speed;
                enemy.yOnPanel-=1;
            } else if (enemy.cell.direction.equals(Direction.DOWN)) {
                enemy.y += enemy.speed;
                enemy.yOnPanel += 1;
            } else if (enemy.cell.direction.equals(Direction.RIGHT)){
                enemy.x+=enemy.speed;
                enemy.xOnPanel += 1;
            }else if (enemy.cell.direction.equals(Direction.LEFT)){
                enemy.x -=enemy.speed;
                enemy.xOnPanel -= 1;
            }
        }
        if (spawnDelay >= 5000){
            enemies.add(generateEnemy(delay));
            spawnDelay = 0;
        }
        spawnDelay += timer.getDelay();
        delay += timer.getDelay();
        repaint();
        System.out.println(delay);

    }
}
