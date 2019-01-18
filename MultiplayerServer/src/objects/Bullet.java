package objects;


import engine.GameEngine;
import gameConstants.GameConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bullet CLASS
 */
public class Bullet {

    private GameEngine gameEngine;

    private int damage;
    private int type;
    private double radius;
    private double bulletSpeed;

    private double posX;
    private double posY;

    private List<Ship> targets;
    private Ship owner;

    private boolean isDead = false;
    private  boolean isNew;

    private Timer timer = new Timer();

    private ReentrantLock lock = new ReentrantLock();

    Bullet(GameEngine ge, int type, double x, double y, Ship owner) {
        this.gameEngine = ge;
        this.type = type;
        setTypeProperties();
        targets = new ArrayList<>();
        this.owner = owner;
        this.posX = x;
        this.posY = y;
        this.isNew = true;
    }

    /**
     * Update properties of the bullet according to its type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.BULLET_TYPE_1:
                this.radius = 5;
                this.damage = 2;
                this.bulletSpeed = 0.001;
                break;
            case GameConstants.BULLET_TYPE_2:
                this.radius = 5;
                this.damage = 1;
                this.bulletSpeed = 0.001;
                break;
            case GameConstants.BULLET_TYPE_3:
                this.radius = 5;
                this.damage = 2;
                this.bulletSpeed = 0.001;
                break;
            case GameConstants.BULLET_TYPE_4:
                this.radius = 15;
                this.damage = 10;
                this.bulletSpeed = 0.001;
                break;
        }
    }

    /**
     * The bullet is shot
     */
    void shoot(int dir) {

        gameEngine.addToBullets(this);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                posY += dir;
//                System.out.println(posX + "  " + posY);
                checkIfHit(posX,posY);
            }
        },0,(int)(this.bulletSpeed * 1000));
    }

    // CheckIfHit controls if bullet hits to given coordinate.
    private void checkIfHit(double x, double y) {
        for (Ship ship : targets) {
            double xp = ship.getPosX();
            double yp = ship.getPosY();

            if (!ship.isDead()) {
                if (x > xp - this.radius && x < xp + ship.getWidth() + this.radius
                        && y > yp - this.radius && y < yp + ship.getHeight() + this.radius) {
                    int health = ship.getHealth();
                    int type = ship.getType();
                    ship.damaged(this.damage);
                    if (ship.isDead()) {
                        if (owner instanceof Player) {
                            ((Player) owner).increaseScore(type);
                        }
                    }
                    hit(health);
                    break;
                }
            }
        }
        if(posX<0 || posX>GameConstants.WIDTH || posY<0 || posY>GameConstants.HEIGHT) {
            kill();
        }
    }

    /**
     * Hit reduces damage of the bullet. If its damage is made 0, kill the bullet.
     */
    private void hit(int damage) {
        lock.lock();
        this.damage-=damage;
        lock.unlock();
        if(this.damage <= 0) {
            kill();
        }
    }

    private void kill() {
        isDead = true;
        timer.cancel();
    }

    /**
     * Setters and Getters
     */

    void setTargets(List<Ship> targets) {
        this.targets = targets;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getType() {
        return type;
    }

}
