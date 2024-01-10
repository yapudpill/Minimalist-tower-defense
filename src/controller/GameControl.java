package src.controller;

import java.util.Timer;

import src.util.Coordinate;

public abstract class GameControl {

    /**
     * Time between two updates in milliseconds. 17 ms approximately corresponds
     * to 60 updates per second.
     */
    static final int FRAME_RATE = 17;

    final Timer updateTimer;
    final MainControl main;

    GameControl(MainControl main) {
        this.main = main;
        this.updateTimer = new Timer();
    }

    abstract void startGame();

    abstract public void addTower(Coordinate pos);

    abstract public void exit();
}
