package LVLupServer;

import LVLupServer.Users.Users;
import LVLupServer.message.client.Disconnect;
import LVLupServer.message.server.UserDetails;
import org.java_websocket.WebSocket;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LevelUpClient {

    private WebSocket connection;

    // Using a thread-safe queue to handle outgoing messages
    private BlockingQueue<Message> outgoingMessages = new LinkedBlockingDeque<>();

    // Writes messages to this specific client.
    private WriteThread writeThread;

    // Details about the current connection's client.
    public int clientNum;
    public UserDetails userDetails;

    public LevelUpClient(WebSocket connection, int clientNum) {
        this.connection = connection;
        this.clientNum = clientNum;

        writeThread = new WriteThread();
        writeThread.start();
    }

    /**
     * Handle incoming binary message (serialized Java object)
     */
    public void handleMessage(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(bais);

            Message msg = (Message) ois.readObject();
            msg.apply(this);

            if (msg.getClass() == Disconnect.class) {
                cleanup();
            }

        } catch (Exception e) {
            System.out.println(clientNum + ": Message handling exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private class WriteThread extends Thread {
        @Override
        public void run() {
            try {
                while (!interrupted() && connection.isOpen()) {
                    Message msg = outgoingMessages.take();

                    // Serialize message to byte array
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(msg);
                    oos.flush();

                    byte[] data = baos.toByteArray();
                    connection.send(data);

                    System.out.println(msg + "->" + clientNum);
                }
            } catch (Exception e) {
                System.out.println(clientNum + ": WriteThread exception: " + e.getMessage());
                e.printStackTrace();
            } finally {
                System.out.println("WriteThread finished for client " + clientNum);
            }
        }
    }

    public void send(Message msg) {
        try {
            outgoingMessages.put(msg);
        } catch (InterruptedException e) {
            System.out.println(clientNum + ": Send exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cleanup() {
        System.out.println("Cleaning up client " + clientNum);
        if (writeThread != null) {
            writeThread.interrupt();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
}