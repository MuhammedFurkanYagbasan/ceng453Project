package gameobjects;


import engine.GameEngine;
import gameConstants.GameConstants;
import gameConstants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import pages.GamePage;
import java.util.ArrayList;
import java.util.List;

/**
 * BULLET CLASS
 */
public class Bullet {

    private int damage;
    private int type;
    private double radius;
    private Color color;
    private double bulletSpeed;
    private Circle bullet;
    private List<Ship> targets;
    private Ship owner;
    private Timeline timeline;

    Bullet(int type, double x, double y, Ship owner) {
        this.type = type;
        setTypeProperties();
        bullet = new Circle(x, y, this.radius, this.color);
        targets = new ArrayList<>();
        this.owner = owner;
    }

    /**
     * Set properties of bullet from its type
     */
    private void setTypeProperties() {

        switch (type){
            case GameConstants.BULLET_TYPE_1:
                this.radius = GameConstants.BULLET_TYPE_1_RADIUS;
                this.damage = GameConstants.BULLET_TYPE_1_DAMAGE;
                this.color = GameConstants.BULLET_TYPE_1_COLOR;
                this.bulletSpeed = GameConstants.BULLET_TYPE_1_BULLETSPEED;
                break;
            case GameConstants.BULLET_TYPE_2:
                this.radius = GameConstants.BULLET_TYPE_2_RADIUS;
                this.damage = GameConstants.BULLET_TYPE_2_DAMAGE;
                this.color = GameConstants.BULLET_TYPE_2_COLOR;
                this.bulletSpeed = GameConstants.BULLET_TYPE_2_BULLETSPEED;
                break;
            case GameConstants.BULLET_TYPE_3:
                this.radius = GameConstants.BULLET_TYPE_3_RADIUS;
                this.damage = GameConstants.BULLET_TYPE_3_DAMAGE;
                this.color = GameConstants.BULLET_TYPE_3_COLOR;
                this.bulletSpeed = GameConstants.BULLET_TYPE_3_BULLETSPEED;
                break;
            case GameConstants.BULLET_TYPE_4:
                this.radius = GameConstants.BULLET_TYPE_4_RADIUS;
                this.damage = GameConstants.BULLET_TYPE_4_DAMAGE;
                this.color = GameConstants.BULLET_TYPE_4_COLOR;
                this.bulletSpeed = GameConstants.BULLET_TYPE_4_BULLETSPEED;
                break;
        }
    }

    /**
     * The bullet has shot
     */
    void shoot(int dir) {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(this.bulletSpeed), e -> {
                    bullet.setTranslateY(bullet.getTranslateY() + dir);
                    checkIfHit(bullet.getCenterX(),bullet.getCenterY() + bullet.getTranslateY());
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);

        GameEngine.addToBullets(this);
        GamePage.gamePane.getChildren().add(bullet);
        timeline.play();
    }

    /**
     * Controls if bullet hits to any of its targets.
     * Kills itself if out of screen
     */
    private void checkIfHit(double x, double y) {
        for (Ship ship : targets) {
            double xp = ship.getPosX();
            double yp = ship.getPosY();

            if (x > xp - this.radius && x < xp + ship.getShip().getWidth() + this.radius
                    && y > yp - this.radius && y < yp + ship.getShip().getHeight() + this.radius) {
                int health = ship.getHealth();
                int type = ship.getType();
                ship.damaged(this.damage);
                if (ship.isDead()) {
                    if (owner instanceof Player) {
                        ((Player) owner).increaseScore(type);
                        GamePage.updateScoreLabel(((Player) owner).getScore());
                    }
                }
                hit(health);
                break;
            }
            if(x < 0 || x > UIConstants.WIDTH || y < 0 || y > UIConstants.HEIGHT) {
                kill();
            }
        }

    }

    /**
     * Hit reduces damage of the bullet. If its damage is made 0, kill itself.
      */
    private void hit(int damage) {
        this.damage -= damage;
        if(this.damage <= 0) {
            kill();
        }
    }

    public void kill() {
        GameEngine.removeFromBulets(this);
        GamePage.gamePane.getChildren().remove(bullet);
        timeline.stop();
    }

    /**
     * Setters and Getters
     */

    void setTargets(List<Ship> targets) {
        this.targets = targets;
    }

}
