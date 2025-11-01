package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.message.server.UserDetails;

import java.io.Serial;
import java.util.List;

public class AllUsers extends Message {
    @Serial
    private static final long serialVersionUID = 115L;
    private List<UserDetails> users;

    public AllUsers(List<UserDetails> users) {
        this.users = users;
    }
}
