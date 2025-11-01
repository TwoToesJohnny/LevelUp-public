package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.Users.Users;
import LVLupServer.dataBase.BackgroundItemRepository;
import LVLupServer.dataBase.ItemRepository;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.dataBase.UserRepository;
import LVLupServer.message.server.InvalidRegistry;
import LVLupServer.message.server.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

public record UserRegister(LevelUpClient levelUpClient) {


    public void sendRegisterValidity(String username, String email, String password) throws SQLException {
        final int introGoalID1 = 9;
        final int introGoalID2 = 10;
        UserRepository userRepository = new UserRepository();
        String notUniqueUsername = "Username already taken";
        String notUniqueEmail = "Email already registered";

        if (userRepository.fetchUser(username, false).getUsername() != null)
            levelUpClient.send(new InvalidRegistry(notUniqueUsername));
        else if ((userRepository.fetchUser(email, true).getUsername() != null))
            levelUpClient.send(new InvalidRegistry(notUniqueEmail));
        else {
            userRepository.createUser(username, email, BCrypt.hashpw(password, BCrypt.gensalt()));
            UserDetails user = userRepository.fetchUser(username, false);

            TaskRepository taskRepository = new TaskRepository();
            levelUpClient.userDetails = user;
            Users.addClient(levelUpClient);
            addDefaultItems();

            taskRepository.createUserTask(user.getUserID(), introGoalID1, true);
            taskRepository.createUserTask(user.getUserID(), introGoalID2, true);
            levelUpClient.send(user);
        }
    }
    private void addDefaultItems() throws SQLException {
        final int DEFAULT_BORDER_ITEM_ID = 8;
        final int DEFAULT_BACKGROUND_ITEM_ID = 10;
        int userID = levelUpClient.userDetails.getUserID();
        ItemRepository itemRepository = new ItemRepository();
        itemRepository.purchaseBorderItem(userID,DEFAULT_BORDER_ITEM_ID);

        BackgroundItemRepository backgroundItemRepository = new BackgroundItemRepository();
        backgroundItemRepository.purchaseBackgroundItem(userID,DEFAULT_BACKGROUND_ITEM_ID);
    }
}
