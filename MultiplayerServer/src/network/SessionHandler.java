package network;


import engine.GameEngine;
import gameConstants.GameConstants;
import gameConstants.ServerConstants;
import objects.Bullet;
import objects.Enemy;
import objects.Player;
import objects.Ship;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Handler for each game between 2 players
 */
public class SessionHandler implements Runnable{

    private GameEngine gameEngine = new GameEngine();

    private Socket player1;
    private Socket player2;

    private DataOutputStream toPlayer1;
    private DataInputStream fromPlayer1;
    private DataOutputStream toPlayer2;
    private DataInputStream fromPlayer2;

    Player p1, p2;

    public SessionHandler(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Run Session Handler
     */
    public void run() {
        try {
            // Create data input and output streams
            fromPlayer1 = new DataInputStream(
                    player1.getInputStream());
            toPlayer1 = new DataOutputStream(
                    player1.getOutputStream());
            fromPlayer2 = new DataInputStream(
                    player2.getInputStream());
            toPlayer2 = new DataOutputStream(
                    player2.getOutputStream());

            toPlayer1.writeInt(ServerConstants.PLAYER1);
            toPlayer2.writeInt(ServerConstants.PLAYER2);

            int player1Hp = fromPlayer1.readInt();
            int player1Score = fromPlayer1.readInt();
            int player1Type = fromPlayer1.readInt();

            int player2Hp = fromPlayer2.readInt();
            int player2Score = fromPlayer2.readInt();
            int player2Type = fromPlayer2.readInt();

            p1 = new Player(gameEngine, player1Type);
            p2 = new Player(gameEngine, player2Type);
            gameEngine.addPlayers(p1, p2);

            p1.setHp(player1Hp); p2.setHp(player2Hp);
            p1.setScore(player1Score); p2.setScore(player2Score);
            p1.setType(player1Type); p2.setType(player2Type);

            // Start The Game
            gameEngine.start();

            sendEnemies();

            // Send and Receive packages
            while (true) {
                try {

                    toPlayer1.writeBoolean(gameEngine.finished);
                    toPlayer2.writeBoolean(gameEngine.finished);

                    if(gameEngine.finished) {
                        gameFinished();
                        break;
                    }

                    readPlayersInfo();

                    updateEnemies();
                    gameEngine.removeDeadEnemies();

                    sendBullets();
                    gameEngine.removeDeadBullets();

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Game Finished, send results
     */
    private void gameFinished() {
        int player1_result, player2_result;
        if(p1.isDead()) {
            player1_result = GameConstants.RESULT_DEFEATED;
            player2_result = GameConstants.RESULT_WON;
            p2.setScore(p2.getScore() + 20);
        } else if(p2.isDead()) {
            player1_result = GameConstants.RESULT_WON;
            player2_result = GameConstants.RESULT_DEFEATED;
            p1.setScore(p1.getScore() + 20);
        } else {
            if (p1.getScore() > p2.getScore()) {
                player1_result = GameConstants.RESULT_WON;
                player2_result = GameConstants.RESULT_DEFEATED;
            } else if (p1.getScore() < p2.getScore()) {
                player1_result = GameConstants.RESULT_DEFEATED;
                player2_result = GameConstants.RESULT_WON;
            } else {
                player1_result = GameConstants.RESULT_DRAW;
                player2_result = GameConstants.RESULT_DRAW;
            }
        }
        try{
            toPlayer1.writeInt(player1_result);
            toPlayer2.writeInt(player2_result);

            toPlayer1.writeInt(p1.getScore());
            toPlayer2.writeInt(p2.getScore());

            player1.close();
            player2.close();
        } catch(Exception e) {
            System.out.println("Error: gameFinished()");
        }
    }

    /**
     *
     */
    private void readPlayersInfo() {
        try {
            double player1PosX = fromPlayer1.readDouble();
            double player1PosY = fromPlayer1.readDouble();

            double player2PosX = fromPlayer2.readDouble();
            double player2PosY = fromPlayer2.readDouble();

            p1.setPosX(player1PosX);
            p1.setPosY(player1PosY);
            p2.setPosX(player2PosX);
            p2.setPosY(player2PosY);

            toPlayer1.writeDouble(player2PosX);
            toPlayer1.writeDouble(player2PosY);

            toPlayer1.writeInt(p1.getHealth());
            toPlayer1.writeInt(p1.getScore());

            toPlayer2.writeDouble(player1PosX);
            toPlayer2.writeDouble(player1PosY);

            toPlayer2.writeInt(p2.getHealth());
            toPlayer2.writeInt(p2.getScore());
        } catch(Exception e) {
            System.out.println("Error: readPlayersInfo()");
        }
    }

    private void sendEnemies() {
        try {
            int enemiesSize = gameEngine.enemies.size();
            toPlayer1.writeInt(enemiesSize);
            toPlayer2.writeInt(enemiesSize);

            for (int i = 0; i < enemiesSize; i++) {

                Ship enemy = gameEngine.enemies.get(i);

                writeEnemiesToPlayer(enemy, toPlayer1);
                toPlayer1.writeInt(enemy.getType());

                writeEnemiesToPlayer(enemy, toPlayer2);
                toPlayer2.writeInt(enemy.getType());
            }
        } catch(Exception e) {
            System.out.println("Error: sendEnemies()");
        }
    }

    /**
     * Send Bullets Information
     */
    private void sendBullets() {
        try {
            int bulletsSize = gameEngine.bullets.size();
            toPlayer1.writeInt(bulletsSize);
            toPlayer2.writeInt(bulletsSize);

            for (int i = 0; i < bulletsSize; i++) {
                Bullet bullet = gameEngine.bullets.get(i);

                writeBulletsToPlayer(bullet, toPlayer1);
                writeBulletsToPlayer(bullet, toPlayer2);

                if(bullet.isNew()) {
                    bullet.setNew(false);
                }
            }
        } catch(Exception e) {
            System.out.println("Error: sendBullets()");
        }
    }

    private void writeBulletsToPlayer(Bullet bullet, DataOutputStream toPlayer) {
        try {
            toPlayer.writeDouble(bullet.getPosX());
            toPlayer.writeDouble(bullet.getPosY());
            toPlayer.writeBoolean(bullet.isDead());
            toPlayer.writeBoolean(bullet.isNew());
            toPlayer.writeInt(bullet.getType());
        } catch(Exception e) {
            System.out.println("Error: writeBulletsToPlayer()");
        }
    }

    /**
     * Send Enemies information
     */
    private void updateEnemies() {
        try {
            int enemiesSize = gameEngine.enemies.size();
            toPlayer1.writeInt(enemiesSize);
            toPlayer2.writeInt(enemiesSize);

            for (int i = 0; i < enemiesSize; i++) {
                Ship enemy = gameEngine.enemies.get(i);

                writeEnemiesToPlayer(enemy, toPlayer1);
                writeEnemiesToPlayer(enemy, toPlayer2);
            }
        } catch(Exception e) {
            System.out.println("Error: updateEnemies()");
        }
    }

    private void writeEnemiesToPlayer(Ship enemy, DataOutputStream toPlayer) {
        try {
            toPlayer.writeDouble(enemy.getPosX());
            toPlayer.writeDouble(enemy.getPosY());
            toPlayer.writeBoolean(enemy.isDead());
        } catch(Exception e) {
            System.out.println("Error: writeEnemiesToPlayer()");
        }
    }

}
