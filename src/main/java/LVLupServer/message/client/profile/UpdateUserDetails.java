package LVLupServer.message.client.profile;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserUpdateProfile;
import LVLupServer.dataBase.UserRepository;
import LVLupServer.message.server.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

public class UpdateUserDetails  extends Message<LevelUpClient> {

        private static final long serialVersionUID = 22L;
    private UserDetails oldUserdetails;

    public UpdateUserDetails(UserDetails oldUserdetails, String username, String password, String email, int user_ID, byte[] profileIcon) {
        this.oldUserdetails = oldUserdetails;
        this.username = username;
        this.password = password;
        this.email = email;
        User_ID = user_ID;
        this.profileIcon = profileIcon;
    }

    private String username;
    private String password;
    private String email;
    private int User_ID;
    private byte[] profileIcon;

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        UserUpdateProfile userUpdateProfile = new UserUpdateProfile(client);
        if(userUpdateProfile.isUpdateValid(email,username,oldUserdetails)){
            UserRepository userRepository = new UserRepository();
            userRepository.updateUser(username,email, BCrypt.hashpw(password,BCrypt.gensalt()),User_ID,profileIcon);
        }

    }
}
