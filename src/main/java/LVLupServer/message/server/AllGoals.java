package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.message.server.Task.Task;

import java.io.Serial;
import java.util.ArrayList;

public class AllGoals extends Message {
    @Serial
    private static final long serialVersionUID = 109L;
    private ArrayList<Task> goals;

    public AllGoals(ArrayList<Task> goals) {
        this.goals = goals;
    }

}
