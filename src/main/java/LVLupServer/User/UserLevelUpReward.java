package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.level.LevelInfo;
import LVLupServer.message.server.LevellingUpRewards;

public record UserLevelUpReward(LevelUpClient client) {

    public void sendLevelUpRewardMessage(int lvl) {
        CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();
        LevelInfo levelInfo = completeTaskRepository.getLevelUp(lvl);

        int gemAmount = levelInfo.getGemAmount();
        int requiredXP = levelInfo.getRequiredXp();

        byte[] badgeIcon = levelInfo.getBadgeImage();
        String badgeName = levelInfo.getBadgeName();

        client.send(new LevellingUpRewards(requiredXP, gemAmount, badgeIcon, badgeName));
    }
}
