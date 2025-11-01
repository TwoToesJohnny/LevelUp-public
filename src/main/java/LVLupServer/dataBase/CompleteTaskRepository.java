package LVLupServer.dataBase;

import LVLupServer.level.LevelInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import static LVLupServer.dataBase.Datasource.getConnection;
public class CompleteTaskRepository {
    public CompleteTaskRepository() {}


    public void completeTask(int userId, int taskID, boolean isGoal, boolean complete) {
        System.out.println("Completing task: " + complete);
        String tableName = isGoal ? "usergoals" : "userhabits";
        String sqlQuery = "UPDATE " + tableName +
                " SET isCompleted = ? " +
                "WHERE task_ID = ? AND user_ID = ?";

        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(sqlQuery)) {

            statement.setBoolean(1, complete);
            statement.setInt(2, taskID);
            statement.setInt(3, userId);

            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public LevelInfo getLevelUp(int lvl){
        String sqlQuery = "Select * from levelup " +
                            "where lvl = ?";

        try(Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1,lvl);

            try(ResultSet resultSet = pst.executeQuery()){
                if(resultSet.next()){
                    int requiredXp = resultSet.getInt("requiredXP");
                    int gems = resultSet.getInt("gems");
                    byte[] blobData = resultSet.getBytes("badgeIcon");
                    String badgeName = resultSet.getString("badgeName");

                    return new LevelInfo(lvl,requiredXp,gems,blobData,badgeName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void lvlUp(int userID){
        System.out.println("Updating user lvl :");
        String sqlQuery = "Update user " +
                "Set user_Level = user_Level + 1 " +
                "where User_ID = ?";

        try(Connection con = getConnection();
        PreparedStatement statement = con.prepareStatement(sqlQuery)) {
            statement.setInt(1,userID);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateUserGems(int userID, int gemAmount){


        System.out.println("Updating user gems :");
        String sqlQuery = "Update user " +
                "Set Gem_Amount = Gem_Amount + ?" +
                " where User_ID = ?";

        System.out.println("gem update amount: " + gemAmount);
        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sqlQuery)) {
            statement.setInt(1,gemAmount);
            statement.setInt(2,userID);

            int rowsAffected = statement.executeUpdate();

            if(rowsAffected == 0) {
                throw new SQLException("User not found or update failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserXP(int userID, int newXP){
        System.out.println("Updating user xp :");
        String sqlQuery = "Update user " +
                "Set totalXP = ? " +
                "where User_ID = ?";

        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sqlQuery)) {
            statement.setInt(1,newXP);
            statement.setInt(2, userID);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateUserWeeklyScore(int userID, int newWeekly_Score){
        System.out.println("Updating user weeklyScore :");
        String sqlQuery = "Update user " +
                "Set weeklyScore = ? " +
                "where User_ID = ?";

        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sqlQuery)) {
            statement.setInt(1,newWeekly_Score);
            statement.setInt(2, userID);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
