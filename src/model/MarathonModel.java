package src.model;

import static src.util.Direction.END_OF_PATH;
import static src.util.Status.DEFEAT;
import static src.util.Status.PLAYING;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

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
    private final GameStats stats;
    private int life, gold, nextWaveTime, waveCount;
    private boolean spawning;
    public Status status;

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
            case EASY:   waveInterval = 12000; spawnRef[0] = 50; spawnRef[1] = 75; break;
            case MEDIUM: waveInterval = 10000; spawnRef[0] = 45; spawnRef[1] = 75; break;
            default:     waveInterval = 8000;  spawnRef[0] = 35; spawnRef[1] = 60; break;
        }
        stats = new GameStats();
        nextWaveTime = 3000; // Wait 3 seconds before the first wave
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
        spawning = grid.updateEnemiesList(frameRate, stats);
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

            grid.spawnEnemies(waveCount, (cds, dir, remain) -> {
                int random = rnd.nextInt(100);
                if (random < spawnRef[0]) {
                    return new BasicEnemy(cds, dir, remain);
                } else if (random < spawnRef[1]) {
                    return new FastEnemy(cds, dir, remain);
                } else {
                    return new TankEnemy(cds, dir, remain);
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
            if (enemy.changedCell()) {
                enemy.setDirection(grid.getDirection(enemy.pos));
            }
            if (enemy.getDirection() == END_OF_PATH) {
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
            int reward = towerCell.update(frameRate, grid.enemies);
            gold += reward;
            stats.earnedGold += reward;
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
    public void addTower(Tower tower) {
        if (gold >= tower.cost && grid.getCell(tower.pos) instanceof TowerCell) {
            TowerCell tc = (TowerCell) grid.getCell(tower.pos);
            Tower existingTower = tc.tower;
            if (existingTower == null || existingTower.getClass() != tower.getClass()) {
                tc.tower = tower;
                gold -= tower.cost;
                stats.spentGold += tower.cost;
                stats.towerPlaced(tower);
            }
        }
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

    public GameStats getStats() {
        return stats;
    }
}
