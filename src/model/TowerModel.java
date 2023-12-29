package src.model;

import javax.swing.*;

public class TowerModel {
    public String name;
    public int damage;
    public int range;
    public int cost;
    public ImageIcon image;
    
    public TowerModel (String name, int damage, int range, int cost){
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.cost = cost;
        image = new ImageIcon("src/resources/towers/" + name + ".png");
    }
}
