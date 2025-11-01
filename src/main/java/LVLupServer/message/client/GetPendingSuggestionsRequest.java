package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.handlers.ClientRequestHandler;

public class GetPendingSuggestionsRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;

    @Override
    public void apply(LevelUpClient client) {
        new ClientRequestHandler(client).handleGetPendingSuggestions();
    }

    @Override
    public String toString() {
        return "GetPendingSuggestionsRequest{}";
    }
}