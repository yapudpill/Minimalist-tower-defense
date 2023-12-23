package src.model;

import static src.util.Direction.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import src.util.Difficulty;

/**
 * The model of a marathon game. It contains all the information about a game
 * played in marathon mode.
 */
public class MarathonModel {
    public int startX, startY; // maybe should be private
    public final Cell[][] grid;

    /**
     * Creates a new marathon game model with the specified map and difficulty.
     *
     * @param diff - The difficulty of the game being loaded
     * @param map  - The name of the file in which the map to load is saved
     */
    public MarathonModel(Difficulty diff, String mapName) {
        Cell[][] g = null;
        try {
            g = readMap(mapName);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        grid = g;
    }

    /**
     * Converts a map file into an 2D array of <code>Cell</code>.
     *
     * @param map - The name of the file in which the map to load is saved
     * @return A 2D array of <code>Cell</code> objects representing the map.
     */
    private Cell[][] readMap(String mapName) throws IOException {
        InputStream in = MarathonModel.class.getResourceAsStream("/src/resources/maps/" + mapName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        int[] firstLine = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        Cell[][] grid = new Cell[firstLine[0]][firstLine[1]];
        startX = firstLine[2];
        startY = firstLine[3];

        String line = reader.readLine();
        for (int i = 0; line != null; i++, line = reader.readLine()) {
            char[] l = line.toCharArray();
            for (int j = 0; j < l.length; j++) {
                switch (l[j]) {
                    case ' ': grid[i][j] = new Cell(); break;
                    case 'H': grid[i][j] = new PathCell(UP,true);break;
                    case 'G': grid[i][j] = new PathCell(LEFT,true);break;
                    case 'B': grid[i][j] = new PathCell(DOWN,true);break;
                    case 'D': grid[i][j] = new PathCell(RIGHT,true);break;
                    case '^': grid[i][j] = new PathCell(UP,false); break;
                    case '<': grid[i][j] = new PathCell(LEFT,false); break;
                    case 'v': grid[i][j] = new PathCell(DOWN,false); break;
                    case '>': grid[i][j] = new PathCell(RIGHT,false); break;
                    case 'x': grid[i][j] = new PathCell(END_OF_PATH,false); break;
                    case 'T': grid[i][j] = new TowerCell(); break;
                }
            }
        }

        return grid;
    }
}
