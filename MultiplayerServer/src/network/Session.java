package network;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Room for every 2 Player
 */
public class Session {

    private static int sessionNo = 1;

    public static void start(ServerSocket serverSocket) {

        try {
            System.out.println(new Date() + ": Wait for players to join session " + sessionNo);

            // Connect to player 1
            Socket player1 = serverSocket.accept();

            System.out.println(new Date() + ": Player 1 joined session " + sessionNo);
            System.out.println("Player 1's IP address" + player1.getInetAddress().getHostAddress());

            // Connect to player 2
            Socket player2 = serverSocket.accept();

            System.out.println(new Date() + ": Player 2 joined session " + sessionNo);
            System.out.println("Player 2's IP address" + player2.getInetAddress().getHostAddress());

            System.out.println(new Date() +
                    ": Start a thread for session " + sessionNo++);

            new Thread(new SessionHandler(player1, player2)).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
