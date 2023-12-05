package src.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.controller.MainControl;

public class StartMenu extends JPanel {

    public StartMenu(MainControl controller) {
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
        constraints.gridwidth = 2;
        JLabel title = new JLabel("TITLE");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, constraints);
        constraints.gridwidth = 1;

        constraints.gridy = 1;
        add(new JButton("Levels"), constraints);
        constraints.gridy = 2;
        add(new JButton("Score"), constraints);
        constraints.gridy = 3;
        add(new JButton("Settings"), constraints);

        // Second column
        constraints.gridx = 1;

        constraints.gridy = 1;
        JButton marathon = new JButton("Marathon");
        marathon.addActionListener(e -> controller.loadMarathonMenu());
        add(marathon, constraints);

        constraints.gridy = 2;
        add(new JButton("Shop"), constraints);

        constraints.gridy = 3;
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> controller.closeWindow());
        add(exit, constraints);
    }
}
