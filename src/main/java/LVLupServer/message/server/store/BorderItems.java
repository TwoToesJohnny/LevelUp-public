package LVLupServer.message.server.store;

import java.io.Serial;
import java.util.List;

public class BorderItems extends StoreItems {
    @Serial
    private static final long serialVersionUID = 123L;


    public BorderItems(List<StoreItem> borderItemList) {
        this.itemList = borderItemList;
    }
}
