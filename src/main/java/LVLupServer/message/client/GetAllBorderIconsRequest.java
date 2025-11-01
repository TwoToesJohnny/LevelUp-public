package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;

public class GetAllBorderIconsRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;

    @Override
    public void apply(LevelUpClient client) {
        new LVLupServer.handlers.ClientRequestHandler(client).handleGetAllBorderIcons();
    }

    @Override
    public String toString() {
        return "GetAllBorderIconsRequest{}";
    }
}
