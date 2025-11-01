package LVLupServer.message.server;

import LVLupServer.Message;

import java.io.Serial;

public class InvalidRegistry extends Message {
    @Serial
    private static final long serialVersionUID = 108L;

    public String reason;

    public InvalidRegistry(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.format("Error while registering (%s)",reason);
    }
}
