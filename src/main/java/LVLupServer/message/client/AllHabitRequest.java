package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.message.server.AllHabits;
import LVLupServer.message.server.Task.Task;

import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllHabitRequest extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 13L;
    public AllHabitRequest() {}

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        TaskRepository taskRepository = new TaskRepository();
        ArrayList<Task> habits = taskRepository.fetchAllHabits(0);
        client.send(new AllHabits(habits));
    }
}
