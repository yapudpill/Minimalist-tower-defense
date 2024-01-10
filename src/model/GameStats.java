package src.model;

import src.model.enemies.BasicEnemy;
import src.model.enemies.Enemy;
import src.model.enemies.FastEnemy;
import src.model.enemies.TankEnemy;
import src.model.towers.BasicTower;
import src.model.towers.CanonTower;
import src.model.towers.SniperTower;
import src.model.towers.Tower;

public class GameStats {
    public int basicKilled, fastKilled, tankKilled, basicPlaced, canonPlaced, sniperPlaced, earnedGold, spentGold, waveCount;

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
