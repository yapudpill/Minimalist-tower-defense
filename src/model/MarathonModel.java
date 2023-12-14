package src.model;

import static src.util.Direction.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import src.util.Difficulty;

public class MarathonModel {
    public int startX, startY; // maybe should be private
    public final Cell[][] grid;

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
                    case '^': grid[i][j] = new PathCell(UP); break;
                    case '<': grid[i][j] = new PathCell(LEFT); break;
                    case 'v': grid[i][j] = new PathCell(DOWN); break;
                    case '>': grid[i][j] = new PathCell(RIGHT); break;
                    case 'x': grid[i][j] = new PathCell(END_OF_PATH); break;
                    case 'T': grid[i][j] = new TowerCell(); break;
                }
            }
        }

        return grid;
    }
}
