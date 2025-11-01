package LVLupServer;

import LVLupServer.Users.Users;
import LVLupServer.message.client.Disconnect;
import LVLupServer.message.server.UserDetails;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LevelUpClient {

    private Socket client;

    // I/O streams for communicating with client.
    private ObjectInputStream in;
    private ObjectOutputStream out;

    // Using a thread-safe queue to handle multiple threads adding to the same
    // queue (potentially) and a single thread de-queueing and sending messages
    // across the network/internet.
    private BlockingQueue<Message> outgoingMessages = new LinkedBlockingDeque<>();

    // Reads messages from this specific client.
    private ReadThread readThread;
    // Writes messages to this specific client.
    private WriteThread writeThread;

    // Details about the current connection's client.
    public int clientNum;
    public UserDetails userDetails;


    public LevelUpClient(Socket client,int clientNum){
        this.client = client;
        this.clientNum = clientNum;

        readThread = new ReadThread();
        readThread.start();
    }
    private class ReadThread extends Thread{
        @Override
        public void run() {
            try {
                System.out.println(clientNum+":Starting readThread");

                out = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                in = new ObjectInputStream(client.getInputStream());
                System.out.println(clientNum+":Obtain I/O streams");

                writeThread = new WriteThread();
                writeThread.start();

                Message msg;
                do{
                    msg = (Message) in.readObject();
                    msg.apply(LevelUpClient.this);

                }while(msg.getClass()!= Disconnect.class);


            } catch (Exception e) {
                System.out.println(clientNum + ": Read.Exception: " + e.getMessage());
                e.printStackTrace();
            }finally {

                System.out.println(clientNum+"Stopping writeThread");
                writeThread.interrupt();
            }
        }
    }
    private class WriteThread extends Thread{
        @Override
        public void run() {
            writeThread = this;
            try {

                while(!interrupted()){
                    Message msg = outgoingMessages.take();
                    out.writeObject(msg);
                    out.flush();

                    System.out.println(msg +"->"+ clientNum);
                }

            } catch (Exception e) {
                System.out.println(clientNum + ":WriteThread exception" + e.getMessage());
                e.printStackTrace();
            }finally {
                writeThread = null;
                System.out.println("WriteThread finished");
            }
        }
    }
    public void send(Message msg)  {
        try {
            outgoingMessages.put(msg);
        } catch (InterruptedException e) {
            System.out.println(clientNum + ": Read.Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

