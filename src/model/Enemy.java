package src.model;

import javax.swing.*;

public class Enemy {
    public ImageIcon image;
    public double lifePoint;
    public boolean isOnBoard;
    public double speed;

    public Enemy(String name, double lifePoint, double speed){
        this.lifePoint = lifePoint;
        isOnBoard = false;
        this.speed = speed;
        image = new ImageIcon("src/resources/ennemies/" + name + ".png");
    }


}
