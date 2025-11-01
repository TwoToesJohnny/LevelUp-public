package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserAddTask;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.message.server.AllGoals;
import LVLupServer.message.server.AllHabits;
import LVLupServer.message.server.Task.Task;
import LVLupServer.message.server.Task.TaskList;
import LVLupServer.message.server.UserDetails;

import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveTask extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 19L;
    private int taskID;
    private int User_ID;
    private boolean isGoal;

    public RemoveTask(int taskID, int user_ID, boolean isGoal) {
        this.taskID = taskID;
        User_ID = user_ID;
        this.isGoal = isGoal;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        TaskRepository taskRepository = new TaskRepository();
        taskRepository.removeUserTask(User_ID,taskID,isGoal);

    }
}