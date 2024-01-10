package src.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import src.controller.MarathonControl;
import src.model.MarathonModel;

/**
 * A <code>JPanel</code> that displays the UI of a mathon game. This includes
 * the grid, score, time, money and tower selection.
 */
public class MarathonView extends JPanel {
    public final TowerShop shop;

    /**
     * Creates a new <code>MarathonView</code> displaying the specified model
     * and sending inputs to the specified controllers.
     *
     * @param model   the mode to be displayed in this view
     * @param control the controller that controls the game itself
     */
    public MarathonView(MarathonModel model, MarathonControl control) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new WrapLayout(FlowLayout.CENTER, 60, 10));
        topPanel.add(new IntLabel(Palette.LIFE, "Life: ", model::getLife));
        topPanel.add(new IntLabel(Palette.GOLD, "Gold: ", model::getGold));
        topPanel.add(new IntLabel(Palette.WAVE, "Wave: ", model::getWaveCount));
        add(topPanel, BorderLayout.NORTH);

        // Necessary because otherwise the grid would be stretched across the window
        JPanel gridPanel = new JPanel();
        gridPanel.add(new GridView(model.grid, control));
        add(gridPanel, BorderLayout.CENTER);

        shop = new TowerShop(control);
        add(shop, BorderLayout.SOUTH);
    }
}
