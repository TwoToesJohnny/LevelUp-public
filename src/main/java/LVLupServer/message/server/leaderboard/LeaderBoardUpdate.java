package LVLupServer.message.server.leaderboard;

import LVLupServer.Message;

import java.io.Serial;

public class LeaderBoardUpdate extends Message {
    @Serial
    private static final long serialVersionUID = 125L;
    private final int position;
    private final int gemsReward;

    public LeaderBoardUpdate(int position, int gemsReward) {
        this.position = position;
        this.gemsReward = gemsReward;
    }

    public int getPosition() {
        return position;
    }

    public int getGemsReward() {
        return gemsReward;
    }
}
