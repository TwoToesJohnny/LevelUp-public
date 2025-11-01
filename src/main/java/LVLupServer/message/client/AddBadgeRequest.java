package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.BadgeRepo;
import LVLupServer.message.server.VirtualItemAddedResponse;

public class AddBadgeRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 1L;

    private final byte[] imageBytes;
    private final int requiredLevel;
    private final String name;
    private final String reward;

    public AddBadgeRequest(byte[] imageBytes, int requiredLevel, String name,String reward) {
        this.imageBytes = imageBytes;
        this.requiredLevel = requiredLevel;
        this.name = name;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public String getReward() {
        return reward;
    }

    @Override
    public void apply(LevelUpClient client) {
        boolean success = BadgeRepo.addBadge(imageBytes, requiredLevel, name,reward);
        String msg = success ? "Badge added successfully!" : "Failed to add badge.";
        client.send(new VirtualItemAddedResponse(success, msg));
    }

}
