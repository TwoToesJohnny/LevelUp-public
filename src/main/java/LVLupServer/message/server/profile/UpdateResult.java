package LVLupServer.message.server.profile;

import LVLupServer.Message;

import java.io.Serial;

public class UpdateResult extends Message {
    @Serial
    private static final long serialVersionUID = 125L;

    public String reason;

    public UpdateResult( String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.format("Error while updating :(%s)",reason);
    }
}
