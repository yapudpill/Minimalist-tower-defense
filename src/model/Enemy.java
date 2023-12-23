package src.model;

import javax.swing.*;

public class Enemy {
    public ImageIcon image;
    public String name;
    public double lifePoint;
    public boolean isOnBoard;
    public double speed;

    public int x;
    public int y;

    public PathCell cell;

    public Enemy(String name, double lifePoint, double speed){
        this.lifePoint = lifePoint;
        this.name = name;
        isOnBoard = false;
        this.speed = speed;
        image = new ImageIcon("src/resources/ennemies/" + name + ".png");
    }


}
