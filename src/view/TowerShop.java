package src.view;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import src.model.BasicTower;
import src.model.CanonTower;
import src.model.SniperTower;
import src.model.Tower;

/**
 * A <code>JPanel</code> that contains the tower selection buttons.
 */
public class TowerShop extends JPanel {
    private final ButtonGroup group;

    /**
     * Creates a new <code>TowerShop</code> and add in it one
     * <code>TowerToggleButton</code> per type of tower.
     */
    public TowerShop() {
        group = new ButtonGroup();
        setLayout(new WrapLayout(FlowLayout.CENTER, 60, 10));

        add(new TowerToggleButton(BasicTower::new, group));
        add(new TowerToggleButton(CanonTower::new, group));
        add(new TowerToggleButton(SniperTower::new, group));
    }

    /**
     * @return a <code>Tower</code> instance corresponding to the selected button
     */
    public Tower getSelection() {
        var model = (TowerToggleButton.TowerButtonModel) group.getSelection();
        if (model == null) {
            return null;
        }
        return model.constr.get();
    }
}
