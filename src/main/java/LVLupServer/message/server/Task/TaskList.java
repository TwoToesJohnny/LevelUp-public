package LVLupServer.message.server.Task;

import LVLupServer.Message;

import java.io.Serial;
import java.util.ArrayList;

public class TaskList extends Message {
    @Serial
    private static final long serialVersionUID = 106L;
    ArrayList<Task> goals;
    ArrayList<Task> habits;

    public TaskList(ArrayList<Task> goals, ArrayList<Task> habits) {
        this.goals = goals;
        this.habits = habits;
    }
}
