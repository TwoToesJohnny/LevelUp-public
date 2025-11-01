package LVLupServer.message.server;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;

public class VirtualItemAddedResponse extends Message<LevelUpClient> {
    private static final long serialVersionUID = 112L;

    private boolean success;
    private String message;

    public VirtualItemAddedResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public void apply(LevelUpClient client) {
        System.out.println("Server: " + message);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
