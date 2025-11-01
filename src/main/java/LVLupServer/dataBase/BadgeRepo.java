package LVLupServer.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import static LVLupServer.dataBase.Datasource.getConnection;

public class BadgeRepo {

    public static boolean addBadge(byte[] imageBytes, int requiredLevel, String name, String reward) {
        String updateQuery = "UPDATE levelup SET badgeIcon = ?, badgeName = ?, gems = ? WHERE lvl = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setBytes(1, imageBytes);
            stmt.setString(2, name);
            stmt.setInt(3, Integer.parseInt(reward));
            stmt.setInt(4, requiredLevel);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearBadge(int level) {
        String updateQuery = "UPDATE levelup SET badgeIcon = NULL, badgeName = NULL, gems = 50 WHERE lvl = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setInt(1, level);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}