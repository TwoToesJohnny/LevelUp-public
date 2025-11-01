package LVLupServer;

import LVLupServer.Users.Users;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class LevelUpServer extends WebSocketServer {
    private int clientNum = 0;
    private ConcurrentHashMap<WebSocket, LevelUpClient> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        new LevelUpServer();
    }

    public LevelUpServer() throws IOException {
        super(new InetSocketAddress(8080));
        Users.setupLeaderBoardScheduler();
        this.start();
        System.out.println("WebSocket server started on port 8080");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        clientNum++;
        LevelUpClient levelUpClient = new LevelUpClient(conn, clientNum);
        clients.put(conn, levelUpClient);
        System.out.println("New client connected: " + clientNum);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        LevelUpClient client = clients.remove(conn);
        if (client != null) {
            client.cleanup();
        }
        System.out.println("Client disconnected: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Not used - we're using binary messages
        System.out.println("Received unexpected text message: " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        LevelUpClient client = clients.get(conn);
        if (client != null) {
            byte[] data = new byte[message.remaining()];
            message.get(data);
            client.handleMessage(data);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
        ex.printStackTrace();
        if (conn != null) {
            LevelUpClient client = clients.get(conn);
            if (client != null) {
                client.cleanup();
            }
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully!");
        setConnectionLostTimeout(100);
    }
}