package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.level.LevelInfo;
import LVLupServer.message.server.LevellingUp;
import LVLupServer.message.server.UpdateXP;
import LVLupServer.message.server.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record UserLevelUp(LevelUpClient client, CompleteTaskRepository repository) {

    private static final Logger logger =  LoggerFactory.getLogger(UserLevelUp.class);
    public static final int MAX_USER_LEVEL = 10;

    public void updateXP(int userId, int taskXP) {
        validateInput(userId, taskXP);

        UserDetails userDetails = getUserDetails();
        int currentLevel = userDetails.getLvl();
        int currentXP = userDetails.getExp();

        LevelInfo levelInfo = repository.getLevelUp(currentLevel);
        int newXP = currentXP + taskXP;

        logger.debug("User {}: {} XP -> {} XP (Required: {})",
                userId, currentXP, newXP, levelInfo.getRequiredXp());

        try {
            repository.updateUserXP(userId, newXP);

            if (shouldLevelUp(currentLevel, newXP, levelInfo.getRequiredXp())) {
                performLevelUp(userId, currentLevel, levelInfo, newXP);
            } else {
                sendXPUpdate(newXP);
            }

            userDetails.setExp(newXP);
            logger.info("User {} XP updated successfully", userId);

        } catch (Exception e) {
            logger.error("Failed to update XP for user {}", userId, e);
            throw new RuntimeException("XP update failed", e);
        }
    }

    public int updateWeeklyScore(int userId, int taskXP) {
        validateUserId(userId);

        UserDetails userDetails = getUserDetails();
        int newWeeklyScore = userDetails.getWeekly_Score() + taskXP;

        repository.updateUserWeeklyScore(userId, newWeeklyScore);
        userDetails.setWeekly_Score(newWeeklyScore);

        logger.debug("User {} weekly score updated to {}", userId, newWeeklyScore);
        return newWeeklyScore;
    }

    private void performLevelUp(int userId, int currentLevel, LevelInfo levelInfo, int newXP) {
        int newLevel = currentLevel + 1;
        int gemReward = levelInfo.getGemAmount();

        logger.info("User {} leveling up: {} -> {}", userId, currentLevel, newLevel);
        System.out.println("Gem reward: " + gemReward);

        repository.lvlUp(userId);
        repository.updateUserGems(userId, gemReward);

        UserDetails userDetails = client.userDetails;
        userDetails.setLvl(newLevel);
        userDetails.setGems(userDetails.getGems() + gemReward);

        sendXPUpdate(newXP);
        sendLevelUpMessage();
    }

    private boolean shouldLevelUp(int currentLevel, int currentXP, int requiredXP) {
        return currentXP >= requiredXP && currentLevel < MAX_USER_LEVEL;
    }

    private UserDetails getUserDetails() {
        if (client.userDetails == null) {
            throw new IllegalStateException("User details not available");
        }
        return client.userDetails;
    }

    private void validateInput(int userId, int taskXP) {
        validateUserId(userId);
        if (taskXP < 0) {
            throw new IllegalArgumentException("Task XP cannot be negative: " + taskXP);
        }
    }

    private void validateUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
    }

    private void sendLevelUpMessage() {
        logger.debug("Sending level up notification");
        client.send(new LevellingUp());
    }

    private void sendXPUpdate(int userXP) {
        client.send(new UpdateXP(userXP));
    }
}
