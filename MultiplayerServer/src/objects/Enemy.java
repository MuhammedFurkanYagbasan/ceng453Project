package objects;


import engine.GameEngine;
import gameConstants.GameConstants;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Enemy CLASS
 */
public class Enemy implements Ship{

    private GameEngine gameEngine;

    private int health;
    private int type;
    private double width;
    private double height;

    private double bulletSpeed;
    private double movementSpeed;

    private double posX;
    private double posY;

    private boolean isDead = false;

    private List<Ship> targets;

    private int movementDir = 1;

    private Timer timer = new Timer();
    private Timer shoot_timer = new Timer();
    private Random generator = new Random();

    private ReentrantLock lock = new ReentrantLock();

    public Enemy(GameEngine ge, int type) {
        this.gameEngine = ge;
        this.type = type;
        setTypeProperties();
        targets = new ArrayList<>();
        posX = GameConstants.WIDTH /2.0;
        posY = 50;
    }

    /**
     * Update properties according to the type
     */
    private void setTypeProperties() {
        switch (type){
            case GameConstants.ENEMY_TYPE_1:
                this.health = GameConstants.ENEMY_TYPE_1_HEALTH_;
                this.width = GameConstants.ENEMY_TYPE_1_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_1_HEIGHT;
                this.bulletSpeed = GameConstants.ENEMY_TYPE_1_BULLET_MOVE_SPEED;
                this.movementSpeed = GameConstants.ENEMY_TYPE_1_MOVEMENT_SPEED;
                break;
            case GameConstants.ENEMY_TYPE_2:
                this.health = GameConstants.ENEMY_TYPE_2_HEALTH_;
                this.width = GameConstants.ENEMY_TYPE_2_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_2_HEIGHT;
                this.bulletSpeed = GameConstants.ENEMY_TYPE_2_BULLET_MOVE_SPEED;
                this.movementSpeed = GameConstants.ENEMY_TYPE_2_MOVEMENT_SPEED;
                break;
            case GameConstants.ENEMY_TYPE_3:
                this.health = GameConstants.ENEMY_TYPE_3_HEALTH_;
                this.width = GameConstants.ENEMY_TYPE_3_WIDTH;
                this.height = GameConstants.ENEMY_TYPE_3_HEIGHT;
                this.bulletSpeed = GameConstants.ENEMY_TYPE_3_BULLET_MOVE_SPEED;
                this.movementSpeed = GameConstants.ENEMY_TYPE_3_MOVEMENT_SPEED;
                break;
        }
    }

    /**
     * Spawns the enemy in the pane.
     */
    public void spawn() {
        moveAround();
        int p = generator.nextInt(20)+1;

        shoot_timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(targets.size() != 0){
                    shoot();
                }
            }
        },0,(int)((0.1 * p + this.bulletSpeed)*1000));
    }

    /**
     * MoveAround moves the enemies with move function in random order.
     */
    private void moveAround() {

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                int p = generator.nextInt(20);
                if (p < 1) {
                    movementDir = generator.nextInt(4) + 1;
                }
                move();
            }
        }, 0, (int)(this.movementSpeed * 1000));
    }

    /**
     * Move the Enemy
     */
    private void move() {
        switch (movementDir) {
            case 1:
                posX += 5;
                break;
            case 2:
                posX -= 5;
                break;
            case 3:
                posY += 5;
                break;
            case 4:
                posY -= 5;
                break;
        }
        if(getPosX()<0) {
            posX = 0;
        } else if(getPosX() + width > GameConstants.WIDTH) {
            posX = GameConstants.WIDTH - width;
        }
        if(getPosY()<0) {
            posY = 0;
        } else if(getPosY()+height>GameConstants.HEIGHT /2.0){
            posY = GameConstants.HEIGHT /2.0 - height;
        }
    }

    /**
     * Damaged reduces the health point of the enemy if health point equals to 0 kill the enemy.
     */
    public void damaged(int damage) {
        lock.lock();
        this.health -=damage;
        lock.unlock();
        if(health <= 0){
            kill();
        }
    }

    /**
     * Kill itself
     */
    public void kill() {
        timer.cancel();
        shoot_timer.cancel();
        isDead = true;

        if(!gameEngine.isThereAnyEnemy()) {
            gameEngine.enemySlotEmpty();
        }
    }

    /**
     * Shoot one bullet.
     */
    public void shoot() {
        Bullet bullet = new Bullet(this.gameEngine,this.type+1,
                getPosX() + width/2,
                getPosY() + height,
                this);

        bullet.setTargets(this.targets);

        bullet.shoot(GameConstants.BULLET_DIR_DOWN);
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

    public void removeFromTargets(Ship ship) {
        this.targets.remove(ship);
    }

    public int getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setTargets(List<Ship> targets) {
        this.targets = targets;
    }

}
