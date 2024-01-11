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

    public void towerPlaced(Tower tower) {
        if (tower instanceof BasicTower) {
            basicPlaced++;
        } else if (tower instanceof CanonTower) {
            canonPlaced++;
        } else if (tower instanceof SniperTower) {
            sniperPlaced++;
        }
    }

    public void enemyDied(Enemy enemy) {
        if (enemy instanceof BasicEnemy) {
            basicKilled++;
        } else if (enemy instanceof FastEnemy) {
            fastKilled++;
        } else if (enemy instanceof TankEnemy) {
            tankKilled++;
        }
    }

    public int getWaveCount() {
        return waveCount;
    }
}
