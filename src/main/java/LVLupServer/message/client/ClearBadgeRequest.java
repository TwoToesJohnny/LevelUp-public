package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.handlers.ClientRequestHandler;

import java.io.Serial;

public class ClearBadgeRequest extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int level;

    public ClearBadgeRequest(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public void apply(LevelUpClient client) {
        new ClientRequestHandler(client).handleClearBadge(level);
    }

    @Override
    public String toString() {
        return "ClearBadgeRequest{level=" + level + "}";
    }
}