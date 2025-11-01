package LVLupServer.message.client;

import LVLupServer.LevelUpClient;
import LVLupServer.Message;

import java.sql.SQLException;

public class Disconnect extends Message<LevelUpClient> {
    private static final long serialVersionUID = 25L;
    private  String user_ID;

    public Disconnect(String user_ID){
        this.user_ID = user_ID;
    }
    @Override
    public void apply(LevelUpClient client) throws SQLException {}
}
