package networkclient;


import gameConstants.GameConstants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pages.GamePage;

/**
 * NetworkPlayer CLASS
 */
public class NetworkPlayer {

    private double posX;
    private double posY;
    private boolean isDead;
    private int type;
    private int score;
    private double width;
    private double height;
    private Color color;
    private Rectangle ship;

    NetworkPlayer(double posX, double posY, boolean isDead, int type) {
        this.posX = posX;
        this.posY = posY;
        this.isDead = isDead;
        this.type = type;
        setTypeProperties();
        this.score = 0;

        ship = new Rectangle(posX,posY,width,height);
        ship.setFill(color);
        GamePage.gamePane.getChildren().add(ship);
    }

    private void setTypeProperties() {
        switch (type){
            case GameConstants.PLAYER_TYPE_1:
                this.color = GameConstants.PLAYER_TYPE_1_COLOR;
                this.width = GameConstants.PLAYER_TYPE_1_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_1_HEIGHT;
                break;
            case GameConstants.PLAYER_TYPE_2:
                this.color = GameConstants.PLAYER_TYPE_2_COLOR;
                this.width = GameConstants.PLAYER_TYPE_2_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_2_HEIGHT;
                break;
            case GameConstants.PLAYER_TYPE_3:
                this.color = GameConstants.PLAYER_TYPE_3_COLOR;
                this.width = GameConstants.PLAYER_TYPE_3_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_3_HEIGHT;
                break;
        }
    }

    /**
     * Updates Player status
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
     * Removes from the pane
     */
    void kill() {
        GamePage.gamePane.getChildren().remove(ship);
    }

    /**
     * Getters and Setters
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
