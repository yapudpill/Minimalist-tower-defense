package model;

import static util.Direction.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import util.Difficulty;

public class MarathonModel {
    public int startX, startY; // maybe should be private
    public final Cell[][] grid;

    public MarathonModel(File map, Difficulty diff) {
        grid = readMap(map);
    }

    private Cell[][] readMap(File map) {
        Scanner scan = null;
        try {
            scan = new Scanner(map);
        } catch (FileNotFoundException e) {
            /* Theoretically impossible to reach because this function is called
               with Files objects generated by File.listFiles() */
            e.printStackTrace();
            System.exit(1);
        }

        Cell[][] grid = new Cell[scan.nextInt()][scan.nextInt()];
        startX = scan.nextInt();
        startY = scan.nextInt();
        scan.nextLine();

        for (int i = 0; scan.hasNextLine(); i++) {
            char[] line = scan.nextLine().toCharArray();
            for (int j = 0; j < line.length; j++) {
                switch (line[j]) {
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

        scan.close();
        return grid;
    }
}
