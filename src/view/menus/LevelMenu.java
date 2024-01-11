package view.menus;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainControl;
import model.GlobalModel;
import view.Palette;

public class LevelMenu extends JPanel {

    public LevelMenu(MainControl control, GlobalModel infos) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        // Menu title (row 0)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        JLabel title = new JLabel("LEVEL SELECTION");
        title.setFont(Palette.PLAIN_SANS_BIG);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, constraints);
        constraints.gridwidth = 1;

        // Level buttons
        InputStream in = MarathonMenu.class.getResourceAsStream("/resources/level_index");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String[] maps = reader.lines().toArray(String[]::new);

        for (int i = 0; i < maps.length; i++) {
            int n = i; // Stupid but needed for the lambda expression

            int lastUnlockedLvl = infos.levelCompletion.size();
            String mapName = maps[n];
            String text = String.format("<html>Level %d<br>%s", n+1, mapName);
            if (n < lastUnlockedLvl) {
                int stars = infos.levelCompletion.get(n);
                text += "<br>" + "★".repeat(stars) + "☆".repeat(3-stars);
            }

            JButton lvl = new JButton(text);
            lvl.setFont(Palette.PLAIN_SANS);
            lvl.addActionListener(e -> control.loadLevelGame(n, mapName));
            if (n > lastUnlockedLvl) {
                lvl.setEnabled(false);
            }

            constraints.gridy = n / 4 + 1;
            constraints.gridx = n % 4;
            add(lvl, constraints);
        }

        // Back button
        constraints.gridy++;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        JButton back = new JButton("Back");
        back.setFont(Palette.PLAIN_SANS);
        back.addActionListener(e -> control.loadStartMenu());
        add(back, constraints);
    }
}
