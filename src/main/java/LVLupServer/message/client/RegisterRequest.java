package LVLupServer.message.client;
import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserRegister;

import java.sql.SQLException;

public class RegisterRequest extends Message<LevelUpClient> {
    private static final long serialVersionUID = 9L;
    private String username;
    private String password;
    private String email;

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        System.out.println("RegisterRequest: Created register request with username: " + username);
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        System.out.println("Applying register request : " );

        UserRegister userRegister = new UserRegister(client);
        userRegister.sendRegisterValidity(username,email,password);
    }
}
