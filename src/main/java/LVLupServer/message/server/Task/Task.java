package LVLupServer.message.server.Task;

import java.time.LocalDateTime;

import LVLupServer.Message;

public class Task extends Message {

    private int taskID;
    private boolean isCompleted;
    private String name;
    private int exp;
    private String type;
    private boolean isApproved;

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;


    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task(){}

    public int getTaskID() {
        return taskID;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getName() {
        return name;
    }
}