package model;

import java.util.ArrayList;
import java.util.Random;

import model.enemies.BasicEnemy;
import model.enemies.Enemy;
import model.enemies.FastEnemy;
import model.enemies.TankEnemy;
import util.Coordinate;
import util.Difficulty;
import util.Direction;
import util.TriFunction;

/**
 * The model of a marathon game. It contains all the information about a game
 * played in marathon mode.
 */
public class MarathonModel extends GameModel {
    private static final Random rnd = new Random();

    /**
     * An array of two integers.
     * <p>
     * To spawn an enemy, we choose an integer between 0 and 99. If it is
     * below the first number, we spawn a BasicEnemy. Between the two numbers,
     * we spawn a FastEnemy. And above the second, we spawn a TankEnemy.
     */
    private final int[] spawnRef;
    public final Difficulty diff;

    /**
     * Creates a new marathon game model with the specified map and difficulty.
     *
     * @param diff the difficulty of the game being loaded
     * @param map  the name of the file in which the map to load is saved
     */
    public MarathonModel(Difficulty diff, String mapName) {
        super(mapName);
        this.diff = diff;

        spawnRef = new int[2];
        switch (diff) {
            case EASY:   waveInterval = 11000; spawnRef[0] = 50; spawnRef[1] = 75; break;
            case MEDIUM: waveInterval = 9500; spawnRef[0] = 45; spawnRef[1] = 75; break;
            default:     waveInterval = 7500;  spawnRef[0] = 35; spawnRef[1] = 60; break;
        }
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
    @Override
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
            stats.waveCount++;

            var constructors = new ArrayList<TriFunction<Coordinate, Direction, Integer, Enemy>>();
            for (int i = 0; i < stats.waveCount; i++) {
                int random = rnd.nextInt(100);
                if (random < spawnRef[0]) {
                    constructors.add(BasicEnemy::new);
                } else if (random < spawnRef[1]) {
                    constructors.add(FastEnemy::new);
                } else {
                    constructors.add(TankEnemy::new);
                }
            }
            grid.spawnEnemies(constructors);

            if (stats.waveCount > 20 && waveInterval >= 1000) {
                waveInterval -= 1000;
            }
        }
    }
}
