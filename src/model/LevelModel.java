package model;

import static util.Status.VICTORY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.enemies.BasicEnemy;
import model.enemies.Enemy;
import model.enemies.FastEnemy;
import model.enemies.TankEnemy;
import util.Coordinate;
import util.Direction;
import util.TriFunction;

public class LevelModel extends GameModel {

    public final int nbWaves, lvl;
    private final ArrayList<int[]> scenario;

    public LevelModel(int lvl, String mapName) {
        super(mapName);
        this.lvl = lvl;

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
     * <p>- If on the final wave and no more enemies on grid, chance state to VICTORY.
     *
     * @param frameRate the time since the last update, in milliseconds
     */
    @Override
    public void update(int frameRate) {
        if (!spawning && stats.waveCount < nbWaves) {
            spawnEnemies(frameRate);
        }
        updateEnemies(frameRate);
        towerShoot(frameRate);
        spawning = grid.updateEnemiesList(frameRate, stats);
        if (!spawning && stats.waveCount == nbWaves && grid.enemies.isEmpty()) {
            status = VICTORY;
        }
    }

    /**
     * Keeps track of the cooldown before the next wave. When it reaches 0, spawns a
     * wave according to the scenario.
     *
     * @param frameRate the time since the last update, in milliseconds
     */
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
}
