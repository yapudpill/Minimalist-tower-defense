package view.menus;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.MainControl;
import model.GameStats;
import view.Palette;

public abstract class EndMenu extends JPanel {
    JPanel header;

    public EndMenu(MainControl controller, GameStats stats, Runnable menuLoader) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;
        constraints.weighty = 1;

        // Header (row 1)
        constraints.gridy = 0;
        header = new JPanel();
        add(header, constraints);

        // Statistics label (row 2)
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(new StatsPanel(stats), constraints);

        // Menu button (row 3)
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.ipadx = 100;
        JButton menu = new JButton("Back to menu");
        menu.setFont(Palette.PLAIN_SANS);
        menu.addActionListener(e -> menuLoader.run());
        add(menu, constraints);
    }

    abstract void fillHeader();
}
