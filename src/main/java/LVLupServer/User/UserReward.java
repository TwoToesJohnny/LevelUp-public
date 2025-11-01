package LVLupServer.User;

import LVLupServer.dataBase.LeaderBoardRewardsRepository;
import LVLupServer.leaderboard.TopUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserReward {

    private final LeaderBoardRewardsRepository rewardsRepository;
    private static final int MAX_TOP_USERS = 3;

    public UserReward() {
        this.rewardsRepository = new LeaderBoardRewardsRepository();
    }

    public void updateRewards(int userID, int weeklyScore){
        List<TopUser> topUsers = getTopUsers(userID, weeklyScore);

        boolean userIsInTop = topUsers.stream()
                .anyMatch(user -> user.getUserID() == userID);

        if(userIsInTop) {
            System.out.println("User " + userID + " is in top " + MAX_TOP_USERS +
                    " with score " + weeklyScore);
            rewardsRepository.updateRewards(topUsers);
        } else {
            System.out.println("User " + userID + " did not make top " + MAX_TOP_USERS);
        }
    }

    private List<TopUser> getTopUsers(int userID, int weeklyScore) {
        List<TopUser> topUsers = new ArrayList<>(rewardsRepository.getTopUsers());

        topUsers.removeIf(user -> user.getUserID() == userID);

        TopUser potentialTopUser = new TopUser(userID, weeklyScore);
        topUsers.add(potentialTopUser);

        topUsers.sort(Comparator.comparingInt(TopUser::getWeeklyScore).reversed());

        return topUsers.subList(0, Math.min(MAX_TOP_USERS, topUsers.size()));
    }
}