package LVLupServer.User;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.CompleteTaskRepository;
import LVLupServer.dataBase.ProfileRepository;
import LVLupServer.dataBase.TaskRepository;
import LVLupServer.message.server.ProfileInfo;

import java.util.ArrayList;

public class UserProfile {
    private LevelUpClient client;

    public UserProfile(LevelUpClient client) {
        this.client = client;
    }
    public void sendProfile(int lvl,int userID){

        ProfileRepository profileRepository = new ProfileRepository();
        CompleteTaskRepository completeTaskRepository= new CompleteTaskRepository();

       ArrayList<byte[]> badgeDataList =  profileRepository.getBadgeDataList(lvl);
       byte[] profileIconData = profileRepository.getProfileData(userID);
       int requiredXP = completeTaskRepository.getLevelUp(lvl).getRequiredXp();

       client.send(new ProfileInfo(requiredXP,badgeDataList,profileIconData));

    }
}
