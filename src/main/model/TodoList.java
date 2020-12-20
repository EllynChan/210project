package model;

import model.exception.NotInBoundsException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class TodoList implements Writable {
    private List<Item> tasks;     //tasks is a list of Items

    //EFFECTS: creates a new TodoList with an empty list of tasks
    public TodoList() {
        tasks = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds new task with the name TaskName to the TodoList
    public void addItem(String taskName) {
        Item i = new Item(taskName);
        tasks.add(i);
    }

    //EFFECTS: adds the item i to the TodoList
    public void addItem(Item i) {
        tasks.add(i);
    }

    //REQUIRES: i is an integer within the bounds of task size
    //EFFECTS: returns the item at index i
    public Item getItem(int i) throws NotInBoundsException {
        if (!(0 <= i && i <= tasks.size())) {
            throw new NotInBoundsException();
        }
        Item itemI = tasks.get(i);
        return itemI;
    }


    //REQUIRES: i is an integer
    //EFFECTS: returns the name of the item at the i-th index
    public String getItemName(int i) throws NotInBoundsException {
        if (!(0 <= i && i <= tasks.size())) {
            throw new NotInBoundsException();
        }
        Item itemI = tasks.get(i);
        return itemI.getTaskName();
    }

    //MODIFIES: this
    //EFFECTS: removes the item at the i-th index
    public void removeItem(int i) throws NotInBoundsException {
        if (!(0 <= i && i < tasks.size())) {
            throw new NotInBoundsException();
        }
        tasks.remove(i);
    }

    //EFFECTS: returns the size of the TodoList
    public int listSize() {
        return tasks.size();
    }

    //MODIFIES: this
    //EFFECTS: changes the completed status of the item at index to true
    public void finishItem(int index) throws NotInBoundsException {
        if (!(0 <= index && index < tasks.size())) {
            throw new NotInBoundsException();
        }
        Item item = tasks.get(index);
        item.finishTask();
    }

    //EFFECTS: returns names of all items in tasks, in the order that they're added
    public String returnAll() {
        String allTasks = "";
        for (Item i : tasks) {
            allTasks = allTasks + i.getTaskName() + ", " + i.completionStatus() + "\n";
        }
        return allTasks;
    }

    public String returnAllForGraphicalApp() {
        String allTasks = "";
        for (Item i : tasks) {
            allTasks = allTasks + i.getTaskName() + ", " + i.completionStatus() + "<br>";
        }
        return "<html>" + allTasks + "<html>";
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", todoListToJson());
        return json;
    }


    // EFFECTS: returns things in this workroom as a JSON array
    public JSONArray todoListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item i : tasks) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}
