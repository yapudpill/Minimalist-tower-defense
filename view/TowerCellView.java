package view;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.TowerCell;
import util.Palette;

public class TowerCellView extends JPanel {

    public TowerCellView(TowerCell cell) {
        setBackground(Palette.TOWER_FILL);
        setBorder(new LineBorder(Palette.BACKGROUND, 5));
    }
}
