package LVLupServer.shared;

import java.io.Serializable;

public class BorderIconDTO implements Serializable {

    private int id;
    private String itemName;
    private int gemCost;
    private byte[] imageData;

    public BorderIconDTO(int id, String itemName, int gemCost, byte[] imageData) {
        this.id = id;
        this.itemName = itemName;
        this.gemCost = gemCost;
        this.imageData = imageData;
    }

    public int getId() { return id; }
    public String getItemName() { return itemName; }
    public int getGemCost() { return gemCost; }
    public byte[] getImageData() { return imageData; }

    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setGemCost(int gemCost) { this.gemCost = gemCost; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }
}
