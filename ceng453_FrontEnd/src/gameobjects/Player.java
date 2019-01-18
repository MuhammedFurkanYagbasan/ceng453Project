package gameobjects;


import gameConstants.GameConstants;
import gameConstants.UIConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import pages.GamePage;
import pages.ResultPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * PLAYER CLASS
 */
public class Player implements Ship {

    private int health;
    private int score;
    private double width;
    private double height;
    private Color color;
    private int type;
    private double bulletSpeed;
    private boolean isDead = false;
    private Rectangle player;
    private List<Ship> targets;
    private Timeline shoot_timeline;

    private ReentrantLock lock = new ReentrantLock();

    public Player(int type) {
        this.score = 0;
        this.type = type;
        setTypeProperties();

        player = new Rectangle(UIConstants.WIDTH /2.0,UIConstants.HEIGHT -100,this.width,this.height);
        player.setFill(this.color);
        targets = new ArrayList<>();
    }

    /**
     * Sets properties of the Player according to its type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.PLAYER_TYPE_1:
                this.health = GameConstants.PLAYER_TYPE_1_HEALTH;
                this.color = GameConstants.PLAYER_TYPE_1_COLOR;
                this.width = GameConstants.PLAYER_TYPE_1_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_1_HEIGHT;
                this.bulletSpeed = GameConstants.PLAYER_TYPE_1_BULLET_SPEED;
                break;
            case GameConstants.PLAYER_TYPE_2:
                this.health = GameConstants.PLAYER_TYPE_2_HEALTH;
                this.color = GameConstants.PLAYER_TYPE_2_COLOR;
                this.width = GameConstants.PLAYER_TYPE_2_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_2_HEIGHT;
                this.bulletSpeed = GameConstants.PLAYER_TYPE_2_BULLET_SPEED;
                break;
            case GameConstants.PLAYER_TYPE_3:
                this.health = GameConstants.PLAYER_TYPE_3_HEALTH;
                this.color = GameConstants.PLAYER_TYPE_3_COLOR;
                this.width = GameConstants.PLAYER_TYPE_3_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_3_HEIGHT;
                this.bulletSpeed = GameConstants.PLAYER_TYPE_3_BULLET_SPEED;
                break;
        }
    }

    /**
     * Spawns the player in the pane.
     */

    public void spawn() {
        GamePage.updateHpLabel(this.health);
        GamePage.updateScoreLabel(this.score);

        GamePage.gamePane.getChildren().add(player);
        shoot_timeline = new Timeline(
                new KeyFrame(Duration.seconds(this.bulletSpeed), e -> {
                    if(targets.size() != 0) {
                        shoot();
                    }
                })
        );
        shoot_timeline.setCycleCount(Timeline.INDEFINITE);
        shoot_timeline.play();
    }

    /**
     * Reduces the health point of the player, if health point equal to 0 kills itself.
     */
    public void damaged(int damage) {
        lock.lock();
        this.health -= damage;
        lock.unlock();
        GamePage.updateHpLabel(this.health);
        if(this.health <= 0){
            kill();
            ResultPage.show("Game Over");
        }
    }

    /**
     * Kills the player and removes itself from its targets
     */
    public void kill() {
        lock.lock();
        GamePage.gamePane.getChildren().remove(player);
        shoot_timeline.stop();
        this.isDead = true;
        for(int i=0; i<targets.size(); i++) {
            targets.get(i).removeFromTargets(this);
        }
        lock.unlock();
    }

    /**
     * Shoot one bullet.
     */
    public void shoot() {
        if(!isDead){
            Bullet bullet = new Bullet(GameConstants.BULLET_TYPE_1,
                    getPosX()+player.getWidth()/2,
                    getPosY(),
                    this);

            bullet.setTargets(this.targets);

            bullet.shoot(GameConstants.BULLET_DIRECTION_UP);
        }
    }

    void increaseScore(int type) {
        switch(type){
            case 1:
                this.score += GameConstants.SCORE_TYPE_1;
                break;
            case 2:
                this.score += GameConstants.SCORE_TYPE_2;
                break;
            case 3:
                this.score += GameConstants.SCORE_TYPE_3;
                break;
        }
    }

    public void removeFromTargets(Ship ship) {
        this.targets.remove(ship);
    }

    /**
     * Setters and Getters
     */

    public double getPosX() {
        return player.getX() + player.getTranslateX();
    }

    public double getPosY() {
        return player.getY() + player.getTranslateY();
    }

    public boolean isDead() {
        return isDead;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getType() {
        return type;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle getShip(){
        return player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTargets(List<Ship> targets) {
        this.targets = targets;
    }

}
