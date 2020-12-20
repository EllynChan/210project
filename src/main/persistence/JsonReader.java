package persistence;

import model.Item;
import model.TodoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads todoList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TodoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private TodoList parseTodoList(JSONObject jsonObject) {
        TodoList t = new TodoList();
        addTasks(t, jsonObject);
        return t;
    }

    // MODIFIES: todolist
    // EFFECTS: parses tasks from JSON object and adds them to todolist
    private void addTasks(TodoList t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(t, nextTask);
        }
    }

    // MODIFIES: todolist
    // EFFECTS: parses todolist from JSON object and adds it to todolist
    private void addTask(TodoList t, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Item i = new Item(name);
        if (jsonObject.getBoolean("completion")) {
            i.finishTask();
        }
        t.addItem(i);
    }
}
