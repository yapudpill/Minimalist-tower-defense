package src.view;

import static src.util.Difficulty.EASY;
import static src.util.Difficulty.HARD;
import static src.util.Difficulty.MEDIUM;

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

import src.controller.MainControl;
import src.view.DifficultyRadioButton.DifficultyButtonModel;

public class MarathonMenu extends JPanel {
    private final MainControl controller;
    private final ButtonGroup difficultyButtons;
    private final JComboBox<String> mapBox;
    private final JButton start;

    public MarathonMenu(MainControl control) {
        controller = control;
        difficultyButtons = new ButtonGroup();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        // First column
        constraints.gridx = 0;

        constraints.gridy = 0;
        constraints.gridwidth = 4;
        JLabel title = new JLabel("MARATHON");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, constraints);
        constraints.gridwidth = 1;

        constraints.gridy = 1;
        add(new JLabel("Difficulty"), constraints);
        constraints.gridy = 2;
        add(new JLabel("Map"), constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        JButton back = new JButton("Back");
        back.addActionListener(e -> control.loadStartMenu());
        add(back, constraints);
        constraints.gridwidth = 1;

        // Second column
        constraints.gridx = 1;

        constraints.gridy = 1;
        JRadioButton easy = new DifficultyRadioButton(EASY, difficultyButtons);
        easy.addActionListener(this::enableStart);
        add(easy , constraints);

        constraints.gridy = 2;
        constraints.gridwidth = 3;
        InputStream in = MarathonMenu.class.getResourceAsStream("/src/resources/maps/index");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        mapBox = new JComboBox<>(reader.lines().toArray(String[]::new));
        add(mapBox, constraints);
        constraints.gridwidth = 1;

        // Third column
        constraints.gridx = 2;

        constraints.gridy = 1;
        JRadioButton medium = new DifficultyRadioButton(MEDIUM, difficultyButtons);
        medium.addActionListener(this::enableStart);
        add(medium, constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        start = new JButton("Start");
        start.setEnabled(false);
        start.addActionListener(this::askLoadMarathonGame);
        add(start, constraints);
        constraints.gridwidth = 1;

        // Fourth column
        constraints.gridx = 3;
        constraints.gridy = 1;
        JRadioButton hard = new DifficultyRadioButton(HARD, difficultyButtons);
        hard.addActionListener(this::enableStart);
        add(hard, constraints);
    }

    private void enableStart(ActionEvent e) {
        start.setEnabled(true);
    }

    private void askLoadMarathonGame(ActionEvent e) {
        controller.loadMarathonGame(
            ((DifficultyButtonModel) difficultyButtons.getSelection()).difficulty,
            (String) mapBox.getSelectedItem());
    }
}
