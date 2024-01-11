package view.menus;

import java.awt.GridLayout;

import javax.swing.JLabel;

import controller.MainControl;
import model.GameStats;
import view.Palette;

public class MarathonEnd extends EndMenu {
    private final GameStats stats;

    public MarathonEnd(MainControl controller, GameStats stats) {
        super(controller, stats, controller::loadMarathonMenu);
        this.stats = stats;

        fillHeader();
    }

    @Override
    void fillHeader() {
        header.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel("Game Over");
        title.setFont(Palette.PLAIN_SANS_BIG);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.BOTTOM);
        header.add(title);

        JLabel wave = new JLabel("You survived " + stats.waveCount + " waves!");
        wave.setFont(Palette.PLAIN_SANS);
        wave.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.TOP);
        header.add(wave);
    }
}
