package src.model;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    public ImageIcon image;
    public String name;
    public int lifePoint;
    public int speed;
    public Point coordinates; // coordinates on the JPanel GridView 
    public PathCell cell; // the cell that the enemy currently is

    public Enemy(String name, int lifePoint, int speed, ImageIcon image){
        this.lifePoint = lifePoint;
        this.name = name;
        this.speed = speed;
        this.image = image;
        coordinates = new Point();
    }


}
