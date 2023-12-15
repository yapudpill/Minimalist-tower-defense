package src.view;

import javax.swing.JPanel;

import src.util.Palette;

/**
 * A <code>JPanel</code> that displays an empty cell.
 */
public class CellView extends JPanel {

    /**
     * Creates a new empty cell view. Sets the background color to
     * <code>Palette.BACKGROUND</code>
     */
    public CellView() {
        setBackground(Palette.BACKGROUND);
    }
}
