package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserLevelUp;
import LVLupServer.User.UserReward;
import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.message.server.Task.Task;

import java.io.Serial;
import java.sql.SQLException;

public class CompleteTask extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 12L;
    private final int taskID;
    private final boolean isGoal;

    public CompleteTask(int taskID, boolean isGoal, int userID) {
        this.taskID = taskID;
        this.isGoal = isGoal;
        this.userID = userID;
    }

    private final int userID;

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();

        UserLevelUp userLevelUp = new UserLevelUp(client,completeTaskRepository);
        UserReward userReward = new UserReward();
        int taskXP;
        TaskRepository taskRepository = new TaskRepository();
        Task task = taskRepository.getTask(taskID,isGoal);
        if(task.isApproved())
            taskXP = task.getExp();
        else
            taskXP = 15;

        userLevelUp.updateXP(userID,taskXP);
        int weeklyScore = userLevelUp.updateWeeklyScore(userID,taskXP);
        userReward.updateRewards(userID, weeklyScore);

        completeTaskRepository.completeTask(userID,taskID,isGoal,true);

    }
}
