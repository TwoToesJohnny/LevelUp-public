package LVLupServer.message.client.task;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.TaskRepository;

import java.io.Serial;
import java.sql.SQLException;

public class SuggestTask extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 32L;
    public enum TaskType{
        GOAL,
        HABIT
    }
    public SuggestTask(String taskName, int xpReward, String taskCategory, TaskType taskType) {
        this.taskName = taskName;
        this.xpReward = xpReward;
        this.taskCategory = taskCategory;
        this.taskType = taskType;
    }
    private final String taskName;
    private final int xpReward;
    private final String taskCategory;
    private final TaskType taskType;

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        TaskRepository taskRepository = new TaskRepository();
       int itemID =  taskRepository.suggestTask(taskName,taskCategory,xpReward,taskType);
       taskRepository.createUserTask(client.userDetails.getUserID(),itemID,taskType == TaskType.GOAL);
    }
}
