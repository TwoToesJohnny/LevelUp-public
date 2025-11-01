package LVLupServer.dataBase;

import LVLupServer.message.client.task.SuggestTask;
import LVLupServer.message.server.Task.Task;

import java.sql.*;
import java.util.ArrayList;

import static LVLupServer.dataBase.Datasource.getConnection;

public class TaskRepository {

    public ArrayList<Task> fetchGoals(int User_ID) throws SQLException {
        String SQL_QUERY = "SELECT g.* " +
                "FROM goal g " +
                "JOIN usergoals ug ON g.task_ID = ug.task_ID " +
                "WHERE ug.user_ID = ?";
        return getGoals(User_ID, SQL_QUERY);
    }

    public boolean addedGoal(int User_ID, int Item_ID) throws SQLException {
        String sqlQuery = "SELECT 1 FROM usergoals " +
                "WHERE user_ID = ? AND task_ID = ? " +
                "LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1, User_ID);
            pst.setInt(2, Item_ID);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        }
    }

    public boolean addedHabit(int User_ID, int Item_ID) throws SQLException {
        String sqlQuery = "SELECT 1 FROM userhabits " +
                "WHERE user_ID = ? AND task_ID = ? " +
                "LIMIT 1";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1, User_ID);
            pst.setInt(2, Item_ID);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        }
    }

    public ArrayList<Task> fetchAllGoals(int User_ID) throws SQLException {
        String SQL_QUERY = "SELECT * from goal";
        return getGoals(User_ID, SQL_QUERY);
    }

    public ArrayList<Task> fetchHabits(int userId) throws SQLException {
        String sqlQuery = "SELECT h.* " +
                "FROM habit h " +
                "JOIN userhabits uh ON h.task_ID = uh.task_ID " +
                "WHERE uh.user_ID = ?";
        return getHabits(userId, sqlQuery);
    }

    private ArrayList<Task> getHabits(int userId, String sqlQuery) throws SQLException {
        ArrayList<Task> habits = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            if (userId != 0)
                pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Task habit = new Task();
                    habit.setName(rs.getString("habitName"));
                    habit.setTaskID(rs.getInt("task_ID"));
                    habit.setCategory(rs.getString("category"));
                    habit.setExp(rs.getInt("exp"));
                    habit.setType("Habit");
                    habit.setCompleted(isCompleted(userId, habit.getTaskID(), false));
                    habit.setApproved(rs.getBoolean("approved"));
                    habits.add(habit);
                }
            }
        }
        return habits;
    }

    public ArrayList<Task> fetchAllHabits(int userId) throws SQLException {
        String sqlQuery = "SELECT * From habit";

        return getHabits(userId, sqlQuery);
    }

    private ArrayList<Task> getGoals(int User_ID, String SQL_QUERY) throws SQLException {
        ArrayList<Task> goals = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY)) {
            if (User_ID != 0)
                pst.setInt(1, User_ID);
            try (ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Task goal = new Task();
                    goal.setName(rs.getString("goalName"));
                    goal.setTaskID(rs.getInt("task_ID"));
                    goal.setCategory(rs.getString("category"));
                    goal.setExp(rs.getInt("exp"));
                    goal.setType("Goal");
                    goal.setCompleted(isCompleted(User_ID, goal.getTaskID(), true));
                    goal.setApproved(rs.getBoolean("approved"));

                    goals.add(goal);
                }
            }
            return goals;
        }
    }


    public boolean isCompleted(int userID, int taskID, boolean isGoal) {
        String tableName = isGoal ? "usergoals" : "userhabits";
        String sqlQuery = "SELECT isCompleted FROM " + tableName + " WHERE task_ID = ? AND user_ID = ?";
        try (Connection con = getConnection();
             PreparedStatement statement = con.prepareStatement(sqlQuery)) {

            statement.setInt(1, taskID);
            statement.setInt(2, userID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    boolean isCompleted = rs.getBoolean("isCompleted");

                    return isCompleted;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Task getTask(int taskID, boolean isGoal) {
        Task task = new Task();
        String sqlQuery;
        if (isGoal)
            sqlQuery = "Select * from goal " +
                    "where task_ID = ?";
        else
            sqlQuery = "Select * from habit " +
                    "where task_ID = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sqlQuery)) {
            pst.setInt(1, taskID);

            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    if (!isGoal)
                        task.setName(resultSet.getString("habitName"));
                    else
                        task.setName(resultSet.getString("goalName"));
                    task.setTaskID(resultSet.getInt("task_ID"));
                    task.setCategory(resultSet.getString("category"));
                    task.setExp(resultSet.getInt("exp"));
                    task.setApproved(resultSet.getBoolean("approved"));
                    if (isGoal)
                        task.setType("Goal");
                    else
                        task.setType("Habit");
                    task.setCompleted(true);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    public void createUserTask(int userId, int taskID, boolean isGoal) throws SQLException {
        String tableName;
        if (isGoal) {
            tableName = "usergoals";
            if (addedGoal(userId, taskID))
                return;
        } else {
            tableName = "userhabits";
            if (addedHabit(userId, taskID))
                return;
        }
        String SQL_Query = " INSERT INTO " + tableName + "(task_ID,user_ID,isCompleted)" +
                "VALUES(?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Query)) {
            statement.setInt(1, taskID);
            statement.setInt(2, userId);
            statement.setBoolean(3, false);
            statement.executeUpdate();
        }
    }

    public void removeUserTask(int userId, int taskID, boolean isGoal) throws SQLException {
        System.out.println("removing task. taskID: " + taskID + "\nuserID: " + userId);
        String tableName;
        if (isGoal)
            tableName = "usergoals";
        else
            tableName = "userhabits";
        String SQL_Query = " DELETE From " + tableName +
                " where task_ID = ? " +
                "and user_ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Query)) {
            statement.setInt(1, taskID);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    public int suggestTask(String name, String category, int exp, SuggestTask.TaskType taskType) {
        String sqlQuery;
        System.out.println("Suggesting task");

        if (taskType == SuggestTask.TaskType.GOAL)
            sqlQuery = "Insert into goal(goalName,category,exp,approved) " +
                    "values(?,?,?,?)";
        else
            sqlQuery = "Insert into habit(habitName,category,exp,approved) " +
                    "values(?,?,?,?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);
            statement.setString(2, category);
            statement.setInt(3, exp);
            statement.setBoolean(4, false);

            statement.executeUpdate();

            // Retrieve the generated key
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Task suggested with ID: " + generatedId);
                    return generatedId;
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


