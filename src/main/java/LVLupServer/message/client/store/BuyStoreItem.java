package LVLupServer.message.client.store;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserBuyItem;
import LVLupServer.dataBase.BackgroundItemRepository;
import LVLupServer.dataBase.ItemRepository;
import LVLupServer.message.server.UserDetails;
import LVLupServer.message.server.store.*;

import java.io.Serial;
import java.sql.SQLException;

public class BuyStoreItem extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 23L;

    private final int userID;
    private final StoreItem storeItem;

    public BuyStoreItem(int userID, StoreItem storeItem) {
        this.userID = userID;
        this.storeItem = storeItem;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserDetails user = client.userDetails;
        UserBuyItem userBuyItem = new UserBuyItem(client);
        userBuyItem.updateUserGems(storeItem);

        if(storeItem instanceof BorderItem){
            ItemRepository itemRepository = new ItemRepository();
            itemRepository.purchaseBorderItem(userID, storeItem.item_ID);

            BorderItems borderItems = itemRepository.getBorderItems(userID);
            client.send(borderItems);
        }
        else{
            BackgroundItemRepository backgroundItemRepository = new BackgroundItemRepository();
            backgroundItemRepository.purchaseBackgroundItem(userID, storeItem.item_ID);

            BackgroundItems backgroundItems = backgroundItemRepository.getBackgroundItems(userID);
            client.send(backgroundItems);
        }
        client.send(user);
    }
}
