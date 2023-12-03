package view;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import util.Difficulty;

public class DifficultyRadioButton extends JRadioButton {

    public DifficultyRadioButton(Difficulty diff, ButtonGroup group) {
        setModel(new DifficultyButtonModel(diff, group));
        switch (diff) {
            case EASY: setText("Easy"); break;
            case MEDIUM: setText("Medium"); break;
            case HARD: setText("Hard"); break;
        }
    }

    public static class DifficultyButtonModel extends ToggleButtonModel {
        public final Difficulty difficulty;

        public DifficultyButtonModel(Difficulty diff, ButtonGroup group) {
            setGroup(group);
            difficulty = diff;
        }
    }
}
