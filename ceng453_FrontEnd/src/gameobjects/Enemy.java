package gameobjects;


import engine.GameEngine;
import gameConstants.GameConstants;
import gameConstants.UIConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pages.GamePage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ENEMY CLASS
 */
public class Enemy implements Ship{

    private int health;
    private Color color;
    private int type;
    private double width;
    private double height;
    private double bulletMoveSpeed;
    private double movementSpeed;
    private Rectangle enemy;
    private boolean isDead = false;
    private List<Ship> targets;
    private int movementDir;
    private Timeline timeline;
    private Timeline shoot_timeline;
    private Random generator = new Random();

    private ReentrantLock lock = new ReentrantLock();

    public Enemy(int type) {
        movementDir = GameConstants.MOVEMENT_DIRECTION_DOWN;
        this.type = type;
        setTypeProperties();
        enemy = new Rectangle(UIConstants.WIDTH /2.0,50,this.width,this.height);
        enemy.setFill(this.color);
        targets = new ArrayList<>();
    }

    /**
     * Sets properties of the Enemy according to its type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.ENEMY_TYPE_1:
                this.health = GameConstants.ENEMY_TYPE_1_HEALTH_;
                this.color = GameConstants.ENEMY_TYPE_1_COLOR;
                this.width = GameConstants.ENEMY_TYPE_1_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_1_HEIGHT;
                this.bulletMoveSpeed = GameConstants.ENEMY_TYPE_1_BULLET_MOVE_SPEED;
                this.movementSpeed = GameConstants.ENEMY_TYPE_1_MOVEMENT_SPEED;
                break;
            case GameConstants.ENEMY_TYPE_2:
                this.health = GameConstants.ENEMY_TYPE_2_HEALTH_;
                this.color = GameConstants.ENEMY_TYPE_2_COLOR;
                this.width = GameConstants.ENEMY_TYPE_2_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_2_HEIGHT;
                this.bulletMoveSpeed = GameConstants.ENEMY_TYPE_2_BULLET_MOVE_SPEED;
                this.movementSpeed = GameConstants.ENEMY_TYPE_2_MOVEMENT_SPEED;
                break;
            case GameConstants.ENEMY_TYPE_3:
                this.health = GameConstants.ENEMY_TYPE_3_HEALTH_;
                this.color = GameConstants.ENEMY_TYPE_3_COLOR;
                this.width = GameConstants.ENEMY_TYPE_3_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_3_HEIGHT;
                this.bulletMoveSpeed = GameConstants.ENEMY_TYPE_3_BULLET_MOVE_SPEED;
                this.movementSpeed = GameConstants.ENEMY_TYPE_3_MOVEMENT_SPEED;
                break;
        }
    }

    /**
     * Spawns the enemy in the pane.
     */
    public void spawn() {
        GamePage.gamePane.getChildren().add(1, enemy);
        moveAround();
        int p = generator.nextInt(20)+1;
        shoot_timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1 * p + this.bulletMoveSpeed), e -> {
                    if(targets.size() != 0){
                        shoot();
                    }
                })
        );
        shoot_timeline.setCycleCount(Timeline.INDEFINITE);
        shoot_timeline.play();
    }


    /**
     * MoveAround moves the enemies with move function in random order.
     * Changes movement direction with possibility 1/20
     */
    public void moveAround() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(this.movementSpeed), e -> {
                    int p = generator.nextInt(20);
                    if(p<1) {
                        movementDir = generator.nextInt(4) + 1;
                    }
                    move();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Moves the enemy with the movement direction
     * Also checks boundaries
     */
    public void move() {
        switch (movementDir) {
            case GameConstants.MOVEMENT_DIRECTION_RIGHT:
                enemy.setTranslateX(enemy.getTranslateX() + 5);
                break;
            case GameConstants.MOVEMENT_DIRECTION_LEFT:
                enemy.setTranslateX(enemy.getTranslateX() - 5);
                break;
            case GameConstants.MOVEMENT_DIRECTION_DOWN:
                enemy.setTranslateY(enemy.getTranslateY() + 5);
                break;
            case GameConstants.MOVEMENT_DIRECTION_UP:
                enemy.setTranslateY(enemy.getTranslateY() - 5);
                break;
        }

        double x_tras, y_tras;
        if(getPosX()<0) {
            x_tras = -enemy.getX();
        } else if(getPosX()+enemy.getWidth()>UIConstants.WIDTH) {
            x_tras = UIConstants.WIDTH - enemy.getX()-enemy.getWidth();
        } else {
            x_tras = enemy.getTranslateX();
        }
        if(getPosY()<0) {
            y_tras = -enemy.getY();
        } else if(getPosY()+enemy.getHeight()>UIConstants.HEIGHT /2.0){
            y_tras = UIConstants.HEIGHT /2.0 - enemy.getY() - enemy.getHeight();
        } else {
            y_tras = enemy.getTranslateY();
        }

        enemy.setTranslateX(x_tras);
        enemy.setTranslateY(y_tras);
    }

    /**
     * Reduces the health point, if health point equals to 0 kills itself.
     */
    public void damaged(int damage) {
        lock.lock();
        this.health -= damage;
        lock.unlock();
        if(health <= 0){
            kill();
        }
    }

    /**
     * Kills itself
     * If GameEngine enemies list is empty, calls enemySlotEmpty to generate next slots
     */
    public void kill() {
        GamePage.gamePane.getChildren().remove(enemy);
        timeline.stop();
        shoot_timeline.stop();
        isDead = true;
        for (Ship target : targets) {
            target.removeFromTargets(this);
        }
        GameEngine.enemiesLock.lock();
        if(GameEngine.enemies.size() == 0) {
            GameEngine.enemySlotEmpty();
        }
        GameEngine.enemiesLock.unlock();
    }

    /**
     * Shoot one bullet.
     */
    public void shoot() {
        Bullet bullet = new Bullet(this.type+1,
                getPosX() + enemy.getWidth()/2,
                getPosY() + enemy.getHeight(),
                this);

        bullet.setTargets(this.targets);

        bullet.shoot(GameConstants.BULLET_DIRECTION_DOWN);
    }

    public void removeFromTargets(Ship ship) {
        this.targets.remove(ship);
    }

    /**
     * Setters and Getters
     */

    public Rectangle getShip() {
        return enemy;
    }

    public double getPosX() {
        return enemy.getX() + enemy.getTranslateX();
    }

    public double getPosY() {
        return enemy.getY() + enemy.getTranslateY();
    }

    public int getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setTargets(List<Ship> targets) {
        this.targets = targets;
    }

}
