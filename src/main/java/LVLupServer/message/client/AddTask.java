package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.dataBase.TaskRepository;

import java.io.Serial;
import java.sql.SQLException;

public class AddTask extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 11L;
    private int taskID;
    private int User_ID;
    private boolean isGoal;

    public AddTask(int taskID, int user_ID, boolean isGoal) {
        this.taskID = taskID;
        User_ID = user_ID;
        this.isGoal = isGoal;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        TaskRepository taskRepository = new TaskRepository();
        CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();

        if(taskRepository.isCompleted(User_ID,taskID,isGoal))
            completeTaskRepository.completeTask(User_ID,taskID,isGoal,false);
        else
            taskRepository.createUserTask(User_ID,taskID,isGoal);
    }
}
