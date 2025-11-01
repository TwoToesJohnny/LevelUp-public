package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.message.server.UserDetails;
import LVLupServer.message.server.store.BorderItem;
import LVLupServer.message.server.store.StoreItem;

public record UserBuyItem(LevelUpClient client) {

    public void updateUserGems(StoreItem storeItem) {
        UserDetails user = client.userDetails;
        System.out.println("User gems before update: " + user.getGems());
        System.out.println("item gem cost: " + storeItem.cost);
        int currentGems = user.getGems();
        int cost = storeItem.cost;

        // Validate user has enough gems
        if(currentGems < cost) {
            throw new IllegalStateException("Insufficient gems");
        }

        int newGemAmount = currentGems - cost;

        CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();
        completeTaskRepository.updateUserGems(user.getUserID(), -cost);  // Subtract cost
        user.setGems(newGemAmount);
    }
}
