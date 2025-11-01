package LVLupServer.message.server;

import LVLupServer.Message;

import java.io.Serial;

public class ClearBadgeResponse extends Message {
    @Serial
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;

    public ClearBadgeResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ClearBadgeResponse{" + "success=" + success + ", message='" + message + '\'' + '}';
    }
}
