package view.menus;

import static util.Difficulty.EASY;
import static util.Difficulty.HARD;
import static util.Difficulty.MEDIUM;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.MainControl;
import view.Palette;
import view.menus.DifficultyRadioButton.DifficultyButtonModel;

/**
 * The selection menu to launch a new marathon game. This menu make the user
 * select the difficulty and the map.
 */
public class MarathonMenu extends JPanel {
    private final MainControl controller;
    private final ButtonGroup difficultyButtons;
    private final JComboBox<String> mapBox;
    private final JButton start;

    /**
     * Creates a new <code>MarathonMenu</code>, filling it with difficulty
     * selection buttons and a dynamically generated map selector.
     */
    public MarathonMenu(MainControl control) {
        controller = control;
        difficultyButtons = new ButtonGroup();

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
        JLabel title = new JLabel("MARATHON");
        title.setFont(Palette.PLAIN_SANS_BIG);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, constraints);
        constraints.gridwidth = 1;

        // Difficulty selection (row 1)
        constraints.gridy = 1;

        constraints.gridx = 0;
        JLabel difficulty = new JLabel("Difficulty");
        difficulty.setFont(Palette.PLAIN_SANS);
        add(difficulty, constraints);

        constraints.gridx = 1;
        JRadioButton easy = new DifficultyRadioButton(EASY, difficultyButtons);
        easy.addActionListener(this::enableStart);
        add(easy , constraints);

        constraints.gridx = 2;
        JRadioButton medium = new DifficultyRadioButton(MEDIUM, difficultyButtons);
        medium.addActionListener(this::enableStart);
        add(medium, constraints);

        constraints.gridx = 3;
        JRadioButton hard = new DifficultyRadioButton(HARD, difficultyButtons);
        hard.addActionListener(this::enableStart);
        add(hard, constraints);

        // Map selection (row 2)
        constraints.gridy = 2;

        constraints.gridx = 0;
        JLabel map = new JLabel("Map");
        map.setFont(Palette.PLAIN_SANS);
        add(map, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        InputStream in = MarathonMenu.class.getResourceAsStream("/resources/marathon_index");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        mapBox = new JComboBox<>(reader.lines().toArray(String[]::new));
        mapBox.setFont(Palette.PLAIN_SANS);
        add(mapBox, constraints);
        constraints.gridwidth = 1;

        // Back and start buttons (row 3)
        constraints.gridy = 3;
        constraints.gridwidth = 2;

        constraints.gridx = 0;
        JButton back = new JButton("Back");
        back.setFont(Palette.PLAIN_SANS);
        back.addActionListener(e -> control.loadStartMenu());
        add(back, constraints);

        constraints.gridx = 2;
        start = new JButton("Start");
        start.setFont(Palette.PLAIN_SANS);
        start.setEnabled(false);
        start.addActionListener(this::askLoadMarathonGame);
        add(start, constraints);
        constraints.gridwidth = 1;
    }

    /**
     * Enables the button to start the game. This method is called when a
     * difficulty and a map are selected.
     */
    private void enableStart(ActionEvent e) {
        start.setEnabled(true);
    }

    /**
     * Ask de <code>MainControl</code> to load a new marathon game with the
     * selected parameters.
     */
    private void askLoadMarathonGame(ActionEvent e) {
        controller.loadMarathonGame(
            ((DifficultyButtonModel) difficultyButtons.getSelection()).difficulty,
            (String) mapBox.getSelectedItem());
    }
}
