package src.model;

import javax.swing.*;

public class Enemy {
    public ImageIcon image;
    public String name;
    public int lifePoint;
    public boolean isOnBoard;
    public int speed;

    public int x;
    public int y;

    public PathCell cell;

    public Enemy(String name, int lifePoint, int speed){
        this.lifePoint = lifePoint;
        this.name = name;
        isOnBoard = false;
        this.speed = speed;
        image = new ImageIcon("src/resources/ennemies/" + name + ".png");
    }


}
