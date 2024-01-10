package src.view.game;

import javax.swing.JPanel;

import src.view.Palette;

/**
 * A <code>JPanel</code> that displays an empty cell.
 */
public class EmptyCellView extends JPanel {

    /**
     * Creates a new empty cell view. Sets the background color to
     * <code>Palette.BACKGROUND</code>
     */
    public EmptyCellView() {
        setBackground(Palette.BACKGROUND);
    }
}
