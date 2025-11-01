package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.BackgroundItemRepository;
import LVLupServer.dataBase.ItemRepository;
import LVLupServer.message.server.store.BackgroundItems;
import LVLupServer.message.server.store.BorderItems;

import java.io.Serial;
import java.sql.SQLException;

public class ItemRequest extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 20L;
    private int userID;

    public ItemRequest(int userId){
        userID = userId;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        ItemRepository itemRepository = new ItemRepository();
        BorderItems borderItems = itemRepository.getBorderItems(userID);

        BackgroundItemRepository backgroundItemRepository = new BackgroundItemRepository();
        BackgroundItems backgroundItems = backgroundItemRepository.getBackgroundItems(userID);

        client.send(borderItems);
        client.send(backgroundItems);
    }
}
