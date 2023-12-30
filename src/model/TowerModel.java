package src.model;

import javax.swing.*;
import java.awt.*;

public class TowerModel {
    public String name;
    public int damage;
    public int range;
    public int cost;
    public Point index;
    public ImageIcon image;
    
    public TowerModel (String name, int damage, int range, int cost){
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.cost = cost;
    }
    
    public TowerModel (TowerModel towerModel, ImageIcon image){
        name = towerModel.name;
        damage = towerModel.damage;
        range = towerModel.range;
        cost = towerModel.cost;
        this.image = image;
    }
}
