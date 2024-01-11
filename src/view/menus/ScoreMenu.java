package src.view.menus;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.controller.MainControl;
import src.model.GameStats;
import src.view.Palette;

public class ScoreMenu extends JPanel {
    private static final String EASY_PANEL = "easy";
    private static final String MEDIUM_PANEL = "medium";
    private static final String HARD_PANEL = "hard";
    private static final String STATS_PANEL = "stats";

    private final CardLayout layout;
    private final JPanel cards;

    public ScoreMenu(MainControl control, ArrayList<GameStats>[] stats) {
        setLayout(new BorderLayout());

        layout = new CardLayout();
        cards = new JPanel(layout);
        cards.add(makeMenu(stats[0]), EASY_PANEL);
        cards.add(makeMenu(stats[1]), MEDIUM_PANEL);
        cards.add(makeMenu(stats[2]), HARD_PANEL);
        add(cards, BorderLayout.CENTER);


        JButton easy = new JButton("Easy");
        easy.setFont(Palette.PLAIN_SANS);
        easy.addActionListener(e -> layout.show(cards, EASY_PANEL));

        JButton medium = new JButton("Medium");
        medium.setFont(Palette.PLAIN_SANS);
        medium.addActionListener(e -> layout.show(cards, MEDIUM_PANEL));

        JButton hard = new JButton("Hard");
        hard.setFont(Palette.PLAIN_SANS);
        hard.addActionListener(e -> layout.show(cards, HARD_PANEL));

        JPanel tabButtons = new JPanel();
        tabButtons.add(easy);
        tabButtons.add(medium);
        tabButtons.add(hard);
        add(tabButtons, BorderLayout.NORTH);


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
                cards.add(new StatsPanel(gs), STATS_PANEL);
                layout.show(cards, STATS_PANEL);
            });
            menu.add(detail, constraints);
        }
        return menu;
    }
}
