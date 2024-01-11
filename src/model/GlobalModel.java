package model;

import java.util.ArrayList;

public class GlobalModel {
    public ArrayList<Integer> levelCompletion;
    public ArrayList<GameStats>[] stats;

    @SuppressWarnings("unchecked")
    public GlobalModel() {
        levelCompletion = new ArrayList<>();
        stats = new ArrayList[3];
        stats[0] = new ArrayList<>();
        stats[1] = new ArrayList<>();
        stats[2] = new ArrayList<>();
    }
}
