package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;

public class AddVirtualItemRequest extends Message<LevelUpClient> {

    private static final long serialVersionUID = 110L;
    private String itemName;
    private int gemCost;
    private byte[] imageData;

    public AddVirtualItemRequest(String itemName, String itemType, String fontType,
                                 int gemCost, byte[] imageData) {
        this.itemName = itemName;
        this.gemCost = gemCost;
        this.imageData = imageData;
    }

    @Override
    public void apply(LevelUpClient client) {
        new LVLupServer.handlers.ClientRequestHandler(client)
                .handleAddVirtualItem(itemName, gemCost, imageData);
    }

    @Override
    public String toString() {
        return "AddVirtualItemRequest{" +
                "itemName='" + itemName + '\'' +
                ", gemCost=" + gemCost +
                '}';
    }
}
