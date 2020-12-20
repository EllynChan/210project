package model;

import org.json.JSONObject;

public class Item {
    private String taskName;      //name of the task
    private Boolean completed;    //true when task is completed.

    //EFFECTS: creates a new incomplete item named taskName
    public Item(String taskName) {
        this.taskName = taskName;
        this.completed = false;
    }

    //EFFECTS: returns name of the task
    public String getTaskName() {
        return taskName;
    }

    //EFFECTS: returns the completion status of the task
    public Boolean getTaskCompletion() {
        return completed;
    }

    //EFFECTS: returns the completion status as a String
    public String completionStatus() {
        if (getTaskCompletion()) {
            return "completed";
        } else {
            return "incomplete";
        }
    }

    //EFFECTS: changes the completion status of the task to true
    public void finishTask() {
        completed = true;
    }

    //EFFECTS: converts the item to json format
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", taskName);
        json.put("completion", completed);
        return json;
    }

}


