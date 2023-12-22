package src.view;

import src.model.Enemy;

import javax.swing.*;


public class EnemyView extends JLabel {
    public Enemy ennemy;

    public EnemyView(String name, double lifePoint, double speed){
        ennemy = new Enemy(name, lifePoint, speed);
        this.setIcon(ennemy.image);
        }

}
