package src.controller;

import static src.util.Status.EXIT;
import static src.util.Status.PLAYING;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import src.model.GameModel;
import src.model.towers.Tower;
import src.util.Coordinate;
import src.view.game.GameView;

public abstract class GameControl {

    /**
     * Time between two updates in milliseconds. 17 ms approximately corresponds
     * to 60 updates per second.
     */
    static final int FRAME_RATE = 17;

    public final GameView view;
    final GameModel model;
    final MainControl main;
    final Timer updateTimer;

    /**
     * Creates a new controller for a game.
     *
     * @param main  the main controller that created this object
     * @param model the model of the game to control
     */
    GameControl(MainControl main, GameModel model) {
        this.main = main;
        this.model = model;
        this.view = new GameView(model, this);
        this.updateTimer = new Timer();
    }

    /**
     * Starts an infinite loop in the <code>updateTimer</code> of this object.
     * The loop calls repeatedly <code>GameModel.update</code> and
     * <code>GameView.repaint</code>. The loop is exited if the
     * <code>status</code> of the model different from <code>PLAYING</code>.
     */
    public void startGame() {
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                model.update(FRAME_RATE);
                view.repaint();
                if (model.status != PLAYING) {
                    cancel();
                    callEndScreen();
                }
            }
        },
        0, FRAME_RATE);
    }

    /**
     * Calls the end screen function in the main controller of this game.
     */
    abstract void callEndScreen();

    /**
     * Adds a tower to <code>model</code> at the specified position. The type
     * of the tower is determined by the selected button in <code>view.shop</code>
     *
     * @param pos the position where to add a tower
     */
    public void addTower(Coordinate pos) {
        Function<Coordinate, Tower> constr = view.shop.getSelection();
        if (constr != null) {
            model.addTower(constr.apply(pos));
        }
    }

    /**
     * Sets the status of the model to EXIT so the game will be existed on the
     * next update loop.
     */
    public void exit() {
        model.status = EXIT;
    }
}
