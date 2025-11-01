package LVLupServer.dataBase;

import LVLupServer.leaderboard.TopUser;
import com.mysql.cj.exceptions.DataReadException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static LVLupServer.dataBase.Datasource.getConnection;

public class LeaderBoardRewardsRepository {


    public List<Integer> getRewards() {
        String sqlQuery = "Select amount from rewardgems";
        List<Integer> rewards = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sqlQuery)) {
                while (rs.next()) {
                    int reward = rs.getInt(1);
                    rewards.add(reward);
                }

            }
            return rewards;

        } catch (SQLException e) {
            throw new DataReadException("Error fetching rewards");
        }
    }

    public List<TopUser> getTopUsers() {
        String sqlQuery = "select rewardgems.User_ID, user.weeklyScore " +
                "from rewardgems " +
                "inner join user on rewardgems.User_ID = user.User_ID";
        List<TopUser> topUsers = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sqlQuery)) {
                while (rs.next()) {
                    int userID = rs.getInt("User_ID");
                    int weeklyScore = rs.getInt("weeklyScore");
                    TopUser topUser = new TopUser(userID, weeklyScore);
                    topUsers.add(topUser);
                }

            }
            return topUsers;

        } catch (SQLException e) {
            throw new DataReadException("Error fetching rewards");
        }
    }

    public static void resetLeaderboard() {
        System.out.println("LeaderBoard reset at: " + System.currentTimeMillis());
        String sqlQuery = "update user " +
                "Set weeklyScore = 0";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sqlQuery);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void resetRewards() {
        String sqlQuery = "update rewardgems " +
                "set User_ID = ? " +
                "where Reward_ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            for (int i = 0; i < 3; i++) {
                stmt.setNull(1, Types.INTEGER);
                stmt.setInt(2, i + 1);
                stmt.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRewards(List<TopUser> users) {
        String sqlQuery = "update rewardgems " +
                "set User_ID = ? " +
                "where Reward_ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            for (int i = 0; i < users.size(); i++) {
                stmt.setInt(1, users.get(i).getUserID());
                stmt.setInt(2, i + 1);
                stmt.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
