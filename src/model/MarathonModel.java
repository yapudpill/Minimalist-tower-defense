package src.model;

import static src.util.Direction.END_OF_PATH;
import static src.util.Status.DEFEAT;
import static src.util.Status.PLAYING;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import src.util.Coordinate;
import src.util.Difficulty;
import src.util.Status;

/**
 * The model of a marathon game. It contains all the information about a game
 * played in marathon mode.
 */
public class MarathonModel {
    private static final Random rnd = new Random();

    /**
     * An array of two integers.
     * <p>
     * To spawn an enemy, we choose an integer between 0 and 99. If it is
     * below the first number, we spawn a BasicEnemy. Between the two numbers,
     * we spawn a FastEnemy. And above the second, we spawn a TankEnemy.
     */
    public final Grid grid;
    private final int[] spawnRef;
    private final int waveInterval;
    private int life, gold, nextWaveTime, waveCount;
    private boolean spawning;
    private Status status;

    /**
     * Creates a new marathon game model with the specified map and difficulty.
     *
     * @param diff the difficulty of the game being loaded
     * @param map  the name of the file in which the map to load is saved
     */
    public MarathonModel(Difficulty diff, String mapName) {
        InputStream in = getClass().getResourceAsStream("/src/resources/maps/" + mapName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        grid = new Grid(reader);

        spawnRef = new int[2];
        switch (diff) {
            case EASY:   waveInterval = 15000; spawnRef[0] = 50; spawnRef[1] = 75; break;
            case MEDIUM: waveInterval = 12000; spawnRef[0] = 45; spawnRef[1] = 75; break;
            default:     waveInterval = 10000; spawnRef[0] = 35; spawnRef[1] = 60; break;
        }
        nextWaveTime = 5000; // Wait 5 seconds before the first wave
        life = 3;
        gold = 100;
        waveCount = 0;
        spawning = false;
        status = PLAYING;
    }

    /**
     * Updates the whole model. To do so, the following operations are done in
     * order:
     *
     * <p>- If no more enemies are to be spawn, try to spawn new ones
     * <p>- Update the position of all the existing enemies
     * <p>- Make the tower shoot
     * <p>- Clear the enemy list to remove any enemy that is dead or reached the
     * end of path
     *
     * @param frameRate the time since the last update, in milliseconds
     */
    public void update(int frameRate) {
        if (!spawning) {
            spawnEnemies(frameRate);
        }
        updateEnemies(frameRate);
        towerShoot(frameRate);
        spawning = grid.updateEnemiesList(frameRate);
    }

    /**
     * Keeps track of the cooldown before the next wave. When it reaches 0, spawns a
     * wave.
     *
     * @param frameRate the time since the last update, in milliseconds
     */
    private void spawnEnemies(int frameRate) {
        nextWaveTime -= frameRate;
        if (nextWaveTime <= 0) {
            nextWaveTime = waveInterval;
            waveCount++;

            grid.spawnEnemies(waveCount, (cds, dir) -> {
                int random = rnd.nextInt(100);
                if (random < spawnRef[0]) {
                    return new BasicEnemy(cds, dir);
                } else if (random < spawnRef[1]) {
                    return new FastEnemy(cds, dir);
                } else {
                    return new TankEnemy(cds, dir);
                }
            });
        }
    }

    /**
     * Updates the position of every enemy on the grid and make them turn if
     * necessary. If an enemy reaches the end of the path, <code>life</code> is
     * decremented by 1.
     *
     * @param frameRate the time since the last update, in milliseconds
     */
    private void updateEnemies(int frameRate) {
        for (Enemy enemy : grid.enemies) {
            enemy.update(frameRate);
            if (enemy.needToTurn()) {
                enemy.direction = grid.getDirection(enemy.pos);
            }
            if (enemy.direction == END_OF_PATH) {
                life--;
                if (life == 0) {
                    status = DEFEAT;
                    return;
                }
            }
        }
    }

    /**
     * Make every tower on the grid try to shoot. Collects the earned gold.
     *
     * @param frameRate the time since the last update, in milliseconds
     */
    private void towerShoot(int frameRate) {
        for (TowerCell towerCell : grid.towerCells) {
            gold += towerCell.shoot(frameRate, grid.enemies);
        }
    }

    /**
     * Adds the specified tower to the grid at the specified location. This
     * operation if done if the player has enough gold and if the specified cell
     * does not already contains the same tower.
     *
     * @param pos   the position where to place the tower
     * @param tower the tower to add to the grid
     */
    public void addTower(Coordinate pos, Tower tower) {
        if (gold >= tower.cost && grid.getCell(pos) instanceof TowerCell) {
            TowerCell tc = (TowerCell) grid.getCell(pos);
            Tower existingTower = tc.getTower();
            if (existingTower == null || existingTower.getClass() != tower.getClass()) {
                tc.setTower(tower);
                gold -= tower.cost;
            }
        }
    }

    /**
     * @return this game status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return the number of health points the player has
     */
    public int getLife() {
        return life;
    }

    /**
     * @return the number of gold the player has
     */
    public int getGold() {
        return gold;
    }

    /**
     * @return the number of waves since the beginning of this game
     */
    public int getWaveCount() {
        return waveCount;
    }
}
