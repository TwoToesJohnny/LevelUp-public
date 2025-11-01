package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.message.server.AllGoals;
import LVLupServer.message.server.Task.Task;

import java.sql.SQLException;
import java.util.ArrayList;

public record UserAddTask(LevelUpClient client) {

    public void sendAllGoals() throws SQLException {
        TaskRepository taskRepository = new TaskRepository();
        ArrayList<Task> goals = taskRepository.fetchAllGoals(0);
        AllGoals allGoals = new AllGoals(goals);
        client.send(allGoals);
    }
}
