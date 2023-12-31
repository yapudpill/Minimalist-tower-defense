package src.view;


import src.model.TowerModel;
import src.model.TowerShopModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class TowerShopView extends JPanel implements ActionListener {
    TowerShopModel towerShopModel;
    JLabel label;
    boolean hasBeenAdded = false;


    public TowerShopView(TowerShopModel towerShopModel){
        this.towerShopModel = towerShopModel;
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(900,100));
        setBackground(Color.BLUE);
        

        
        for (JButton button: towerShopModel.buttons){
            add(button);
            button.addActionListener(this);
        }
        
        towerShopModel.button.setIcon(new ImageIcon(generateImage("test_green",900/towerShopModel.buttons.length,100)));
        towerShopModel.button.setHorizontalTextPosition(JButton.CENTER);
        towerShopModel.button1.setIcon(new ImageIcon(generateImage("test_red",900/towerShopModel.buttons.length,100)));
        towerShopModel.button1.setHorizontalTextPosition(JButton.CENTER);
        
        
        
        
        label = new JLabel();
        
    }

    private Image generateImage (String s, int width, int height){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/resources/towers/" + s + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        return dimg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {// probleme de taille d'image des boutons
        if (e.getSource() == towerShopModel.button){
            towerShopModel.tower = new TowerModel("test_green",0,0,0);
            label.setIcon(new ImageIcon(generateImage("test_green",getWidth()/7,getHeight()/2)));
            add(label);
            hasBeenAdded = true;
        }
        else{
            if(e.getSource() == towerShopModel.button1){
                towerShopModel.tower = new TowerModel("test_red",0,0,0);
                label.setIcon(new ImageIcon(generateImage("test_red",getWidth()/7,getHeight()/2)));
                add(label);
                hasBeenAdded = true;
            }
        }
        revalidate();
        repaint();
    }
}
