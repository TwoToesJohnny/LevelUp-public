package LVLupServer.message.server;

import LVLupServer.Message;

import java.io.Serial;

public class InvalidLogin extends Message {
    @Serial
    private static final long serialVersionUID = 102L;

    public String reason;

    public InvalidLogin(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.format("Error while logging in(%s)",reason);
    }
}
