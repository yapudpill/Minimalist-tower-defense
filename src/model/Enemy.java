package src.model;

import javax.swing.*;

public class Enemy {
    public ImageIcon image;
    public String name;
    public int lifePoint;
    public int speed;

    public int x;
    public int y;
    public int xOnPanel;
    public int yOnPanel;

    public PathCell cell;

    public Enemy(String name, int lifePoint, int speed){
        this.lifePoint = lifePoint;
        this.name = name;
        this.speed = speed;
        image = new ImageIcon("src/resources/ennemies/" + name + ".png");
        xOnPanel = 0;
        yOnPanel = 0;
    }


}
