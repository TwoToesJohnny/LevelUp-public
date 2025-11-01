package LVLupServer.message.server;

import LVLupServer.Message;

import java.io.Serial;

public class UpdateXP extends Message {
    @Serial
    private static final long serialVersionUID = 112L;
    public int userXP;

    public UpdateXP(int userXP) {
        this.userXP = userXP;
    }
}
