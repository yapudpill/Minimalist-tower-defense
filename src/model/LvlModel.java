package src.model;

import static src.util.Direction.END_OF_PATH;
import static src.util.Status.DEFEAT;
import static src.util.Status.PLAYING;
import static src.util.Status.VICTORY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import src.util.Coordinate;
import src.util.Direction;
import src.util.Status;

public class LvlModel {

    /**
     * An array of two integers.
     * <p>
     * To spawn an enemy, we choose an integer between 0 and 99. If it is
     * below the first number, we spawn a BasicEnemy. Between the two numbers,
     * we spawn a FastEnemy. And above the second, we spawn a TankEnemy.
     */
    public final Grid grid;
    public final int nbWaves, lvl;
    public final GameStats stats;
    private final ArrayList<int[]> scenario;
    private final int waveInterval;
    private int life, gold, nextWaveTime;
    private boolean spawning;
    public Status status;

    public LvlModel(int lvl, String mapName) {
        this.lvl = lvl;

        InputStream in = getClass().getResourceAsStream("/src/resources/" + mapName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        grid = new Grid(reader);

        scenario = new ArrayList<>();
        int totalWaveTmp = 0;
        int waveIntervalTmp = 0;
        try {
            int[] scenarioHeader = Arrays.stream(reader.readLine().split(" "))
                                         .mapToInt(Integer::valueOf)
                                         .toArray();
            totalWaveTmp = scenarioHeader[0];
            waveIntervalTmp = scenarioHeader[1];
            for (int i = 0; i < totalWaveTmp; i++) {
                scenario.add(Arrays.stream(reader.readLine().split(" "))
                                   .mapToInt(Integer::valueOf)
                                   .toArray());
            }
        } catch (IOException e) {
            System.exit(1);
        }
        nbWaves = totalWaveTmp;
        waveInterval = waveIntervalTmp;

        stats = new GameStats();
        nextWaveTime = 3000; // Wait 3 seconds before the first wave
        life = 3;
        gold = 120;
        spawning = false;
        status = PLAYING;
    }

    public void update(int frameRate) {
        if (!spawning && stats.waveCount < nbWaves) {
            spawnEnemies(frameRate);
        }
        updateEnemies(frameRate);
        towerShoot(frameRate);
        spawning = grid.updateEnemiesList(frameRate, stats);
        if (stats.waveCount == nbWaves && grid.enemies.isEmpty()) {
            status = VICTORY;
        }
    }

    private void spawnEnemies(int frameRate) {
        nextWaveTime -= frameRate;
        if (nextWaveTime <= 0) {
            int[] toSpawn = scenario.get(stats.waveCount);
            var constructors = new ArrayList<TriFunction<Coordinate, Direction, Integer, Enemy>>();
            for (int i = 0; i < toSpawn[0]; i++) {
                constructors.add(BasicEnemy::new);
            }
            for (int i = 0; i < toSpawn[1]; i++) {
                constructors.add(FastEnemy::new);
            }
            for (int i = 0; i < toSpawn[2]; i++) {
                constructors.add(TankEnemy::new);
            }
            Collections.shuffle(constructors);
            grid.spawnEnemies(constructors);

            nextWaveTime = waveInterval;
            stats.waveCount++;
        }
    }

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

    private void towerShoot(int frameRate) {
        for (TowerCell towerCell : grid.towerCells) {
            int reward = towerCell.update(frameRate, grid.enemies);
            gold += reward;
            stats.earnedGold += reward;
        }
    }

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
