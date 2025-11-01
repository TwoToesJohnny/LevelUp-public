package LVLupServer.dataBase;

import LVLupServer.message.server.UserDetails;
import LVLupServer.message.server.store.BackgroundItem;
import LVLupServer.message.server.store.BorderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static LVLupServer.dataBase.Datasource.getConnection;

public class UserRepository {

    public List<UserDetails> fetchUsers() throws SQLException {
        String sqlQuery = "Select * from user" +
                " order by weeklyScore desc";
        List<UserDetails> users = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    UserDetails user = new UserDetails();

                    getUser(rs, user);

                    users.add(user);
                }
            }
        }
        return users;
    }

    public List<Integer> fetchUsersID() throws SQLException {
        String sqlQuery = "Select User_ID from user" +
                " order by weeklyScore desc";
        List<Integer> users = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int userID = rs.getInt(1);
                    users.add(userID);
                }
            }
        }
        return users;
    }

    private void getUser(ResultSet rs, UserDetails user) throws SQLException {
        int appliedBorderItemID = rs.getInt("borderItem_ID");
        int appliedBackgroundItemID = rs.getInt("backgroundItem_ID");
        BorderItem appliedBorderItem = getAppliedBorderItem(appliedBorderItemID);
        BackgroundItem appliedBackground = getAppliedBackgroundItem(appliedBackgroundItemID);

        user.setUserID(rs.getInt("User_ID"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("User_password"));
        user.setEmail(rs.getString("email"));
        user.setLoggedIn(rs.getBoolean("loggedIn"));
        user.setGems(rs.getInt("Gem_Amount"));
        user.setExp(rs.getInt("totalXP"));
        user.setLvl(rs.getInt("user_Level"));
        user.setWeekly_Score(rs.getInt("weeklyScore"));
        user.setProfileIcon(rs.getBytes("profileIcon"));
        user.setAppliedBorderItem(appliedBorderItem);
        user.setAppliedBackground(appliedBackground);
    }

    private BorderItem getAppliedBorderItem(int itemID) throws SQLException {
        String sqlQuery = "Select * from bordericonitem " +
                "where Item_ID = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1,itemID);

            try(ResultSet resultSet = pst.executeQuery()) {
                if(resultSet.next()) {
                    byte[] icon = resultSet.getBytes("Icon");
                    String name = resultSet.getString("item_Name");
                    int cost = resultSet.getInt("cost");

                    return new BorderItem(itemID, icon, true, name, cost);
                }else
                    return null;

            }

        }
    }
    private BackgroundItem getAppliedBackgroundItem(int itemID) throws SQLException {
        String sqlQuery = "Select * from backgrounditem " +
                "where Item_ID = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1,itemID);

            try(ResultSet resultSet = pst.executeQuery()) {
                if(resultSet.next()) {
                    String backgroundString = resultSet.getString("backgroundImage");
                    String name = resultSet.getString("item_Name");
                    int cost = resultSet.getInt("cost");

                    return new BackgroundItem(true,name,cost,itemID,backgroundString);
                }else
                    return null;

            }
        }
    }

    public UserDetails fetchUser(String userName, boolean isEmail) throws SQLException {
        String SQL_QUERY;
        if (isEmail) SQL_QUERY = "SELECT * FROM user WHERE email = ?";
        else SQL_QUERY = "SELECT * FROM user WHERE username = ?";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY)) {
            pst.setString(1, userName);
            UserDetails user = new UserDetails();
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {

                    getUser(rs, user);

                }
                return user;
            }
        }
    }

    public void updateUser(String username, String email, String password, int user_ID, byte[] profileIcon) throws SQLException {

        String sql_Query = "Update user " +
                "Set username = ? , " +
                "email = ? , " +
                "User_password = ? ," +
                "profileIcon = ? " +
                "where User_ID = ?; ";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql_Query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setBytes(4, profileIcon);
            preparedStatement.setInt(5, user_ID);

            preparedStatement.executeUpdate();
            System.out.println("Updated user with username: " + username);
        }
    }
    public void updateUserProfileBorder(int itemID, int userID){
        String sqlQuery = "Update user "+
        "Set borderItem_ID = ? " +
                "where User_ID = ?" ;

        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1,itemID);
            preparedStatement.setInt(2,userID);

            preparedStatement.executeUpdate();
            System.out.println("Updated userProfileBorder with itemID: " + itemID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateUserBackground(int itemID, int userID){
        String sqlQuery = "Update user "+
                "Set backgroundItem_ID = ? " +
                "where User_ID = ?" ;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1,itemID);
            preparedStatement.setInt(2,userID);

            preparedStatement.executeUpdate();
            System.out.println("Updated userProfileBorder with itemID: " + itemID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void createUser(String username, String email, String password) throws SQLException {
        int defaultValue = 0;
        int defaultLevel = 1;
        boolean defaultLoggedIn = false;
        String SQL_Query = "INSERT INTO user(Username,User_password,email,Gem_Amount,totalXP,weeklyScore,user_Level,loggedIn)" +
                "Values (?,?,?,?,?,?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_Query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, defaultValue);
            preparedStatement.setInt(5, defaultValue);
            preparedStatement.setInt(6, defaultValue);
            preparedStatement.setInt(7, defaultLevel);
            preparedStatement.setBoolean(8, defaultLoggedIn);

            preparedStatement.executeUpdate();
        }
    }
}
