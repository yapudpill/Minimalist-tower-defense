package src.controller;

import static src.util.Status.PLAYING;

import java.util.Timer;
import java.util.TimerTask;

import src.model.MarathonModel;
import src.model.Tower;
import src.util.Coordinate;
import src.util.Difficulty;
import src.view.MarathonView;

/**
 * Controller for a marathon type game. Receives any inputs concerning the game
 * and modifies the model and view in consequence.
 */
public class MarathonControl {

    /**
     * Time between two updates in milliseconds. 17 ms approximately corresponds
     * to 60 updates per second.
     */
    private static final int FRAME_RATE = 17;

    public final MarathonView view;
    private final MarathonModel model;
    private final Timer updateTimer;
    private final MainControl main;

    /**
     * Creates a new controller for the marathon game mode.
     *
     * <p>
     * Internally, it creates a new <code>MarathonModel</code> and a new
     * <code>MarathonView</code>.
     *
     * @param main the main controller that created this
     *             <code>MarathonControl</code> object
     * @param diff the difficulty of the game being loaded
     * @param map  the name of the file in which the map to load is saved
     */
    public MarathonControl(MainControl main, Difficulty diff, String mapName) {
        this.main = main;
        model = new MarathonModel(diff, mapName);
        view = new MarathonView(model, this);
        updateTimer = new Timer();
    }

    /**
     * Starts an infinite loop in the <code>updateTimer</code> of this object.
     * The loop calls repeatedly <code>MarathonModel.update</code> and
     * <code>MarathonView.repaint</code>. The loop is exited if the
     * <code>status</code> of the model different from <code>PLAYING</code> and
     * the end screen is shown.
     *
     * @see MarathonModel#update(int)
     * @see MainControl#loadMarathonEnd()
     */
    public void startGame() {
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                model.update(FRAME_RATE);
                view.repaint();
                if (model.getStatus() != PLAYING) {
                    cancel();
                    main.loadMarathonEnd();
                }
            }
        },
                0, FRAME_RATE);
    }

    /**
     * Adds a tower to <code>model</code> at the specified position. The type
     * of the tower is determined by the selected button in <code>view.shop</code>
     *
     * @param pos the position where to add a tower
     */
    public void addTower(Coordinate pos) {
        Tower tower = view.shop.getSelection();
        if (tower != null) {
            model.addTower(pos, tower);
        }
    }
}
