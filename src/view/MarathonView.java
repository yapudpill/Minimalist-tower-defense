package src.view;

import javax.swing.*;

import src.controller.MainControl;
import src.controller.MarathonControl;
import src.model.MarathonModel;
import src.model.TowerShopModel;

import java.awt.*;

/**
 * A <code>JPanel</code> that displays the UI of a mathon game. This includes
 * the grid, score, time, money and tower selection.
 */
public class MarathonView extends JPanel {

    /**
     * Creates a new <code>MarathonView</code> displaying the specified model
     * and sending inputs to the specified controllers.
     *
     * @param model       - The mode to be displayed in this view
     * @param mainControl - The controller that receives signals to change menu
     * @param gameControl - The controller that controls the game itself
     */
    public MarathonView(MarathonModel model, MainControl mainControl, MarathonControl gameControl) {
        TowerShopModel towerShopModel = new TowerShopModel();
        TowerShopView towerShopView = new TowerShopView(towerShopModel);
        GridView gridView = new GridView(model.grid, model.player,mainControl, towerShopModel, towerShopView);
        JPanel panel2 = new JPanel();
        panel2.add(gridView);

        setLayout(new BorderLayout());

        add(panel2,BorderLayout.CENTER);
        add(towerShopView,BorderLayout.SOUTH);

    }
}
