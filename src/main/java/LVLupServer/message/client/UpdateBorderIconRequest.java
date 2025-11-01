package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.shared.BorderIconDTO;
import LVLupServer.handlers.ClientRequestHandler;

public class UpdateBorderIconRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;
    private final BorderIconDTO icon;

    public UpdateBorderIconRequest(BorderIconDTO icon) {
        this.icon = icon;
    }

    public BorderIconDTO getIcon() { return icon; }

    @Override
    public void apply(LevelUpClient client) {
        // Create handler and process on server
        new ClientRequestHandler(client).handleUpdateBorderIcon(icon);
    }
}
