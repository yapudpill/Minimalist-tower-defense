package view.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameControl;
import model.GameModel;
import model.LevelModel;
import view.Palette;
import view.WrapLayout;

public class GameView extends JPanel {
    public final TowerShop shop;

    /**
     * Creates a new <code>View</code> displaying the specified model
     * and sending inputs to the specified controllers.
     *
     * @param model   the mode to be displayed in this view
     * @param control the controller that controls the game itself
     */
    public GameView(GameModel model, GameControl control) {
        setLayout(new BorderLayout());

        String levelText, waveLabel;
        if (model instanceof LevelModel) {
            LevelModel m = (LevelModel) model;
            levelText = "Level " + (m.lvl+1);
            waveLabel = "Wave (/" + m.nbWaves + "): ";
        } else {
            levelText = "Marathon";
            waveLabel = "Wave: ";
        }
        JLabel levelLabel = new JLabel(levelText);
        levelLabel.setFont(Palette.PLAIN_SANS);

        JPanel topPanel = new JPanel(new WrapLayout(FlowLayout.CENTER, 60, 10));
        topPanel.add(levelLabel);
        topPanel.add(new IntLabel(Palette.LIFE, "Life: ", model::getLife));
        topPanel.add(new IntLabel(Palette.GOLD, "Gold: ", model::getGold));
        topPanel.add(new IntLabel(Palette.WAVE, waveLabel, model.stats::getWaveCount));
        add(topPanel, BorderLayout.NORTH);

        // Necessary because otherwise the grid would be stretched across the window
        JPanel gridPanel = new JPanel();
        gridPanel.add(new GridView(model.grid, control));
        add(gridPanel, BorderLayout.CENTER);

        this.shop = new TowerShop(control);
        add(shop, BorderLayout.SOUTH);
    }
}
