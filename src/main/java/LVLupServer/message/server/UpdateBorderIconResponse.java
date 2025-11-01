package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.LevelUpClient;
import java.io.Serializable;

public class UpdateBorderIconResponse extends Message<LevelUpClient> implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean success;
    private final String message;

    public UpdateBorderIconResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }

    @Override
    public void apply(LevelUpClient client) {

    }
}
