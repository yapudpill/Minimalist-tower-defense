package src.controller;

import static src.util.Status.EXIT;
import static src.util.Status.PLAYING;

import java.util.TimerTask;
import java.util.function.Function;

import src.model.LvlModel;
import src.model.Tower;
import src.util.Coordinate;
import src.view.LvlView;

public class LvlControl extends GameControl {
    public final LvlView view;
    private final LvlModel model;

    public LvlControl(MainControl main, int lvl, String mapName) {
        super(main);
        model = new LvlModel(lvl, mapName);
        view = new LvlView(model, this);
    }

    @Override
    public void startGame() {
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                model.update(FRAME_RATE);
                view.repaint();
                if (model.status != PLAYING) {
                    cancel();
                    main.loadLvlEnd(model.status, model.stats, model.lvl, model.nbWaves, model.getLife());
                }
            }
        },
        0, FRAME_RATE);
    }

    @Override
    public void addTower(Coordinate pos) {
        Function<Coordinate, Tower> constr = view.shop.getSelection();
        if (constr != null) {
            model.addTower(constr.apply(pos));
        }
    }

    @Override
    public void exit() {
        model.status = EXIT;
    }
}
