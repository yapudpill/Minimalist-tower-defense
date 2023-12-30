package src.model;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    public ImageIcon image;
    public String name;
    public int lifePoint;
    public int speed;
    public Point coordinates;

    public PathCell cell;

    public Enemy(String name, int lifePoint, int speed, ImageIcon image){
        this.lifePoint = lifePoint;
        this.name = name;
        this.speed = speed;
        this.image = image;
        coordinates = new Point();
    }


}
