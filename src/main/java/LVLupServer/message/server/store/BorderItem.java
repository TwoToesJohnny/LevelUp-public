package LVLupServer.message.server.store;

import LVLupServer.Message;

import java.io.Serial;

public class BorderItem extends StoreItem {
    @Serial
    private static final long serialVersionUID = 124L;

    public byte [] BorderICon;
    public BorderItem(int item_ID, byte[] borderICon, boolean owned, String item_name, int cost) {
        this.item_ID = item_ID;
        this.BorderICon = borderICon;
        this.owned = owned;
        this.item_name = item_name;
        this.cost = cost;
    }

}
