package view.menus;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameStats;
import view.Palette;

public class StatsPanel extends JPanel {

    public StatsPanel(GameStats stats) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        // Statistics label (row 1)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        JLabel statistics = new JLabel("Statistics:");
        statistics.setFont(Palette.PLAIN_SANS_BIG);
        statistics.setHorizontalAlignment(JLabel.CENTER);
        add(statistics, constraints);
        constraints.gridwidth = 1;

        // Enemy statistics (row 2)
        constraints.gridy = 1;
        constraints.gridx = 0;
        JLabel enemies = new JLabel("Enemy killed:");
        enemies.setFont(Palette.PLAIN_SANS);
        enemies.setHorizontalAlignment(JLabel.CENTER);
        add(enemies, constraints);

        constraints.gridx = 1;
        JLabel basicEnemies = new JLabel("Basic: " + stats.basicKilled);
        basicEnemies.setFont(Palette.PLAIN_SANS);
        add(basicEnemies, constraints);

        constraints.gridx = 2;
        JLabel fastEnemies = new JLabel("Fast: " + stats.fastKilled);
        fastEnemies.setFont(Palette.PLAIN_SANS);
        add(fastEnemies, constraints);

        constraints.gridx = 3;
        JLabel tankEnemies = new JLabel("Tank: " + stats.tankKilled);
        tankEnemies.setFont(Palette.PLAIN_SANS);
        add(tankEnemies, constraints);

        // Tower statistics (row 3)
        constraints.gridy = 2;
        constraints.gridx = 0;
        JLabel towers = new JLabel("Tower placed:");
        towers.setFont(Palette.PLAIN_SANS);
        towers.setHorizontalAlignment(JLabel.CENTER);
        add(towers, constraints);

        constraints.gridx = 1;
        JLabel basicTowers = new JLabel("Basic: " + stats.basicPlaced);
        basicTowers.setFont(Palette.PLAIN_SANS);
        add(basicTowers, constraints);

        constraints.gridx = 2;
        JLabel canonTowers = new JLabel("Canon: " + stats.canonPlaced);
        canonTowers.setFont(Palette.PLAIN_SANS);
        add(canonTowers, constraints);

        constraints.gridx = 3;
        JLabel sniperTowers = new JLabel("Sniper: " + stats.sniperPlaced);
        sniperTowers.setFont(Palette.PLAIN_SANS);
        add(sniperTowers, constraints);

        // Gold statistics (row 4)
        constraints.gridy = 3;
        constraints.gridx = 0;
        JLabel gold = new JLabel("Gold:");
        gold.setFont(Palette.PLAIN_SANS);
        gold.setHorizontalAlignment(JLabel.CENTER);
        add(gold, constraints);

        constraints.gridx = 1;
        JLabel earned = new JLabel("Earned: " + stats.earnedGold);
        earned.setFont(Palette.PLAIN_SANS);
        add(earned, constraints);

        constraints.gridx = 2;
        JLabel spent = new JLabel("Spent: " + stats.spentGold);
        spent.setFont(Palette.PLAIN_SANS);
        add(spent, constraints);
    }
}
