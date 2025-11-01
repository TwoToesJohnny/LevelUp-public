package LVLupServer.message.server;

import LVLupServer.Message;

public class SuggestionActionResponse extends Message {
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;

    public SuggestionActionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}