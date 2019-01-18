package engine;

import gameConstants.GameConstants;
import gameConstants.StringConstants;
import gameobjects.Bullet;
import gameobjects.Enemy;
import gameobjects.Player;
import gameobjects.Ship;
import networkclient.NetworkClient;
import pages.GamePage;
import pages.MultiplayerWaitingPage;
import pages.ResultPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main Game operations are taken care of in this class
 */

public class GameEngine {

    static public Player player;

    static private int level = 1;
    static private int slotNum = 0;

    public static List<Ship> enemies = new ArrayList<>();
    private static List<Ship> players = new ArrayList<>();
    private static List<Bullet> bullets = new ArrayList<>();

    /**
     * locks for simultaneous access to Lists
     */
    static public ReentrantLock enemiesLock = new ReentrantLock();
    static private ReentrantLock playersLock = new ReentrantLock();
    static private ReentrantLock bulletsLock = new ReentrantLock();

    /**
     * Start the Game:
     *
     * - generate the player and add to players list
     * - spawn the generated player in the game
     * - generate first enemy slot
     */
    public static void start() {

        player = new Player(GameConstants.PLAYER_TYPE_1);
        player.setTargets(enemies);
        player.spawn();

        players.add(player);

        enemySlotEmpty();
    }

    /**
     * Manage coordination between Enemy slots and game_level
     */
    public static void enemySlotEmpty() {
        slotNum++;
        switch (slotNum){
            case 1:
                GamePage.updateLevelLabel(level);
                generateEnemies(GameConstants.LEVEL_1_WAVE_1_ENEMY_NUM,GameConstants.PLAYER_TYPE_1);
                break;
            case 2:
                generateEnemies(GameConstants.LEVEL_1_WAVE_2_ENEMY_NUM,GameConstants.PLAYER_TYPE_1);
                generateEnemies(GameConstants.LEVEL_1_WAVE_2_ENEMY_NUM,GameConstants.PLAYER_TYPE_2);
                break;
            case 3:
                GamePage.updateLevelLabel(++level);
                generateEnemies(GameConstants.LEVEL_2_WAVE_1_ENEMY_NUM, GameConstants.PLAYER_TYPE_2);
                break;
            case 4:
                GamePage.updateLevelLabel(++level);
                generateEnemies(GameConstants.LEVEL_3_WAVE_1_ENEMY_NUM,GameConstants.PLAYER_TYPE_3);
                break;
            case 5:
                GamePage.updateLevelLabel(++level);
                MultiplayerWaitingPage.show();
                NetworkClient.connectToServer();
                break;
            default:
                ResultPage.show(StringConstants.SCORE_LABEL + player.getScore());
        }
    }

    /**
     * Generates given number of Enemies with given type.
     * and spawns generated enemies
     */
    private static void generateEnemies(int number, int type) {
        for(int i=0; i<number; i++) {
            Enemy e = new Enemy(type);
            addToEnemies(e);
            e.spawn();
            e.setTargets(players);
        }
    }

    /**
     * Clears the lists for next game
     */
    static public void clearGame() {
        level = 1; slotNum = 0;

        enemiesLock.lock();
        for(int i = enemies.size() - 1; i >= 0; i--) {
            enemies.get(i).kill();
        }
        enemiesLock.unlock();

        playersLock.lock();
        for(int i = players.size() - 1; i >= 0; i--) {
            players.get(i).kill();
        }
        playersLock.unlock();

        bulletsLock.lock();
        for(int i = bullets.size() - 1; i >= 0; i--) {
            bullets.get(i).kill();
        }

        bulletsLock.unlock();
    }

    /**
     * Lists Operations with Locks
     */
    static private void addToEnemies(Enemy e) {
        enemiesLock.lock();
        enemies.add(e);
        enemiesLock.unlock();
    }

    static public void removeFromEnemies(Enemy e) {
        enemiesLock.lock();
        enemies.remove(e);
        enemiesLock.unlock();
    }

    static public void addToBullets(Bullet b) {
        bulletsLock.lock();
        bullets.add(b);
        bulletsLock.unlock();
    }

    static public void removeFromBulets(Bullet b) {
        bulletsLock.lock();
        bullets.remove(b);
        bulletsLock.unlock();
    }

    static public void addToPlayers(Player p) {
        playersLock.lock();
        players.add(p);
        playersLock.unlock();
    }

    static public void removeFromPlayers(Player p) {
        playersLock.lock();
        players.remove(p);
        playersLock.unlock();
    }

}
