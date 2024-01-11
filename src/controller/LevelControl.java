package controller;

import model.LevelModel;

public class LevelControl extends GameControl {

    /**
     * Creates a new controller for the level game mode.
     *
     * @param main    the main controller that created this object
     * @param level   the number of the level to load
     * @param mapName the name of the file in which the map to load is saved
     */
    public LevelControl(MainControl main, int level, String mapName) {
        super(main, new LevelModel(level, mapName));
    }

    @Override
    void callEndScreen() {
        LevelModel mod = (LevelModel) model;
        main.loadLevelEnd(mod.status, mod.stats, mod.lvl, mod.nbWaves, model.getLife());
    }
}
