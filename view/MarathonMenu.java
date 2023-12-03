package view;

import static util.Difficulty.EASY;
import static util.Difficulty.HARD;
import static util.Difficulty.MEDIUM;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.MainControl;
import view.DifficultyRadioButton.DifficultyButtonModel;

public class MarathonMenu extends JPanel {
    private final MainControl controller;
    private final ButtonGroup difficultyButtons;
    private final JComboBox<File> mapBox;
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
        mapBox = new JComboBox<>(control.mapDir.listFiles());
        mapBox.setRenderer(this::renderMapBoxElement);
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

    private Component renderMapBoxElement(JList <? extends File> list, File file, int index, boolean selected, boolean focus) {
        return new JLabel(file.getName());
    }

    private void askLoadMarathonGame(ActionEvent e) {
        controller.loadMarathonGame(
            ((DifficultyButtonModel) difficultyButtons.getSelection()).difficulty,
            (File) mapBox.getSelectedItem());
    }
}
