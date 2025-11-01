package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserLevelUpReward;

import java.sql.SQLException;

public class RewardRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 14L;
    private int lvl;

    public RewardRequest(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserLevelUpReward userLevelUpReward = new UserLevelUpReward(client);
        userLevelUpReward.sendLevelUpRewardMessage(lvl);
    }
}
