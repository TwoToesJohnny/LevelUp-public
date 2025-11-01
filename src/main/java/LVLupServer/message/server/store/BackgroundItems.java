package LVLupServer.message.server.store;

import java.io.Serial;
import java.util.List;

public class BackgroundItems extends StoreItems {
    @Serial
    private static final long serialVersionUID = 123L;

    public BackgroundItems(List<StoreItem> backgroundItemList) {
        this.itemList = backgroundItemList;
    }
}
