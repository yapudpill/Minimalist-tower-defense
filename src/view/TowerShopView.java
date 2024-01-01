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


public class TowerShopView extends JPanel implements ActionListener { // The bottom bar
    // The buttons that are in the bottom bar-----------------------------------------------------------
    private final TowerShopModel greenTower;
    private final TowerShopModel redTower;
    private final TowerShopModel button1;
    private final TowerShopModel button2;
    private final TowerShopModel button3;
    //-----------------------------------------------------------------------------------------------------
    public TowerModel towerModel; // The selected Tower that the label has to display

    public TowerShopModel [] buttons; // List of the buttons
    public JLabel label; // Label that displays the selected tower
    public boolean hasBeenAdded = false; // Checks whether the Player has selected a cell

    public TowerShopView(){
        greenTower = new TowerShopModel(new TowerModel("test_green",10,2,50),new JButton("50" /* the cost*/));
        redTower = new TowerShopModel(new TowerModel("test_red",30,1,50),new JButton("50"/* the cost*/));
        button1 = new TowerShopModel(null,new JButton("123"));
        button2 = new TowerShopModel(null,new JButton("456"));
        button3 = new TowerShopModel(null,new JButton("789"));
        buttons = new TowerShopModel[]{greenTower,redTower,button1,button2,button3};
        
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(900,100));
        setBackground(Color.BLUE);
        

        
        for (TowerShopModel towerShopModel: buttons){
            add(towerShopModel.button);
            towerShopModel.button.addActionListener(this);
        }
        
        greenTower.button.setIcon(new ImageIcon(generateImage("test_green",900/buttons.length,100)));
        greenTower.button.setHorizontalTextPosition(JButton.CENTER);
        redTower.button.setIcon(new ImageIcon(generateImage("test_red",900/buttons.length,100)));
        redTower.button.setHorizontalTextPosition(JButton.CENTER);
        
        
        label = new JLabel();
        
    }
    
    /** Generates an image with the specified width and height (only need to put the file's name without the path and extension (has to be png tho)*/
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
    public void actionPerformed(ActionEvent e) { // add here if you need to add a button
        if (e.getSource() == greenTower.button){
            towerModel = greenTower.tower;
            label.setIcon(new ImageIcon(generateImage("test_green",getWidth()/7,getHeight()/2)));
            add(label);
            hasBeenAdded = true;
        }
        else{
            if(e.getSource() == redTower.button){
                towerModel = redTower.tower;
                label.setIcon(new ImageIcon(generateImage("test_red",getWidth()/7,getHeight()/2)));
                add(label);
                hasBeenAdded = true;
            }
        }
        revalidate();
        repaint();
    }
}
