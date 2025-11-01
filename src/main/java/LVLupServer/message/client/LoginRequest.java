package LVLupServer.message.client;


import LVLupServer.LevelUpClient;
import LVLupServer.Message;
import LVLupServer.User.UserLogin;

import java.io.Serial;
import java.sql.SQLException;

public class LoginRequest extends Message<LevelUpClient> {
    @Serial
    private static final long serialVersionUID = 7L;
    private String username;
    private String password;
    private boolean isEmail;

    public LoginRequest(String username, String password, boolean isEmail) {
        this.username = username;
        this.password = password;
        this.isEmail = isEmail;
    }

    @Override
    public void apply(LevelUpClient client) throws SQLException {
        System.out.println("Applying login request : " );


        UserLogin userLogin = new UserLogin(client);
        userLogin.sendLoginValidity(username,password,isEmail);
    }
}
