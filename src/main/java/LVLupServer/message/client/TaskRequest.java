package LVLupServer.message.client;
import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserLogin;
import LVLupServer.dataBase.UserRepository;

import java.sql.SQLException;

public class TaskRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 8L;
    private String username;

    public TaskRequest(String username) {
        this.username = username;
        System.out.println("Created task request with username: " + username );
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserRepository userRepository = new UserRepository();
        UserLogin userLogin = new UserLogin(client);

        userLogin.sendTaskList(userRepository.fetchUser(username,false));
    }
}
