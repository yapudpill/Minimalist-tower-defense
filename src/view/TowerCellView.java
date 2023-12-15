package src.view;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import src.model.TowerCell;
import src.util.Palette;

/**
 * A <code>JPanel</code> that displays a tower cell.
 */
public class TowerCellView extends JPanel {

    /**
     * Creates a new <code>TowerCellView</code> that displays the specified
     * <code>TowerCell</code>.
     *
     * <p>
     * The color the the background is set to <code>Palette.TOWER_FILL</code>.
     * Borders are added with color <code>Palette.BACKGROUND</code>.
     * </p>
     *
     * @param cell - The <code>TowerCell</code> object to display
     */
    public TowerCellView(TowerCell cell) {
        setBackground(Palette.TOWER_FILL);
        setBorder(new LineBorder(Palette.BACKGROUND, 5));
    }
}
