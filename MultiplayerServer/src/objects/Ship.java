package objects;

import java.util.List;

public interface Ship {

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

    double getWidth();
    double getHeight();
}
