package networkclient;


import gameConstants.GameConstants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pages.GamePage;


/**
 * NetworkEnemy CLASS
 */
public class NetworkEnemy {

    private double posX;
    private double posY;
    private boolean isDead;
    private int type;
    private double width;
    private double height;
    private Color color;
    private Rectangle ship;

    NetworkEnemy(double posX, double posY, boolean isDead, int type) {
        this.posX = posX;
        this.posY = posY;
        this.isDead = isDead;
        this.type = type;
        setTypeProperties();

        ship = new Rectangle(posX,posY,width,height);
        ship.setFill(color);
        GamePage.gamePane.getChildren().add(ship);
    }

    /**
     * Sets properties of the Enemy according to its type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.ENEMY_TYPE_1:
                this.color = GameConstants.ENEMY_TYPE_1_COLOR;
                this.width = GameConstants.ENEMY_TYPE_1_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_1_HEIGHT;
                break;
            case GameConstants.ENEMY_TYPE_2:
                this.color = GameConstants.ENEMY_TYPE_2_COLOR;
                this.width = GameConstants.ENEMY_TYPE_2_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_2_HEIGHT;
                break;
            case GameConstants.ENEMY_TYPE_3:
                this.color = GameConstants.ENEMY_TYPE_3_COLOR;
                this.width = GameConstants.ENEMY_TYPE_3_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_3_HEIGHT;
                break;
        }
    }

    /**
     * Updates Enemy status
     */
    void reDraw() {
        if(isDead){
           kill();
        } else {
            ship.setX(posX);
            ship.setY(posY);
        }
    }

    /**
     * Kills itself
     */
    void kill() {
        NetworkClient.enemies.remove(this);
        GamePage.gamePane.getChildren().remove(ship);
    }

    /**
     * Setter and Getters
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
