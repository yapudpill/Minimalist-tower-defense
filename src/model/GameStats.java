package model;

import model.enemies.BasicEnemy;
import model.enemies.Enemy;
import model.enemies.FastEnemy;
import model.enemies.TankEnemy;
import model.towers.BasicTower;
import model.towers.CanonTower;
import model.towers.SniperTower;
import model.towers.Tower;

public class GameStats {
    public static int nextGameNo = 1;

    public final int gameNo;
    public final String mapName;
    public int basicKilled, fastKilled, tankKilled, basicPlaced, canonPlaced, sniperPlaced, earnedGold, spentGold, waveCount;

    public GameStats(String mapName) {
        this.mapName = mapName;
        gameNo = nextGameNo;
        nextGameNo++;
    }

    /**
     * Adds 1 to the counter of the tower type.
     *
     * @param tower the tower to count
     */
    public void towerPlaced(Tower tower) {
        if (tower instanceof BasicTower) {
            basicPlaced++;
        } else if (tower instanceof CanonTower) {
            canonPlaced++;
        } else if (tower instanceof SniperTower) {
            sniperPlaced++;
        }
    }

    /**
     * Adds 1 to the counter of the enemy type.
     *
     * @param enemy the enemy to count
     */
    public void enemyDied(Enemy enemy) {
        if (enemy instanceof BasicEnemy) {
            basicKilled++;
        } else if (enemy instanceof FastEnemy) {
            fastKilled++;
        } else if (enemy instanceof TankEnemy) {
            tankKilled++;
        }
    }

    /**
     * @return the number of wave since the beginning of the game
     */
    public int getWaveCount() {
        return waveCount;
    }
}
