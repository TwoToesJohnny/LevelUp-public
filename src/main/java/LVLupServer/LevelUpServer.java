package LVLupServer;


import LVLupServer.Users.Users;
import LVLupServer.dataBase.VirtualItemRepo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class LevelUpServer {
    private ServerSocket server;
    private int clientNum = 0;

    public static void main(String[] args) throws IOException {
        new LevelUpServer();
    }

    public LevelUpServer() throws IOException {
        server = new ServerSocket(8080);
        Users.setupLeaderBoardScheduler();
        while (true) {
            Socket client = server.accept();

            clientNum++;

            LevelUpClient levelUpClient = new LevelUpClient(client, clientNum);
        }
    }

}
