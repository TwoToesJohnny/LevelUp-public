package LVLupServer.message.client.profile;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserProfile;

import java.io.Serial;
import java.sql.SQLException;

public class ProfileRequest extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 21L;
    public int user_ID;
    public int lvl;

    public ProfileRequest(int user_ID, int lvl) {
        this.user_ID = user_ID;
        this.lvl = lvl;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserProfile userProfile = new UserProfile(client);
        userProfile.sendProfile(lvl,user_ID);
    }
}
