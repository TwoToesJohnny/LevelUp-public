package LVLupServer.Users;

import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.dataBase.LeaderBoardRewardsRepository;
import LVLupServer.dataBase.UserRepository;
import LVLupServer.leaderboard.TopUser;
import LVLupServer.message.server.UserDetails;

import java.sql.SQLException;
import java.util.*;

public class LeaderBoardReset {
    private static final LeaderBoardRewardsRepository leaderBoardRewardsRepository = new LeaderBoardRewardsRepository();
    CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();
    List<Integer> rewards = leaderBoardRewardsRepository.getRewards();
    UserRepository userRepository = new UserRepository();

    public LeaderBoardReset() throws SQLException {
    }
    public List<Integer> getSortedUsers() throws SQLException {
        List<Integer> usersList = userRepository.fetchUsersID();
        if (usersList == null)
            return Collections.emptyList();
        return usersList;

    }
    public static void resetLeaderBoard(){
        LeaderBoardRewardsRepository.resetLeaderboard();
        leaderBoardRewardsRepository.resetRewards();
    }
    public void updateTopUsers(){
        List<TopUser> topUsers = leaderBoardRewardsRepository.getTopUsers();

        System.out.println("=== UPDATING TOP USERS ===");
        System.out.println("Number of top users found: " + topUsers.size());
        System.out.println("Number of rewards available: " + rewards.size());

        if (topUsers.isEmpty()) {
            System.out.println("No top users to reward!");
            return;
        }

        for (int i = 0; i < topUsers.size() && i < rewards.size(); i++) {
            int userId = topUsers.get(i).getUserID();
            int gemReward = rewards.get(i);

            System.out.println("Position " + (i+1) + ": User ID " + userId +
                    " - Awarding " + gemReward + " gems");

            try {
                completeTaskRepository.updateUserGems(userId, gemReward);
                System.out.println("✓ Successfully updated gems for user " + userId);
            } catch (Exception e) {
                System.err.println("✗ FAILED to update gems for user " + userId);
                e.printStackTrace();
            }
        }

        System.out.println("=== TOP USERS UPDATE COMPLETE ===");
    }


    public List<Integer> getRewards(){
        rewards = leaderBoardRewardsRepository.getRewards();
        return rewards;
    }
}
