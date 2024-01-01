package src.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.controller.MainControl;
import src.model.*;
import src.util.Direction;
/**
 * A <code>JPanel</code> that displays the game grid, where the tower are placed
 * and enemies are shown.
 */
public class GridView extends JPanel implements ActionListener, MouseListener {
    private final int gridWidth, gridHeight;
    private final ArrayList <PathCellView> spawns = new ArrayList<>(); // List of spawn cells
    private final ArrayList<Enemy> enemies = new ArrayList<>(); // List of enemies that are currently on the board 
    private final ArrayList<TowerCell> towerCells = new ArrayList<>(); // List of TowerCells that currently has a tower
    private final Timer timer;
    private int spawnDelay;
    private int delay; // The amount of time that has passed in ms (timer.getDelay doesn't work as intended, so it's not precise)
    private final Player player;
    private final MainControl mainControl;
    
    private final TowerShopView towerShopView;
    private final Cell[][] grid;


    /** Lists of enemies that the method generateEnemy uses-----------------------------*/
    public String [] debut = new String[]{"test_green","test_red"};
    /** ---------------------------------------------------------------------------------*/

    /**
     * Creates a new <code>GridView</code> that displays the specified cell grid.
     *
     * @param grid - The cell grid to display
     */
    public GridView(Cell[][] grid, Player player, MainControl mainControl,TowerShopView towerShopView) {
        this.grid = grid;
        addMouseListener(this);
        this.towerShopView = towerShopView;
        gridHeight = grid.length;
        gridWidth = grid[0].length;
        this.player = player;
        this.mainControl = mainControl;

        setLayout(new GridLayout(gridHeight, gridWidth));
        addCellsToView();

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) { // Update the images whenever the player resize the window
                updateImageSize();
                repaint();
            }
        });
        
        timer = new Timer(1,this);
        timer.start();
    }
    private void addCellsToView (){
        for (int i = 0; i<gridHeight;i++) {
            for (int j = 0;j<gridWidth;j++) {
                if (grid[i][j] instanceof TowerCell) {
                    add(new TowerCellView((TowerCell) grid[i][j]));
                } else if (grid[i][j] instanceof PathCell) {
                    PathCellView pathCellView = new PathCellView((PathCell)grid[i][j]);
                    if (((PathCell)(grid[i][j])).direction.equals(Direction.UP)){
                        ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i-1][j];
                    }
                    else{
                        if (((PathCell)(grid[i][j])).direction.equals(Direction.DOWN)){
                            ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i+1][j];

                        }
                        else{
                            if (((PathCell)(grid[i][j])).direction.equals(Direction.RIGHT)){
                                ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i][j+1];
                            }
                            else{
                                if (((PathCell)(grid[i][j])).direction.equals(Direction.LEFT)){
                                    ((PathCell) grid[i][j]).nextCell = (PathCell) grid[i][j-1];
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
    }
    /** Generates an image with a size that fills the entire cell  (only need to put the file's name without the path and extension(has to be png tho)*/
    private Image generateImage (String s){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/resources/ennemies/" + s + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(getWidth()/gridWidth,getHeight()/gridHeight,Image.SCALE_DEFAULT);
        return dimg;
    }
    /** Update tower and enemie's images when the player resizes the window */
    private void updateImageSize (){
        for (int i = 0; i<gridHeight;i++){
            for (int j = 0;j<gridWidth;j++ ){
                if (grid[i][j] instanceof TowerCell){
                    if (((TowerCell) grid[i][j]).tower != null && ((TowerCell) grid[i][j]).tower.image != null){
                        ((TowerCell) grid[i][j]).tower.image = new ImageIcon(generateImage(((TowerCell) grid[i][j]).tower.name));
                        ((TowerCell) grid[i][j]).coordinates = getCellViewCoordsFromIndex(i,j);
                        
                    }
                }
                else{
                    if (grid[i][j] instanceof PathCell){
                        if (((PathCell) grid[i][j]).enemy != null){
                            ((PathCell) grid[i][j]).enemy.image =  new ImageIcon(generateImage(((PathCell) grid[i][j]).enemy.name));
                            ((PathCell) grid[i][j]).enemy.coordinates = getCellViewCoordsFromIndex(i,j);
                        }
                    }
                }
            }
        }
    }
    
    /** Generates a random enemy depending on how much time has passed */
    public Enemy generateEnemy (int msElapsed){
        Enemy e;
        if (msElapsed <= 120000){
            int f = new Random().nextInt(debut.length);
            e = new Enemy(debut[f],100,1,new ImageIcon (generateImage(debut[f])));
            int g = new Random().nextInt(spawns.size());
            e.coordinates.x = spawns.get(g).getX();
            e.coordinates.y = spawns.get(g).getY();
            e.cell = spawns.get(g).getCell();
            e.cell.enemy = e;
            return e;
        }
        else{
            int f = new Random().nextInt(debut.length);
            e =  new Enemy(debut[f],200 + msElapsed/20000,1,new ImageIcon (generateImage(debut[f]))); /** A CHANGER */
            int g = new Random().nextInt(spawns.size());
            e.coordinates.x = spawns.get(g).getX();
            e.coordinates.y = spawns.get(g).getY();
            e.cell = spawns.get(g).getCell();
            e.cell.enemy = e;
            return e;
        }

    }
    /** Returns the index of a cell in the array Grid, where point.x is the xth line and point.y is the yth line */ 
    public Point getIndexFromCell (Cell cell){
        for (int i = 0; i <gridHeight; i++){
            for (int j = 0; j < gridWidth; j++){
                if (grid[i][j] == cell){
                    return new Point(i,j);
                }
            }
        }
        return null; // Not supposed to happen
    }

    /** Checks whether an enemy is within a tower's range */
    public boolean isInRange(TowerCell towerCell, Enemy enemy) {
        int range = towerCell.tower.range;
        int towerX = getIndexFromCell(towerCell).x;
        int towerY = getIndexFromCell(towerCell).y;
        int enemyX = getIndexFromCell(enemy.cell).x;
        int enemyY = getIndexFromCell(enemy.cell).y;

        int distanceX = Math.abs(towerX - enemyX);
        int distanceY = Math.abs(towerY - enemyY);

        return distanceX <= range && distanceY <= range;
    }

    /** Gives the corresponding cell for the given coordinates on the game board */   
    private Cell getCell (Point p){
        return grid[Math.floorDiv(p.y,getHeight()/gridHeight)][Math.floorDiv(p.x,(getWidth()/gridWidth))];
    }
    
    /** Gives the corresponding coordinates on the game board for the given index in the array Grid */
    private Point getCellViewCoordsFromIndex (int i, int j){
        return (new Point (j*getWidth()/gridWidth,i*getHeight()/gridHeight));
    }

    /** Checks whether an enemy has changed a cell visually */
    public boolean hasChangedCell (Enemy enemy){
        return  getCell(new Point(enemy.coordinates.x,enemy.coordinates.y)) != enemy.cell;
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
        
        // Player's gold view --------------------------------------------------------------------------
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0,getHeight()-getHeight()/13,getWidth()/10,getHeight()/13);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(player.gold),getWidth()/26,getHeight()-getHeight()/26);
        //----------------------------------------------------------------------------------------------
        
        // enemies' wiew------------------------------------------------------------------------------
        for (Enemy enemy: enemies){
            g2d.drawImage(enemy.image.getImage(),enemy.coordinates.x,enemy.coordinates.y,null);
        }
        //-------------------------------------------------------------------------------------------------
        
        //towers' view--------------------------------------------------------------------------------------
        for (Cell[] cells : grid){
            for (Cell cell: cells){
                if (cell instanceof TowerCell){
                    if (((TowerCell) cell).tower != null){
                        g2d.drawImage(((TowerCell)(cell)).tower.image.getImage(),((TowerCell)cell).coordinates.x,((TowerCell)cell).coordinates.y,null);
                    }
                }
            }
        }
        //---------------------------------------------------------------------------------------------------
    }
    @Override
    public void actionPerformed (ActionEvent e){
        if (player.life == 0){
            timer.stop();
            mainControl.loadStartMenu();
        }
        
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) { // Makes the player lose one life point whenever an enemy reach END_OF_PATH and removes the enemy from the board
            Enemy enemy = iterator.next();
            if (enemy.cell.direction.equals(Direction.END_OF_PATH)) {
                player.life--;
                iterator.remove();
            }
            else { // Makes the player gain 20 gold everytime an enemy dies (can be changed by making an if on the enemy's name) and makes it disappear
                if (enemy.lifePoint <= 0){
                    player.gold += 20;
                    iterator.remove();
                }
            }
        }
        for (Enemy enemy: enemies){ // Change the cell that is bound to the enemy when it changes a cell visually
            if (hasChangedCell(enemy)){
                enemy.cell.enemy = null;
                enemy.cell.nextCell.enemy = enemy;
                enemy.cell = enemy.cell.nextCell;
            }
            
            // Makes the enemy move depending on the Direction, that the PathCell the enemy currently is in, has 
            if (enemy.cell.direction.equals(Direction.UP)){
                enemy.coordinates.y-=enemy.speed;
            } else if (enemy.cell.direction.equals(Direction.DOWN)) {
                enemy.coordinates.y += enemy.speed;
            } else if (enemy.cell.direction.equals(Direction.RIGHT)){
                enemy.coordinates.x+=enemy.speed;
            }else if (enemy.cell.direction.equals(Direction.LEFT)){
                enemy.coordinates.x -=enemy.speed;
            }
            
            for (TowerCell towerCell: towerCells){ // the towers will always prioritize the enemy that is closest to the END_OF_PATH because whenever a tower attacks it resets its cd to 0, which means that the next enemy in the loop won't trigger the attack (is it what we want ?)
                if (towerCell.tower != null){
                    if (isInRange(towerCell,enemy)){
                        if (towerCell.tower.cooldown >= 2000) {
                            enemy.lifePoint -= towerCell.tower.damage;
                            towerCell.tower.cooldown = 0;
                            //System.out.println( towerCell.tower + " a fait " + towerCell.tower.damage + " dmg à " + enemy + "de couleur " + enemy.name + ", il lui reste " + enemy.lifePoint + " hp");
                        }
                    }
                }
            }
        }
        
        // Towers' cooldown 
        for (TowerCell towerCell: towerCells){
            towerCell.tower.cooldown += timer.getDelay()*20;
        }
        
        // Frequency at which enemies spawn
        if (spawnDelay >= 5000){
            Enemy f = generateEnemy(delay);
            enemies.add(f);
            spawnDelay = 0;
        }
        
        // Checks whether the player has enough gold, to make a button available
        for (TowerShopModel towerShopModel: towerShopView.buttons){
            if (towerShopModel.tower != null){
                if (towerShopModel.tower.cost <= player.gold){
                    towerShopModel.button.setEnabled(true);
                }
                else{
                    towerShopModel.button.setEnabled(false);
                }
            }
            else{
                towerShopModel.button.setEnabled(false);
            }
        }
        
        // Time tracking
        spawnDelay += timer.getDelay()*20;
        delay += timer.getDelay()*20;
        repaint();

        // Updates the selected tower view (the label in TowerShopView)
        if (towerShopView.towerModel == null && towerShopView.hasBeenAdded){
            towerShopView.remove(towerShopView.label);
            towerShopView.hasBeenAdded = false;
            towerShopView.revalidate();
            towerShopView.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { // Enables the tower's location selection
        // Calculates the coordinates of the top left of the selected cell
        Point clickPoint = e.getPoint();
        int cellWidth = getWidth() / gridWidth; 
        int cellHeight = getHeight() / gridHeight;
        
        int cellX = Math.floorDiv(clickPoint.x, cellWidth) * cellWidth;
        int cellY = Math.floorDiv(clickPoint.y, cellHeight) * cellHeight;
        
        if (towerShopView.towerModel != null && getCell(clickPoint) instanceof TowerCell){ // checks if a tower has been selected and if the selected cell is a TowerCell
            Image image = generateImage(towerShopView.towerModel.name);
            ((TowerCell) getCell(clickPoint)).tower = new TowerModel(towerShopView.towerModel,new ImageIcon(image));
            ((TowerCell) getCell(clickPoint)).coordinates = new Point (cellX, cellY);
            towerCells.add((TowerCell) getCell(clickPoint));
            player.gold -= towerShopView.towerModel.cost; 
            towerShopView.towerModel = null;
            
        }
        else{ // Makes the label disappear when you don't click on a TowerCell
            towerShopView.towerModel = null;
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
