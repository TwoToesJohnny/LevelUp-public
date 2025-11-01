package LVLupServer.dataBase;

import LVLupServer.shared.BadgesDTO;
import LVLupServer.shared.BorderIconDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static LVLupServer.dataBase.Datasource.getConnection;

public class VirtualItemRepo {

    public void addBorderIconItem(String itemName, int cost, byte[] iconData) throws SQLException {
        String sql = "INSERT INTO bordericonitem (Item_ID, Icon, item_Name, cost) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int generatedId = generateNextItemId(conn);
            stmt.setInt(1, generatedId);
            stmt.setBytes(2, iconData);
            stmt.setString(3, itemName);
            stmt.setInt(4, cost);

            stmt.executeUpdate();
        }
    }

    private int generateNextItemId(Connection conn) throws SQLException {
        String query = "SELECT IFNULL(MAX(Item_ID), 0) + 1 FROM bordericonitem";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Failed to generate new Item_ID");
    }

    public List<BorderIconDTO> getAllBorderIcons() throws SQLException {
        List<BorderIconDTO> list = new ArrayList<>();
        String sql = "SELECT Item_ID, Icon, item_Name, cost FROM bordericonitem";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int itemId = rs.getInt("Item_ID");
                byte[] icon = rs.getBytes("Icon");
                String name = rs.getString("item_Name");
                int cost = rs.getInt("cost");

                System.out.println("Retrieved border icon - ID: " + itemId +
                        ", Name: " + name +
                        ", Cost: " + cost +
                        ", Icon bytes: " + (icon != null ? icon.length : 0));

                list.add(new BorderIconDTO(itemId, name, cost, icon));
            }
        }
        return list;
    }

    public boolean deleteBorderIcon(int id) throws SQLException {
        String sql = "DELETE FROM bordericonitem WHERE Item_ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateBorderIcon(BorderIconDTO icon) throws SQLException {
        String sql = "UPDATE bordericonitem SET item_Name = ?, cost = ?, Icon = ? WHERE Item_ID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, icon.getItemName());
            stmt.setInt(2, icon.getGemCost());
            stmt.setBytes(3, icon.getImageData());
            stmt.setInt(4, icon.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<BadgesDTO> getAllBadges() throws SQLException {
        List<BadgesDTO> badges = new ArrayList<>();
        String sql = "SELECT lvl, gems, badgeName, badgeIcon FROM levelup ORDER BY lvl ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int lvl = rs.getInt("lvl");
                int gems = rs.getInt("gems");
                String badgeName = rs.getString("badgeName");
                byte[] badgeIconBytes = rs.getBytes("badgeIcon");

                badges.add(new BadgesDTO(badgeIconBytes, badgeName, lvl, gems));
            }
        }
        return badges;
    }
}