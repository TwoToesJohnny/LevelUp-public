package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.message.server.Task.Task;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class AllHabits extends Message {
    @Serial
    private static final long serialVersionUID = 110L;
    private ArrayList<Task> habits;

    public AllHabits(ArrayList<Task> habits) {
        this.habits = habits;
    }
}
