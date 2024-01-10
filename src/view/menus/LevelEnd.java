package src.view.menus;

import java.awt.GridLayout;

import javax.swing.JLabel;

import src.controller.MainControl;
import src.model.GameStats;
import src.view.Palette;

public class LevelEnd extends EndMenu {
    private final GameStats stats;
    private final int nbWaves, stars;
    private final  boolean victory;

    public LevelEnd(MainControl controller, GameStats stats, int nbWaves, int stars, boolean victory) {
        super(controller, stats, controller::loadLevelMenu);
        this.stats = stats;
        this.nbWaves = nbWaves;
        this.stars = stars;
        this.victory = victory;

        fillHeader();
    }

    @Override
    void fillHeader() {
        header.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel(victory ? "Victory!" : "Game Over");
        title.setFont(Palette.PLAIN_SANS_BIG);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        header.add(title);

        String text;
        if (victory) {
            text = "★".repeat(stars) + "☆".repeat(3-stars);
        } else {
            text = "You survived " + stats.waveCount + " waves out of " + nbWaves;
        }
        JLabel wave = new JLabel(text);
        wave.setFont(Palette.PLAIN_SANS);
        wave.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.TOP);
        header.add(wave);
    }
}
