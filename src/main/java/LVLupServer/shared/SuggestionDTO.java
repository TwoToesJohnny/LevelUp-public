package LVLupServer.shared;

import java.io.Serializable;

public class SuggestionDTO implements Serializable {
    private int taskId;
    private String type;
    private String name;
    private String category;
    private int exp;
    private boolean isCompleted;

    public SuggestionDTO(int taskId, String type, String name, String category,
                         int exp, boolean isCompleted) {
        this.taskId = taskId;
        this.type = type;
        this.name = name;
        this.category = category;
        this.exp = exp;
        this.isCompleted = isCompleted;
    }

    public int getTaskId() { return taskId; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getExp() { return exp; }
    public boolean isCompleted() { return isCompleted; }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setExp(int exp) { this.exp = exp; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}