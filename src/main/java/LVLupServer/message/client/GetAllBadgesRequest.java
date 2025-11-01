package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;

public class GetAllBadgesRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;

    @Override
    public void apply(LevelUpClient client) {
        new LVLupServer.handlers.ClientRequestHandler(client).handleGetAllBadges();
    }


    @Override
    public String toString() {
        return "GetAllBadgesRequest{}";
    }
}
