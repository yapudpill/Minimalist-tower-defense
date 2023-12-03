package controller;

import java.io.File;

import model.MarathonModel;
import util.Difficulty;
import view.MarathonView;

public class MarathonControl {
    private final MarathonModel model;
    public final MarathonView panel;

    public MarathonControl(MainControl main, File map, Difficulty diff) {
        model = new MarathonModel(map, diff);
        panel = new MarathonView(model, main, this);
    }
}
