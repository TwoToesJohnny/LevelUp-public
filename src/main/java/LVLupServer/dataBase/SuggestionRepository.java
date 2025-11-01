package LVLupServer.dataBase;

import LVLupServer.shared.SuggestionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static LVLupServer.dataBase.Datasource.getConnection;

public class SuggestionRepository {

    public List<SuggestionDTO> getPendingSuggestions() throws SQLException {
        List<SuggestionDTO> suggestions = new ArrayList<>();

        String goalQuery = "SELECT task_ID, goalName as name, category, exp FROM goal WHERE approved = 0";
        String habitQuery = "SELECT task_ID, habitName as name, category, exp FROM habit WHERE approved = 0";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet goalRs = stmt.executeQuery(goalQuery)) {

            while (goalRs.next()) {
                SuggestionDTO suggestion = new SuggestionDTO(
                        goalRs.getInt("task_ID"),
                        "goal",
                        goalRs.getString("name"),
                        goalRs.getString("category"),
                        goalRs.getInt("exp"),
                        false
                );
                suggestions.add(suggestion);
            }
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet habitRs = stmt.executeQuery(habitQuery)) {

            while (habitRs.next()) {
                SuggestionDTO suggestion = new SuggestionDTO(
                        habitRs.getInt("task_ID"),
                        "habit",
                        habitRs.getString("name"),
                        habitRs.getString("category"),
                        habitRs.getInt("exp"),
                        false
                );
                suggestions.add(suggestion);
            }
        }

        return suggestions;
    }

    public boolean approveGoal(int taskId, String name, String category, int exp) throws SQLException {
        String query = "UPDATE goal SET goalName = ?, category = ?, exp = ?, approved = 1 WHERE task_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setInt(3, exp);
            stmt.setInt(4, taskId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean approveHabit(int taskId, String name, String category, int exp) throws SQLException {
        String query = "UPDATE habit SET habitName = ?, category = ?, exp = ?, approved = 1 WHERE task_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setInt(3, exp);
            stmt.setInt(4, taskId);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean rejectGoal(int taskId) throws SQLException {
        String deleteUserGoalsQuery = "DELETE FROM usergoals WHERE task_ID = ?";
        String deleteGoalQuery = "DELETE FROM goal WHERE task_ID = ? AND approved = 0";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement stmt = conn.prepareStatement(deleteUserGoalsQuery)) {
                    stmt.setInt(1, taskId);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement(deleteGoalQuery)) {
                    stmt.setInt(1, taskId);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public boolean rejectHabit(int taskId) throws SQLException {
        String deleteUserHabitsQuery = "DELETE FROM userhabits WHERE task_ID = ?";
        String deleteHabitQuery = "DELETE FROM habit WHERE task_ID = ? AND approved = 0";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement stmt = conn.prepareStatement(deleteUserHabitsQuery)) {
                    stmt.setInt(1, taskId);
                    stmt.executeUpdate();
                }

                try (PreparedStatement stmt = conn.prepareStatement(deleteHabitQuery)) {
                    stmt.setInt(1, taskId);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}