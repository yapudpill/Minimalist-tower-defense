package src.controller;

import src.model.MarathonModel;
import src.util.Difficulty;
import src.view.MarathonView;

public class MarathonControl {
    private final MarathonModel model;
    public final MarathonView panel;

    public MarathonControl(MainControl main, Difficulty diff, String mapName) {
        model = new MarathonModel(diff, mapName);
        panel = new MarathonView(model, main, this);
    }
}
