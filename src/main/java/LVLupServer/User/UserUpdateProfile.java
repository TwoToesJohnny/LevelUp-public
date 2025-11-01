package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.UserRepository;
import LVLupServer.message.server.UserDetails;
import LVLupServer.message.server.profile.UpdateResult;

import java.sql.SQLException;

public record UserUpdateProfile(LevelUpClient levelUpClient) {
    private final static String errorMessage = "Update failed. ";
    private final static String incorrectUsernameMessage = "Username already exists!";
    private final static String incorrectEmailMessage = "Email already exists!";
    private final static String successMessage = "Successfully updated!";

    public boolean isUpdateValid(String email,String username,UserDetails oldUserDetails) throws SQLException {
        UserRepository userRepository = new UserRepository();
        UserDetails userNameDetails = userRepository.fetchUser(username, false);
        UserDetails userEmailDetails = userRepository.fetchUser(email, true);
        System.out.printf("oldEmail: (%s)%n",oldUserDetails.getEmail());
        System.out.printf("email: (%s)%n",email);


        if((userNameDetails.getUsername() != null)&&(!username.equals(oldUserDetails.getUsername()))){
            levelUpClient.send(new UpdateResult( incorrectUsernameMessage));
            return false;
        }
        if((userEmailDetails.getEmail() != null)&&(!email.equals(oldUserDetails.getEmail()))) {
            levelUpClient.send(new UpdateResult( incorrectEmailMessage));
            return false;
        }
        else {
            levelUpClient.send(new UpdateResult(successMessage));
            return true;
        }
    }
}
