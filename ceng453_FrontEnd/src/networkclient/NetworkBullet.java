package networkclient;


import gameConstants.GameConstants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pages.GamePage;

/**
 * NetworkBullet CLASS
 */
public class NetworkBullet {

    private double posX;
    private double posY;
    private boolean isDead;
    private int type;
    private double radius;
    private Color color;
    private Circle bullet;

    NetworkBullet(double posX, double posY, boolean isDead, int type) {
        this.posX = posX;
        this.posY = posY;
        this.isDead = isDead;
        this.type = type;
        setTypeProperties();

        bullet = new Circle(posX,posY,radius,color);
        GamePage.gamePane.getChildren().add(bullet);
    }

    /**
     * Set properties of bullet from its type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.BULLET_TYPE_1:
                this.radius = GameConstants.BULLET_TYPE_1_RADIUS;
                this.color = GameConstants.BULLET_TYPE_1_COLOR;
                break;
            case GameConstants.BULLET_TYPE_2:
                this.radius = GameConstants.BULLET_TYPE_2_RADIUS;
                this.color = GameConstants.BULLET_TYPE_2_COLOR;
                break;
            case GameConstants.BULLET_TYPE_3:
                this.radius = GameConstants.BULLET_TYPE_3_RADIUS;
                this.color = GameConstants.BULLET_TYPE_3_COLOR;
                break;
            case GameConstants.BULLET_TYPE_4:
                this.radius = GameConstants.BULLET_TYPE_4_RADIUS;
                this.color = GameConstants.BULLET_TYPE_4_COLOR;
                break;
        }
    }

    /**
     * Update Bullet status
     */
    void reDraw() {
        if(isDead){
            kill();
        } else {
            bullet.setCenterX(posX);
            bullet.setCenterY(posY);
        }
    }

    /**
     * Kill the bullet
     */
    void kill() {
        NetworkClient.bullets.remove(this);
        GamePage.gamePane.getChildren().remove(bullet);
    }


    /**
     * Getter and Setters
     */

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
