package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserAddTask;

import java.io.Serial;
import java.sql.SQLException;

public class AllGoalRequest extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 10L;
    public AllGoalRequest(){
        System.out.println("Received AllTaskRequest from client");
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserAddTask userAddTask = new UserAddTask(client);
        userAddTask.sendAllGoals();
    }
}
