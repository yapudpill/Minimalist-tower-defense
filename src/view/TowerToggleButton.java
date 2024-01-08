package src.view;

import java.awt.Font;
import java.util.function.Supplier;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

import src.model.BasicTower;
import src.model.CanonTower;
import src.model.SniperTower;
import src.model.Tower;

/**
 * A modified type of <code>JToggleButton</code> that encapsulates a tower
 * constructor.
 */
public class TowerToggleButton extends JToggleButton {

    /**
     * Creates a new <code>TowerToggleButton</code> in the specified group
     * that encapsulates the specified constructor.
     *
     * @param constr the constructor that this button represents
     * @param group  the group in which this button will be placed
     */
    public TowerToggleButton(Supplier<Tower> constr, ButtonGroup group) {
        setModel(new TowerButtonModel(constr, group));
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
        setOpaque(true);

        Tower tower = constr.get();
        String name = "";
        if (tower instanceof BasicTower) {
            setBackground(Palette.BASIC_TOWER_INNER);
            name = "Basic";
        } else if (tower instanceof CanonTower) {
            setBackground(Palette.CANON_TOWER_INNER);
            name = "Canon";
        } else if (tower instanceof SniperTower) {
            setBackground(Palette.SNIPER_TOWER_INNER);
            name = "Sniper";
        }
        setText(name + " (" + tower.cost + ")");
    }

    /**
     * A modified type of <code>ToggleButtonModel</code> that encapsulates a
     * tower constructor.
     */
    public static class TowerButtonModel extends ToggleButtonModel {
        public final Supplier<Tower> constr;

        /**
         * Creates a new <code>TowerButtonModel</code> in the specified group
         * that encapsulates the specified constructor.
         *
         * @param constr the constructor that this button represents
         * @param group  the group in which this button will be placed
         */
        public TowerButtonModel(Supplier<Tower> constr, ButtonGroup group) {
            setGroup(group);
            this.constr = constr;
        }
    }
}
