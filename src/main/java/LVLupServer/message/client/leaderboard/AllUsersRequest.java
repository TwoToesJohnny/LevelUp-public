package LVLupServer.message.client.leaderboard;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.dataBase.UserRepository;
import LVLupServer.message.server.AllUsers;
import LVLupServer.message.server.UserDetails;

import java.sql.SQLException;
import java.util.List;

public class AllUsersRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 15L;
    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserRepository userRepository = new UserRepository();
        List<UserDetails> usersDetailsList =  userRepository.fetchUsers();

        client.send(new AllUsers(usersDetailsList));
    }
}
