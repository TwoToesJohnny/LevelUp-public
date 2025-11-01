package LVLupServer.message.server.store;

import LVLupServer.Message;

import java.io.Serial;

public class BackgroundItem extends StoreItem {
    @Serial
    private static final long serialVersionUID = 125L;

    public String backgroundImage;

    public BackgroundItem(boolean owned, String item_name, int cost, int itemID, String backgroundImage) {
        this.owned = owned;
        this.item_name = item_name;
        this.cost = cost;
        this.item_ID = itemID;
        this.backgroundImage = backgroundImage;
    }
}
