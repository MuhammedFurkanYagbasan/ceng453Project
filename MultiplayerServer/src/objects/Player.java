package objects;


import engine.GameEngine;
import gameConstants.GameConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Player CLASS
 */
public class Player implements Ship {

    private GameEngine gameEngine;

    private int hp;
    private int score;
    private double width;
    private double height;
    private int type;
    private double bulletSpeed;

    private double posX;
    private double posY;

    private boolean isDead = false;

    private List<Ship> targets;

    private Timer shoot_timer = new Timer();

    private ReentrantLock lock = new ReentrantLock();

    // Constructor
    public Player(GameEngine ge, int type) {
        this.gameEngine = ge;
        this.type = type;
        setTypeProperties();
        targets = new ArrayList<>();

        this.posX = 0;
        this.posY = GameConstants.HEIGHT - height;
    }

    /**
     * Sets properties of the Player according to its type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.PLAYER_TYPE_1:
                this.width = GameConstants.PLAYER_TYPE_1_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_1_HEIGHT;
                this.bulletSpeed = GameConstants.PLAYER_TYPE_1_BULLET_SPEED;
                break;
            case GameConstants.PLAYER_TYPE_2:
                this.width = GameConstants.PLAYER_TYPE_2_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_2_HEIGHT;
                this.bulletSpeed = GameConstants.PLAYER_TYPE_2_BULLET_SPEED;
                break;
            case GameConstants.PLAYER_TYPE_3:
                this.width = GameConstants.PLAYER_TYPE_3_WIDTH;
                this.height = GameConstants.PLAYER_TYPE_3_HEIGHT;
                this.bulletSpeed = GameConstants.PLAYER_TYPE_3_BULLET_SPEED;
                break;
        }
    }

    /**
     * Spawn creates a player in pane.
     */
    public void spawn() {
        shoot_timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(targets.size() != 0) {
                    shoot();
                }
            }
        },0,(int)(this.bulletSpeed*1000));
    }

    /**
     * Damaged reduces the health point of the player, if health point equal to 0 kill the Player.
     */
    public void damaged(int damage) {
        lock.lock();
        this.hp-=damage;
        lock.unlock();
        if(this.hp<=0){
            kill();
        }
    }

    /**
     * Kills itself
     */
    public void kill() {
        shoot_timer.cancel();
        this.isDead = true;
        gameEngine.finished = true;
    }

    /**
     * Shoot one bullet.
     */
    public void shoot() {
        if(!isDead){
            Bullet bullet = new Bullet(this.gameEngine, GameConstants.BULLET_TYPE_1,
                    getPosX()+this.width/2,
                    getPosY(),
                    this);

            bullet.setTargets(this.targets);

            bullet.shoot(GameConstants.BULLET_DIR_UP);
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
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getHealth() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        setTypeProperties();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
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
