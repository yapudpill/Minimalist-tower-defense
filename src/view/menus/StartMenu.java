package view.menus;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainControl;
import view.Palette;

/**
 * The main menu of this project. Here the user can chose to go to any other
 * menu, for example the game launcher or the score panel.
 */
public class StartMenu extends JPanel {

    /**
     * Creates a new <code>StartMenu</code> that sends inputs to the specified
     * controller.
     *
     * @param controller the controller in charge of switching menus
     */
    public StartMenu(MainControl controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        // First column
        constraints.gridx = 0;

        constraints.gridy = 0;
        constraints.gridwidth = 2;
        JLabel title = new JLabel("MINIMALIST TOWER DEFENSE");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(Palette.PLAIN_SANS_BIG);
        add(title, constraints);
        constraints.gridwidth = 1;

        constraints.gridy = 1;
        JButton levels = new JButton("Levels");
        levels.setFont(Palette.PLAIN_SANS);
        levels.addActionListener(e -> controller.loadLevelMenu());
        add(levels, constraints);

        constraints.gridy = 2;
        JButton scores = new JButton("Scores");
        scores.setFont(Palette.PLAIN_SANS);
        scores.addActionListener(e -> controller.loadScoresMenu());
        add(scores, constraints);

        // Second column
        constraints.gridx = 1;

        constraints.gridy = 1;
        JButton marathon = new JButton("Marathon");
        marathon.setFont(Palette.PLAIN_SANS);
        marathon.addActionListener(e -> controller.loadMarathonMenu());
        add(marathon, constraints);

        constraints.gridy = 2;
        JButton exit = new JButton("Exit");
        exit.setFont(Palette.PLAIN_SANS);
        exit.addActionListener(e -> controller.closeWindow());
        add(exit, constraints);
    }
}
