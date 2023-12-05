package src.view;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import src.model.TowerCell;
import src.util.Palette;

public class TowerCellView extends JPanel {

    public TowerCellView(TowerCell cell) {
        setBackground(Palette.TOWER_FILL);
        setBorder(new LineBorder(Palette.BACKGROUND, 5));
    }
}
