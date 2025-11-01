package LVLupServer.message.client.appearance;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.UserRepository;

import java.io.Serial;
import java.sql.SQLException;

public class UpdateProfileBorder extends Message<LevelUpClient> {

    @Serial
    private static final long serialVersionUID = 24L;

    private int itemID;

    public UpdateProfileBorder(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserRepository userRepository = new UserRepository();
        userRepository.updateUserProfileBorder(itemID,client.userDetails.getUserID());
    }
}
