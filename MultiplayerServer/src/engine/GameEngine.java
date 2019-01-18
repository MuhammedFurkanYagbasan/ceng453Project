package engine;


import gameConstants.GameConstants;
import objects.Bullet;
import objects.Enemy;
import objects.Player;
import objects.Ship;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * GameEngine that manages the game
 */
public class GameEngine {

    public List<Ship> players = new ArrayList<>();
    public List<Ship> enemies = new ArrayList<>();
    public List<Bullet> bullets = new ArrayList<>();

    public boolean finished = false;

    ReentrantLock bulletsLock = new ReentrantLock();
    ReentrantLock enemiesLock = new ReentrantLock();

    private int slotNo;

    public GameEngine() {}

    public void addPlayers(Player p1, Player p2) {
        players.add(p1);
        players.add(p2);
    }

    /**
     * Start the game
     */
    public void start() {
        slotNo = 0;

        players.get(0).setTargets(enemies);
        players.get(1).setTargets(enemies);

        enemySlotEmpty();

        players.get(0).spawn();
        players.get(1).spawn();

    }

    public void enemySlotEmpty() {
        slotNo++;
        switch (slotNo){
            case 1:
                generateEnemies(3, GameConstants.ENEMY_TYPE_3);
                break;
                default:
                    finished = true;

        }
    }

    /**
     * GenerateEnemies creates given number of aliens with given type.
     */
    public void generateEnemies(int number, int type){
        for(int i=0; i<number; i++) {
            Enemy e = new Enemy(this, type);
            addToEnemies(e);
            e.setTargets(players);
            e.spawn();
        }
    }


    /**
     * List operations with lock
     */

    public void removeFromBullets(Bullet b) {

        bulletsLock.lock();
        bullets.remove(b);
        bulletsLock.unlock();

    }

    public void addToBullets(Bullet b) {
        bulletsLock.lock();
        bullets.add(b);
        bulletsLock.unlock();
    }

    public void removeFromEnemies(Ship e) {

        enemiesLock.lock();
        enemies.remove(e);
        enemiesLock.unlock();

    }

    public void addToEnemies(Ship e) {
        enemiesLock.lock();
        enemies.add(e);
        enemiesLock.unlock();
    }

    public void removeDeadEnemies() {
        enemiesLock.lock();
        int enemiesSize = enemies.size();
        for (int i = enemiesSize - 1; i >=0; i--) {
            Ship e = enemies.get(i);
            if(e.isDead()) {
                enemies.remove(e);
            }
        }
        enemiesLock.unlock();
    }

    public void removeDeadBullets() {
        bulletsLock.lock();
        int bulletsSize = bullets.size();
        for (int i = bulletsSize - 1; i >=0; i--) {
            Bullet b = bullets.get(i);
            if(b.isDead()) {
                bullets.remove(b);
            }
        }
        bulletsLock.unlock();
    }

    public boolean isThereAnyEnemy() {
        try{
            enemiesLock.lock();
            int n = enemies.size();

            for(int i = 0; i<n; i++) {
                if(!enemies.get(i).isDead()) {
                    return true;
                }
            }
            return false;
        } finally {
            enemiesLock.unlock();
        }
    }
}
