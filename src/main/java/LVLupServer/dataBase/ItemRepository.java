package LVLupServer.dataBase;

import LVLupServer.message.server.store.BorderItem;
import LVLupServer.message.server.store.BorderItems;
import LVLupServer.message.server.store.StoreItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static LVLupServer.dataBase.Datasource.getConnection;
public class ItemRepository {
    private List<Integer> getUserOwnedBorderIcon_IntList(int userID){
        String sql_query = "select Item_ID from user_bordericonitems " +
                "where User_ID = ?";
        List<Integer> userOwnedBorderIcon_IntList = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
            preparedStatement.setInt(1,userID);

           try( ResultSet resultSet = preparedStatement.executeQuery()){
               while (resultSet.next())
                   userOwnedBorderIcon_IntList.add(resultSet.getInt("Item_ID"));
           }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userOwnedBorderIcon_IntList;
    }
    public void purchaseBorderItem(int userID,int itemID) throws SQLException {
        String sql_query = "insert into user_bordericonitems(Item_ID,User_ID) " +
                "values(?,?)";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)){
            preparedStatement.setInt(1,itemID);
            preparedStatement.setInt(2,userID);

            preparedStatement.executeUpdate();
        }

    }

    public BorderItems getBorderItems(int userID){
        List<Integer> userOwnedBorderIcon_IntList = getUserOwnedBorderIcon_IntList(userID);
        String sql_query = "select * from bordericonitem ";
        List<StoreItem> borderItemList = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {

            try( ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    boolean owned = false;
                    int itemID = resultSet.getInt("Item_ID");
                        if(userOwnedBorderIcon_IntList.contains(itemID))
                            owned = true;
                    byte [] icon = resultSet.getBytes("Icon");
                    String name = resultSet.getString("item_Name");
                    int cost = resultSet.getInt("cost");

                    BorderItem borderItem = new BorderItem(itemID,icon,owned,name,cost);
                    borderItemList.add(borderItem);
                }
            }
            return new BorderItems(borderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
