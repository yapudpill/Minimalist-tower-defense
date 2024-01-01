package src.model;

import javax.swing.*;
import java.awt.*;

public class TowerShopModel {
    public TowerModel tower; // The tower that is bound to the button
    public JButton button; // The button that makes "tower" spawn
    
    public TowerShopModel(TowerModel tower, JButton button){
        this.tower = tower;
        this.button = button;
    }
    
}
