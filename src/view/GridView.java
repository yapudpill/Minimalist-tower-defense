package src.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;

import src.controller.MainControl;
import src.model.*;
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
    private Player player;
    public MainControl mainControl;

    /** Lists of enemies that the method generateEnemy uses-----------------------------*/
    public String [] debut = new String[]{"test_green","test_red"};
    /** ---------------------------------------------------------------------------------*/

    /**
     * Creates a new <code>GridView</code> that displays the specified cell grid.
     *
     * @param grid - The cell grid to display
     */
    public GridView(Cell[][] grid, Player player, MainControl mainControl) {
        gridHeight = grid.length;
        gridWidth = grid[0].length;
        boolean first = true;
        this.player = player;
        this.mainControl = mainControl;

        setLayout(new GridLayout(gridHeight, gridWidth));
        for (int i = 0; i<gridHeight;i++) {
            for (int j = 0;j<gridWidth;j++) {
                if (grid[i][j] instanceof TowerCell) {
                    add(new TowerCellView((TowerCell) grid[i][j]));
                } else if (grid[i][j] instanceof PathCell) {
                    PathCellView pathCellView = new PathCellView((PathCell)grid[i][j]);
                    if (((PathCell)(grid[i][j])).direction.equals(Direction.UP)){
                        ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i-1][j];
                        ((PathCell) grid[i][j]).cellView = pathCellView;
                    }
                    else{
                        if (((PathCell)(grid[i][j])).direction.equals(Direction.DOWN)){
                            ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i+1][j];
                            ((PathCell) grid[i][j]).cellView = pathCellView;

                        }
                        else{
                            if (((PathCell)(grid[i][j])).direction.equals(Direction.RIGHT)){
                                ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i][j+1];
                                ((PathCell) grid[i][j]).cellView = pathCellView;
                            }
                            else{
                                if (((PathCell)(grid[i][j])).direction.equals(Direction.LEFT)){
                                    ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i][j-1];
                                    ((PathCell) grid[i][j]).cellView = pathCellView;
                                }
                            }
                        }
                    }
                    add(pathCellView);
                    if (((PathCell) grid[i][j]).spawn){
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
        System.out.println(spawns.size());
    }

    public Enemy generateEnemy (int msElapsed){ /** generates a random enemy depending on how much time have passed */
        Enemy e;
        if (msElapsed <= 120000){
            int f = new Random().nextInt(debut.length);
            e = new Enemy(debut[f],100,10);
            int g = new Random().nextInt(spawns.size());
            e.x = spawns.get(g).getX();
            e.y = spawns.get(g).getY();
            e.cell = spawns.get(g).getCell();
            e.cell.cellView = spawns.get(g);
            return e;
        }
        else{
            int f = new Random().nextInt(debut.length);
            e =  new Enemy(debut[f],200 + msElapsed/20000,10); /** A CHANGER */
            int g = new Random().nextInt(spawns.size());
            e.x = spawns.get(g).getX();
            e.y = spawns.get(g).getY();
            e.cell = spawns.get(g).getCell();
            e.cell.cellView = spawns.get(g);
            return e;
        }

    }

    public boolean hasChangedCell (Enemy enemy){
        return  (enemy.xOnPanel > enemy.cell.cellView.getWidth()-10
                ||enemy.yOnPanel > enemy.cell.cellView.getHeight()-10);
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
        if (player.life == 0){
            timer.stop();
            mainControl.loadStartMenu();
        }
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.cell.direction.equals(Direction.END_OF_PATH)) {
                player.life--;
                iterator.remove();
            }
            else {
                if (enemy.lifePoint == 0){
                    iterator.remove();
                }
            }
        }
        for (Enemy enemy: enemies){
            if (hasChangedCell(enemy)){
                PathCellView pathCellView = enemy.cell.nextCell.cellView;
                enemy.cell = enemy.cell.nextCell;
                enemy.cell.cellView = pathCellView;
                enemy.xOnPanel = 0;
                enemy.yOnPanel = 0;
            }

            if (enemy.cell.direction.equals(Direction.UP)){
                enemy.y-=enemy.speed;
                enemy.yOnPanel+=enemy.speed;
            } else if (enemy.cell.direction.equals(Direction.DOWN)) {
                enemy.y += enemy.speed;
                enemy.yOnPanel += enemy.speed;
            } else if (enemy.cell.direction.equals(Direction.RIGHT)){
                enemy.x+=enemy.speed;
                enemy.xOnPanel += enemy.speed;
            }else if (enemy.cell.direction.equals(Direction.LEFT)){
                enemy.x -=enemy.speed;
                enemy.xOnPanel += enemy.speed;
            }
        }
        if (spawnDelay >= 5000){
            Enemy f = generateEnemy(delay);
            enemies.add(f);
            spawnDelay = 0;
        }
        spawnDelay += timer.getDelay()*2;
        delay += timer.getDelay();
        repaint();
    }
}
