package src.controller;

import src.model.MarathonModel;
import src.util.Difficulty;

/**
 * Controller for a marathon type game. Receives any inputs concerning the game
 * and modifies the model and view in consequence.
 */
public class MarathonControl extends GameControl {

    /**
     * Creates a new controller for the marathon game mode.
     *
     * @param main     the main controller that created this object
     * @param diff     the difficulty of the game being loaded
     * @param mapName  the name of the file in which the map to load is saved
     */
    public MarathonControl(MainControl main, Difficulty diff, String mapName) {
        super(main, new MarathonModel(diff, mapName));
    }

    @Override
    void callEndScreen() {
        MarathonModel m = (MarathonModel) model;
        main.loadMarathonEnd(m.status, m.stats, m.diff);
    }
}
