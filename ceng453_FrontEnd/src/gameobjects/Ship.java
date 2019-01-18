package gameobjects;

import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * Player and Enemy implements this Interface
 */
public interface Ship {

    Rectangle getShip();
    int getHealth();
    void spawn();
    void damaged(int damage);
    void shoot();
    void setTargets(List<Ship> targets);
    void removeFromTargets(Ship ship);
    boolean isDead();
    int getType();
    void kill();
    double getPosX();
    double getPosY();

}
