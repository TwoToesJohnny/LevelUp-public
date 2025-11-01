package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.Users.Users;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.dataBase.UserRepository;
import LVLupServer.message.server.Task.Task;
import LVLupServer.message.server.Task.TaskList;
import LVLupServer.message.server.InvalidLogin;
import LVLupServer.message.server.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;


public record UserLogin(LevelUpClient levelUpClient) {
    private final static String incorrectPasswordMessage = "Invalid password";
    private final static String incorrectUsernameMessage = "Invalid username";
    private final static String alreadyLoggedInMessage = "User is already logged in";

    public void sendLoginValidity(String username, String password, boolean isEmail) throws SQLException {

        UserRepository userRepository = new UserRepository();
        UserDetails user = userRepository.fetchUser(username, isEmail);

        if (user.getUsername() == null) {
            InvalidLogin msg = new InvalidLogin(incorrectUsernameMessage);
            levelUpClient.send(msg);
        } else if (user.isLoggedIn()) {
            InvalidLogin msg = new InvalidLogin(alreadyLoggedInMessage);
            levelUpClient.send(msg);
        } else if (BCrypt.checkpw(password, user.getPassword())) {
            levelUpClient.userDetails = user;
            Users.addClient(levelUpClient);
            levelUpClient.send(user);
        } else {
            InvalidLogin msg = new InvalidLogin(incorrectPasswordMessage);
            levelUpClient.send(msg);
        }

    }

    public void sendTaskList(UserDetails user) throws SQLException {
        TaskRepository taskRepository = new TaskRepository();
        ArrayList<Task> userHabits = taskRepository.fetchHabits(user.getUserID());
        ArrayList<Task> userGoals = taskRepository.fetchGoals(user.getUserID());
        levelUpClient.send(new TaskList(userGoals, userHabits));
    }
}
