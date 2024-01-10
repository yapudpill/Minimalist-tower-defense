package src.model;

import static src.util.Direction.DOWN;
import static src.util.Direction.END_OF_PATH;
import static src.util.Direction.LEFT;
import static src.util.Direction.RIGHT;
import static src.util.Direction.UP;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import src.model.cells.Cell;
import src.model.cells.EmptyCell;
import src.model.cells.PathCell;
import src.model.cells.TowerCell;
import src.model.enemies.Enemy;
import src.util.Coordinate;
import src.util.Direction;
import src.util.TriFunction;

/**
 * The <code>Grid</code> class stores everything about the cells, the towers
 * and the enemies currently in game. It also manages spawning new enemies and
 * clearing useless ones.
 */
public class Grid {

    /**
     * Time between to enemy spawn within the same wave
     */
    private static final int spawnInterval = 300;
    private static final Random rnd = new Random();

    public final ArrayList<Coordinate> spawnPoints;
    public final ArrayList<TowerCell> towerCells;
    public final ArrayList<Enemy> enemies;
    public final int width, height;
    public final Cell[][] grid;
    private final ArrayDeque<Enemy> toSpawn;
    private int nextSpawnTime;

    /**
     * Creates a new <code>Grid</code> by reading a map from the specified reader.
     * Only reads the header line and the grid, allowing storage of supplementary
     * data below the grid.
     *
     * @param reader the reader from which the map is read
     */
    public Grid(BufferedReader reader) {
        spawnPoints = new ArrayList<>();
        towerCells = new ArrayList<>();
        enemies = new ArrayList<>();
        toSpawn = new ArrayDeque<>();
        nextSpawnTime = 0;

        Cell[][] gridTmp = null;
        int widthTmp = 0;
        int heightTmp = 0;

        try {
            int[] firstLine = Arrays.stream(reader.readLine().split(" "))
                                    .mapToInt(Integer::valueOf)
                                    .toArray();
            widthTmp = firstLine[0];
            heightTmp = firstLine[1];
            gridTmp = new Cell[heightTmp][widthTmp];

            for (int y = 0; y < heightTmp; y++) {
                char[] l = reader.readLine().toCharArray();
                for (int x = 0; x < widthTmp; x++) {
                    switch (l[x]) {
                        case '.': gridTmp[y][x] = new EmptyCell(); break;

                        case 'u': spawnPoints.add(new Coordinate(x, y));
                        case '^': gridTmp[y][x] = new PathCell(UP); break;

                        case 'l': spawnPoints.add(new Coordinate(x, y));
                        case '<': gridTmp[y][x] = new PathCell(LEFT); break;

                        case 'd': spawnPoints.add(new Coordinate(x, y));
                        case 'v': gridTmp[y][x] = new PathCell(DOWN); break;

                        case 'r': spawnPoints.add(new Coordinate(x, y));
                        case '>': gridTmp[y][x] = new PathCell(RIGHT); break;

                        case 'x': gridTmp[y][x] = new PathCell(END_OF_PATH); break;

                        case 'T':
                            gridTmp[y][x] = new TowerCell();
                            towerCells.add((TowerCell) gridTmp[y][x]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        width = widthTmp;
        height = heightTmp;
        grid = gridTmp;
    }

    /**
     * Adds the specified number of enemies to the <code>toSpawn</code> Queue
     * using the specified constructor.
     *
     * @param nb     the number of enemies to spawn
     * @param constr the enemy constructor
     */
    public void spawnEnemies(ArrayList<TriFunction<Coordinate, Direction,Integer, Enemy>> constructors) {
        for (var constr : constructors) {
            Coordinate spawn = new Coordinate(randomSpawn());
            Direction direction = getDirection(spawn);
            toSpawn.add(constr.apply(spawn, direction, distanceToEnd(spawn)));
        }
    }

    /**
     * Selects a random spawn point in <code>spawnPoints</code>.
     *
     * @return the coordinates of a randomly selected spawn point
     */
    private Coordinate randomSpawn() {
        return spawnPoints.get(rnd.nextInt(spawnPoints.size()));
    }

    private int distanceToEnd(Coordinate cds) {
        int dist = 0;
        int x = (int) cds.x;
        int y = (int) cds.y;
        for (Direction d = getDirection(x, y); d != END_OF_PATH; d = getDirection(x, y)) {
            switch (d) {
                case DOWN:   y++; break;
                case LEFT:   x--; break;
                case UP:     y--; break;
                case RIGHT:  x++; break;
                case END_OF_PATH: break;
            }
            dist++;
        }
        return dist;
    }

    /**
     * Cleans the <code>enemies</code> list by removing the one that are dead
     * or that reached the end of the path.
     * <p>Then spawn a new enemy from the <code>toSpawn</code> Queue if the delay
     * is reached.
     *
     * @param frameRate the time since the last update, in milliseconds
     * @return <code>true</code> if there is still enemies to spawn,
     *         <code>false</code> otherwise
     */
    public boolean updateEnemiesList(int frameRate, GameStats stats) {
        for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
            Enemy enemy = it.next();
            if (!enemy.isOnGrid()) {
                it.remove();
                if (!enemy.isAlive()) {
                    stats.enemyDied(enemy);
                }
            }
        }

        nextSpawnTime -= frameRate;
        if (nextSpawnTime <= 0 && !toSpawn.isEmpty()) {
            nextSpawnTime = spawnInterval;
            enemies.add(toSpawn.poll());
        }
        return !toSpawn.isEmpty();
    }

    /**
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the cell located at the specified coordinates
     */
    public Cell getCell(int x, int y) {
        return grid[y][x];
    }

    /**
     * @param cds the specified coordinates
     * @return the cell located at the specified coordinates
     */
    public Cell getCell(Coordinate cds) {
        return getCell((int) cds.x, (int) cds.y);
    }

    public Direction getDirection(int x, int y) {
        Cell c = getCell(x, y);
        if (c instanceof PathCell) {
            return ((PathCell) c).direction;
        }
        return null;
    }

    /**
     * @param cds the specified coordinates
     * @return the direction of the cell located at the specified coordinates if
     *         it is a <code>PathCell</code>, <code>null</code> otherwise
     */
    public Direction getDirection(Coordinate cds) {
        return getDirection((int) Math.round(cds.x), (int) Math.round(cds.y));
    }

    /**
     * @param x the x coordinate
     * @param y the y coordinate
     * @return <code>true</code> if the cell at the specified coordinates is a
     *         spawn point
     */
    public boolean isSpawn(int x, int y) {
        return spawnPoints.contains(new Coordinate(x, y));
    }
}
