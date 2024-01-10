package src.model;

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
