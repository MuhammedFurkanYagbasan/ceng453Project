package network;

import gameConstants.ServerConstants;
import java.net.ServerSocket;
import java.util.Date;

/**
 * START SERVER SOCKET
 */
public class Server {

    public static void start() {
        new Thread(()-> {
            try {
                ServerSocket serverSocket = new ServerSocket(ServerConstants.serverPort);
                System.out.println(new Date() + ": Server started at socket 8000");

                while(true) {
                    Session.start(serverSocket);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
