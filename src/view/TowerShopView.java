package src.view;


import src.model.TowerModel;
import src.model.TowerShopModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class TowerShopView extends JPanel implements ActionListener {
    TowerShopModel towerShopModel;


    public TowerShopView(TowerShopModel towerShopModel){
        this.towerShopModel = towerShopModel;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(900,100));
        GridBagConstraints constraints = new GridBagConstraints();
        setBackground(Color.RED);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        towerShopModel.button.addActionListener(this);

        for (JButton button: towerShopModel.buttons){
            add(button,constraints);
        }
        towerShopModel.button.setIcon(new ImageIcon("src/resources/towers/test_green.png"));
        towerShopModel.button.setHorizontalTextPosition(JButton.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == towerShopModel.button){
            towerShopModel.tower = new TowerModel("test_green",0,0,0);
        }
    }
}
