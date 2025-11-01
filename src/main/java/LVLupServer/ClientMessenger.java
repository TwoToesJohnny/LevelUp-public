package LVLupServer;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientMessenger {
    private final ObjectOutputStream out;
    private final int clientNum;

    public ClientMessenger(ObjectOutputStream out, int clientNum) {
        this.out = out;
        this.clientNum = clientNum;
    }

    public synchronized void sendMessage(Object message) {
        try {
            out.writeObject(message);
            out.flush();
            System.out.println("Message sent to client " + clientNum + ": " + message.getClass().getSimpleName());
        } catch (IOException e) {
            System.out.println("Error sending message to client " + clientNum + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
