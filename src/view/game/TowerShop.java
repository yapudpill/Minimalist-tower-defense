package view.game;

import java.awt.FlowLayout;
import java.util.function.Function;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.GameControl;
import model.towers.BasicTower;
import model.towers.CanonTower;
import model.towers.SniperTower;
import model.towers.Tower;
import util.Coordinate;
import view.Palette;
import view.WrapLayout;
import view.game.TowerToggleButton.TowerButtonModel;

/**
 * A <code>JPanel</code> that contains the tower selection buttons.
 */
public class TowerShop extends JPanel {
    private final ButtonGroup group;

    /**
     * Creates a new <code>TowerShop</code> and add in it one
     * <code>TowerToggleButton</code> per type of tower.
     */
    public TowerShop(GameControl control) {
        group = new ButtonGroup();
        setLayout(new WrapLayout(FlowLayout.CENTER, 60, 10));

        add(new TowerToggleButton(BasicTower::new, group));
        add(new TowerToggleButton(CanonTower::new, group));
        add(new TowerToggleButton(SniperTower::new, group));

        JButton exit = new JButton("Back to menu");
        exit.setFont(Palette.PLAIN_SANS);
        exit.addActionListener(e -> control.exit());
        add(exit);
    }

    /**
     * @return a <code>Tower</code> instance corresponding to the selected button
     */
    public Function<Coordinate, Tower> getSelection() {
        var model = (TowerButtonModel) group.getSelection();
        if (model == null) {
            return null;
        }
        return model.constr;
    }
}
