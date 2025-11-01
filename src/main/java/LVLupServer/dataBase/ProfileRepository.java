package LVLupServer.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static LVLupServer.dataBase.Datasource.getConnection;

public class ProfileRepository {

    public ArrayList<byte[]> getBadgeDataList(int lvl){
        String sqlQuery = "Select badgeIcon from levelup " +
                "where lvl <= ?";

        ArrayList<byte[]> badgeDataList = new ArrayList<>();
        try(Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1,lvl);

            try(ResultSet resultSet = pst.executeQuery()){
                while (resultSet.next()){
                    byte[] badgeData = resultSet.getBytes("badgeIcon");
                    if(badgeData != null)
                        badgeDataList.add(badgeData);
                }
                return badgeDataList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getProfileData(int user_ID){
        String sqlQuery = "Select profileIcon from user " +
                "where User_ID = ?";

        try(Connection con = getConnection();
            PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1,user_ID);

            try(ResultSet resultSet = pst.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getBytes("profileIcon");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
