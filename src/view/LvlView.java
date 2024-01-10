package src.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import src.controller.LvlControl;
import src.model.LvlModel;

public class LvlView extends JPanel {
    public final TowerShop shop;

    public LvlView(LvlModel model, LvlControl control) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new WrapLayout(FlowLayout.CENTER, 60, 10));

        JLabel lvl = new JLabel("Level " + (model.lvl+1));
        lvl.setFont(Palette.PLAIN_SANS);
        topPanel.add(lvl);

        topPanel.add(new IntLabel(Palette.LIFE, "Life: ", model::getLife));
        topPanel.add(new IntLabel(Palette.GOLD, "Gold: ", model::getGold));
        topPanel.add(new IntLabel(Palette.WAVE, "Wave (/" + model.nbWaves + "): ", model.stats::getWaveCount));
        add(topPanel, BorderLayout.NORTH);

        // Necessary because otherwise the grid would be stretched across the window
        JPanel gridPanel = new JPanel();
        gridPanel.add(new GridView(model.grid, control));
        add(gridPanel, BorderLayout.CENTER);

        shop = new TowerShop(control);
        add(shop, BorderLayout.SOUTH);
    }
}
