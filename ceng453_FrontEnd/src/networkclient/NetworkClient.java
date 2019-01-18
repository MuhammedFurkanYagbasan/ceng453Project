package networkclient;

import engine.GameEngine;
import gameConstants.GameConstants;
import gameConstants.NetworkConstants;
import gameConstants.StringConstants;
import javafx.application.Platform;
import pages.GamePage;
import pages.MultiplayerWaitingPage;
import pages.ResultPage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * NetworkClient where the server connection is handled
 * and multiplayer level is managed
 */
public class NetworkClient {

    private static NetworkPlayer player2;

    private static DataInputStream fromServer;
    private static DataOutputStream toServer;

    static List<NetworkEnemy> enemies = new ArrayList<>();
    static List<NetworkBullet> bullets = new ArrayList<>();

    /**
     * Establish server connection
     */
    public static void connectToServer() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(NetworkConstants.multiplayerServerHost, NetworkConstants.multiplayerServerPort);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

            MultiplayerWaitingPage.changeWaitingStatus(StringConstants.SERVER_CONNECTION_SUCCESS);

            runCycle();

        } catch (Exception ex) {
            ex.printStackTrace();
            MultiplayerWaitingPage.changeWaitingStatus(StringConstants.SERVER_CONNECTION_FAIL);
        }
    }

    /**
     * Server and client network communication
     */
    private static void runCycle() {
        new Thread(() -> {
            try {
                int player = fromServer.readInt();

                Platform.runLater(GamePage::show);

                toServer.writeInt(GameEngine.player.getHealth());
                toServer.writeInt(GameEngine.player.getScore());
                toServer.writeInt(GameEngine.player.getType());

                getEnemiesFirst();

                Platform.runLater(() -> {
                    player2 = new NetworkPlayer(0,0, false, 1);
                });

                // Communication Loop
                while (true) {
                    boolean isGameFinished = fromServer.readBoolean();
                    if(isGameFinished) {
                        int result = fromServer.readInt();
                        int score = fromServer.readInt();
                        GameEngine.player.setScore(score);

                        String msg;
                        if(result == GameConstants.RESULT_WON) {
                            msg = StringConstants.RESULT_WON;
                        } else if(result == GameConstants.RESULT_DEFEATED) {
                            msg = StringConstants.RESULT_DEFEATED;
                        } else {
                            msg = StringConstants.RESULT_DRAW;
                        }
                        Platform.runLater(()-> {
                            clearScreen();
                            ResultPage.show( msg + "\n" + StringConstants.SCORE_LABEL + GameEngine.player.getScore());
                        });

                        break;
                    }

                    sendPosition();
                    receive();

                    drawEnemies();
                    drawBullets();
                    drawPlayer2();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Clear Lists
     */
    private static void clearScreen() {
        player2.kill();
        int enemiesSize = enemies.size();
        for (int i = enemiesSize -1; i >= 0; i--) {
            enemies.get(i).kill();
        }
        int bulletsSize = bullets.size();
        for (int i = bulletsSize -1; i >= 0 ; i--) {
            bullets.get(i).kill();
        }
    }

    /**
     * Package to construct enemies
     */
    private static void getEnemiesFirst() {
        try {
            int enemiesSize = fromServer.readInt();
            for (int i = 0; i < enemiesSize; i++) {
                double eposX = fromServer.readDouble();
                double eposY = fromServer.readDouble();
                boolean eisDead = fromServer.readBoolean();
                int etype = fromServer.readInt();

                Platform.runLater(() -> {
                    NetworkEnemy e = new NetworkEnemy(eposX, eposY, eisDead, etype);
                    enemies.add(e);
                });
            }
        } catch(Exception e) {
            System.out.println("Error: getEnemiesFirst");
        }
    }

    /**
     * Send position of the player to server
     */
    private static void sendPosition() {
        try {
            toServer.writeDouble(GameEngine.player.getPosX());
            toServer.writeDouble(GameEngine.player.getPosY());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private static void receive() {
        try {
            double player2PosX = fromServer.readDouble();
            double player2PosY = fromServer.readDouble();

            int playerHp = fromServer.readInt();
            int playerScore = fromServer.readInt();

            Platform.runLater(() -> {
                player2.setPosX(player2PosX);
                player2.setPosY(player2PosY);

                GameEngine.player.setHealth(playerHp);
                GameEngine.player.setScore(playerScore);
                GamePage.updateHpLabel(playerHp);
                GamePage.updateScoreLabel(playerScore);
            });

            getEnemiesLoop();
            getBulletsLoop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Bullets information to update them
     */
    private static void getBulletsLoop() {
        try {
            int bulletsSize = fromServer.readInt();
            for (int i = 0; i < bulletsSize; i++) {
                double bposX = fromServer.readDouble();
                double bposY = fromServer.readDouble();
                boolean bisDead = fromServer.readBoolean();
                boolean bisNew = fromServer.readBoolean();
                int btype = fromServer.readInt();

                if(bisNew) {
                    Platform.runLater(() -> {
                        NetworkBullet b = new NetworkBullet(bposX, bposY, bisDead, btype);
                        bullets.add(b);
                    });
                }

                final int j = i;
                Platform.runLater(() -> {
                    bullets.get(j).setPosX(bposX);
                    bullets.get(j).setPosY(bposY);
                    bullets.get(j).setDead(bisDead);
                });

            }
        } catch(Exception e) {
            System.out.println("Error: getEnemiesFirst");
        }
    }

    /**
     * Get enemies information to update them
     */
    private static void getEnemiesLoop() {
        try {
            int enemiesSize = fromServer.readInt();
            for(int i = 0; i<enemiesSize; i++) {
                double eposX = fromServer.readDouble();
                double eposY = fromServer.readDouble();
                boolean eisDead = fromServer.readBoolean();

                final int j = i;
                Platform.runLater(() -> {
                    enemies.get(j).setPosX(eposX);
                    enemies.get(j).setPosY(eposY);
                    enemies.get(j).setDead(eisDead);
                });
            }
        } catch(Exception e) {
            System.out.println("Error: getEnemiesFirst");
        }
    }

    /**
     * Draw enemies, remove if dead
     */
    private static void drawEnemies() {
        int n = enemies.size();

        for(int i=n-1; i>=0; i--) {
            final int j = i;
            Platform.runLater(() -> {
                enemies.get(j).reDraw();
            });
        }
    }

    /**
     * Draw Bullets, remove if dead
     */
    private static void drawBullets() {
        int n = bullets.size();

        for(int i=n-1; i>=0; i--) {
            final int j = i;
            Platform.runLater(() -> {
                bullets.get(j).reDraw();
            });
        }
    }

    /**
     * Draw player2 in the window
     */
    private static void drawPlayer2() {
        Platform.runLater(() -> {
            player2.reDraw();
        });
    }

}
