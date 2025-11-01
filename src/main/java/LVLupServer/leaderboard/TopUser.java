package LVLupServer.leaderboard;

public class TopUser {
    private final int userID;
    private final int weeklyScore;

    public int getUserID() {
        return userID;
    }

    public int getWeeklyScore() {
        return weeklyScore;
    }

    public TopUser(int userID, int weeklyScore) {
        this.userID = userID;
        this.weeklyScore = weeklyScore;
    }
}
