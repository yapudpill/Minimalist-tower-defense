package src.view;


import src.model.TowerModel;
import src.model.TowerShopModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Handler;




public class TowerShopView extends JPanel implements ActionListener, MouseListener {
    TowerShopModel towerShopModel;
    JButton button = new JButton("test");
    JButton button1 = new JButton("123");
    JButton button2 = new JButton("456");
    JButton button3 = new JButton("789");
    JButton button4 = new JButton("101112");


    public TowerShopView(TowerShopModel towerShopModel){
        this.towerShopModel = towerShopModel;
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(900,100));
        GridBagConstraints constraints = new GridBagConstraints();
        setBackground(Color.RED);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        button.addActionListener(this);
        button.addMouseListener(this);


        add(button,constraints);
        add(button1,constraints);
        add(button2,constraints);
        add(button3,constraints);
        add(button4,constraints);
        button.setIcon(new ImageIcon("src/resources/towers/test_green.png"));
        button.setHorizontalTextPosition(JButton.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            towerShopModel.tower = new TowerModel("test_green",0,0,0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println(e.getLocationOnScreen());
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
