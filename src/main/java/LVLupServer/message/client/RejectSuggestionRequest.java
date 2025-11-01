package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.handlers.ClientRequestHandler;

public class RejectSuggestionRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;

    private final int taskId;
    private final String type;

    public RejectSuggestionRequest(int taskId, String type) {
        this.taskId = taskId;
        this.type = type;
    }

    public int getTaskId() { return taskId; }
    public String getType() { return type; }

    @Override
    public void apply(LevelUpClient client) {
        new ClientRequestHandler(client).handleRejectSuggestion(this);
    }
}