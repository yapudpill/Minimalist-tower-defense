package view.menus;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import controller.MainControl;
import model.GameStats;
import view.Palette;

public class ScoreMenu extends JPanel {
    private final JTabbedPane tabs;

    public ScoreMenu(MainControl control, ArrayList<GameStats>[] stats) {
        setLayout(new BorderLayout());

        tabs = new JTabbedPane();
        tabs.addTab("Easy", makeMenu(stats[0]));
        tabs.addTab("Medium", makeMenu(stats[1]));
        tabs.addTab("Hard", makeMenu(stats[2]));
        tabs.addChangeListener(e -> {
            if (tabs.getTabCount() == 4 && tabs.getSelectedIndex() != 3) {
                tabs.remove(3);
            }
        });
        tabs.setFont(Palette.PLAIN_SANS);
        add(tabs, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.setFont(Palette.PLAIN_SANS);
        back.addActionListener(e -> control.loadStartMenu());
        add(back, BorderLayout.SOUTH);
    }

    private JPanel makeMenu(ArrayList<GameStats> stats) {
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;

        for (int i = 0; i < stats.size(); i++) {
            GameStats gs = stats.get(i);
            constraints.gridy = i;

            constraints.gridx = 0;
            JLabel gameNo = new JLabel("Game n°" + gs.gameNo);
            gameNo.setFont(Palette.PLAIN_SANS);
            menu.add(gameNo, constraints);

            constraints.gridx = 1;
            JLabel mapName = new JLabel("Map: " + gs.mapName);
            mapName.setFont(Palette.PLAIN_SANS);
            menu.add(mapName, constraints);

            constraints.gridx = 2;
            JLabel waveCount = new JLabel("Survived " + gs.waveCount + " waves");
            waveCount.setFont(Palette.PLAIN_SANS);
            menu.add(waveCount, constraints);

            constraints.gridx = 3;
            JButton detail = new JButton("Detail");
            detail.setFont(Palette.PLAIN_SANS);
            detail.addActionListener(e -> {
                tabs.addTab("Details", new StatsPanel(gs));
                tabs.setSelectedIndex(3);
            });
            menu.add(detail, constraints);
        }
        return menu;
    }
}
