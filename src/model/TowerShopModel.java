package src.model;

import javax.swing.*;
import java.awt.*;

public class TowerShopModel {
    public TowerModel tower;
    public JButton button;
    
    public TowerShopModel(TowerModel tower, JButton button){
        this.tower = tower;
        this.button = button;
    }
    
}
