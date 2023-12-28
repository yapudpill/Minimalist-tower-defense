package src.controller;

import src.model.MarathonModel;
import src.util.Difficulty;
import src.view.MarathonView;

/**
 * Controller for a marathon type game. Receives any inputs concerning the game
 * and modifies the model and view in consequence.
 */
public class MarathonControl {
    private final MarathonModel model;
    public final MarathonView panel;

    /**
     * Creates a new controller for the marathon game mode.
     *
     * <p>
     * Internally, it creates a new <code>MarathonModel</code> and a new
     * <code>MarathonView</code>.
     * </p>
     *
     * @param main - The main controller than created this controller
     * @param diff - The difficulty of the game being loaded
     * @param map  - The name of the file in which the map to load is saved
     */
    public MarathonControl(MainControl main, Difficulty diff, String mapName) {
        model = new MarathonModel(diff, mapName);
        panel = new MarathonView(model, main, this);
    }


}
