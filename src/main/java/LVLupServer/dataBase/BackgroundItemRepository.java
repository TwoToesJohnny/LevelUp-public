package LVLupServer.dataBase;

import LVLupServer.message.server.store.BackgroundItem;
import LVLupServer.message.server.store.BackgroundItems;
import LVLupServer.message.server.store.StoreItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static LVLupServer.dataBase.Datasource.getConnection;

public class BackgroundItemRepository {
    private List<Integer> getUserOwnedBackground_IntList(int userID){
        String sql_query = "select Item_ID from user_backgrounditems " +
                "where User_ID = ?";
        List<Integer> userOwnedBackground_IntList = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setInt(1,userID);

            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next())
                    userOwnedBackground_IntList.add(resultSet.getInt("Item_ID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userOwnedBackground_IntList;
    }
    public void purchaseBackgroundItem(int userID, int itemID) throws SQLException {
        String sql_query = "insert into user_backgrounditems(Item_ID,User_ID) " +
                "values(?,?)";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)){
            preparedStatement.setInt(1,itemID);
            preparedStatement.setInt(2,userID);

            preparedStatement.executeUpdate();
        }

    }

    public BackgroundItems getBackgroundItems(int userID){
        List<Integer> getUserOwnedBackground_IntList = getUserOwnedBackground_IntList(userID);
        String sql_query = "select * from backgrounditem ";
        List<StoreItem> backgroundItemList = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {

            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    boolean owned = false;
                    int itemID = resultSet.getInt("Item_ID");
                    if(getUserOwnedBackground_IntList.contains(itemID))
                        owned = true;
                    String backgroundString = resultSet.getString("backgroundImage");
                    String name = resultSet.getString("item_Name");
                    int cost = resultSet.getInt("cost");

                    BackgroundItem backgroundItem = new BackgroundItem(owned,name,cost,itemID,backgroundString);
                    backgroundItemList.add(backgroundItem);
                }
            }
            return new BackgroundItems(backgroundItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
