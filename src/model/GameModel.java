package src.model;

import static src.util.Direction.END_OF_PATH;
import static src.util.Status.DEFEAT;
import static src.util.Status.PLAYING;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import src.model.cells.TowerCell;
import src.model.enemies.Enemy;
import src.model.towers.Tower;
import src.util.Status;

public abstract class GameModel {

    public final Grid grid;
    public final GameStats stats;
    public Status status;

    BufferedReader reader;
    int waveInterval;
    int life, gold, nextWaveTime;
    boolean spawning;

    public GameModel(String mapName) {
        InputStream in = getClass().getResourceAsStream("/src/resources/" + mapName);
        reader = new BufferedReader(new InputStreamReader(in));
        grid = new Grid(reader);
        stats = new GameStats();
        nextWaveTime = 3000; // Wait 3 seconds before the first wave
        life = 3;
        gold = 120;
        spawning = false;
        status = PLAYING;
    }

    abstract public void update(int frameRate);

    /**
     * Updates the position of every enemy on the grid and make them turn if
     * necessary. If an enemy reaches the end of the path, <code>life</code> is
     * decremented by 1.
     *
     * @param frameRate the time since the last update, in milliseconds
     */
    void updateEnemies(int frameRate) {
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
    void towerShoot(int frameRate) {
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
}
