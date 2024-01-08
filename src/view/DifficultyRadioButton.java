package src.view;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import src.util.Difficulty;

/**
 * A modified type of <code>JRadioButton</code> that encapsulates a difficulty
 * information.
 */
public class DifficultyRadioButton extends JRadioButton {

    /**
     * Creates a new <code>DifficultyRadioButton</code> in the specified group
     * that encapsulates the specified difficulty.
     *
     * @param diff  the difficulty that this button represents
     * @param group the group in which this button will be placed
     */
    public DifficultyRadioButton(Difficulty diff, ButtonGroup group) {
        setModel(new DifficultyButtonModel(diff, group));
        switch (diff) {
            case EASY: setText("Easy"); break;
            case MEDIUM: setText("Medium"); break;
            case HARD: setText("Hard"); break;
        }
    }

    /**
     * A modified type of <code>ToggleButtonModel</code> that encapsulates a
     * difficulty information.
     */
    public static class DifficultyButtonModel extends ToggleButtonModel {
        public final Difficulty difficulty;

        /**
         * Creates a new <code>DifficultyButtonModel</code> in the specified group
         * that encapsulates the specified difficulty
         *
         * @param diff  the difficulty that this button represents
         * @param group the group in which this button will be placed
         */
        public DifficultyButtonModel(Difficulty diff, ButtonGroup group) {
            setGroup(group);
            difficulty = diff;
        }
    }
}
