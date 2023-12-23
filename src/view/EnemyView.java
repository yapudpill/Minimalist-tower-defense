package src.view;

import src.model.Enemy;
import src.util.Direction;
import src.util.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class EnemyView extends JPanel implements ActionListener {
    public Enemy ennemy;
    int xVelocity;
    int yVeclocity;
    int x = 0;
    int y = 0;
    Timer timer;
    Image image;

    public EnemyView(String name, double lifePoint, double speed){
        ennemy = new Enemy(name, lifePoint, speed);
        timer = new Timer(100,null);
        }

    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(ennemy.image.getImage(),x,y,null);


    }

    public void actionPerformed (ActionEvent e){
        if (ennemy.cell.direction.equals(Direction.UP)){
            y+=1;
        } else if (ennemy.cell.direction.equals(Direction.RIGHT)){
            x+=1;
        } else if (ennemy.cell.direction.equals(Direction.DOWN)) {
            y-=1;
        } else if (ennemy.cell.direction.equals(Direction.LEFT)) {
            x-=1;
        }
        if (x>=Math.abs(getWidth())){
            // faut recup la map
        }
        else{
            if (x>= Math.abs(getHeight())){
                // pareil ici
            }
        }
        repaint();
    }


}
