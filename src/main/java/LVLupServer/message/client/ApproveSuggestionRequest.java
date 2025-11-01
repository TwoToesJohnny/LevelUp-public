package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.handlers.ClientRequestHandler;

public class ApproveSuggestionRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;

    private final int taskId;
    private final String type;
    private final String name;
    private final String category;
    private final int exp;

    public ApproveSuggestionRequest(int taskId, String type, String name,
                                    String category, int exp) {
        this.taskId = taskId;
        this.type = type;
        this.name = name;
        this.category = category;
        this.exp = exp;
    }

    public int getTaskId() { return taskId; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getExp() { return exp; }

    @Override
    public void apply(LevelUpClient client) {
        new ClientRequestHandler(client).handleApproveSuggestion(this);
    }
}