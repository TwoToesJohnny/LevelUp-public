package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.handlers.ClientRequestHandler;

public class DeleteBorderIconRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;
    private final int id;

    public DeleteBorderIconRequest(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    @Override
    public void apply(LevelUpClient client) {
        new ClientRequestHandler(client).handleDeleteBorderIcon(id);
    }
}
