package ui;

import model.TodoList;
import model.exception.NotInBoundsException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ConsoleApp {

    private static final String JSON_STORE = "./data/todoList.json";
    private Scanner input;
    private TodoList todoList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Random rand = new Random();
    int randNum = rand.nextInt(100) + 1;

    //from TellerApp
    //EFFECTS: runs the application
    public ConsoleApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();
        startOptions();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                quitOptions();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }

    private void startOptions() {
        System.out.println("Welcome to the app. "
                + "Do you wish load the existing todo-list?");

        String selection = "";

        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            loadTodoList();
        }
    }

    //EFFECTS: constructs a Scanner and a TodoList
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        todoList = new TodoList();
    }

    //EFFECTS: displays a menu
    private void displayMenu() {
        System.out.println("\nYou are in a todo-list app");
        System.out.println("\tm -> manage to-do list");
        System.out.println("\ts -> save to-do list");
        System.out.println("\tl -> load to-do list");
        System.out.println("\tf -> fortune");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("m")) {
            doManageList();
        } else if (command.equals("f")) {
            doFortune();
        } else if (command.equals("s")) {
            saveTodoList();
        } else if (command.equals("l")) {
            loadTodoList();
        } else {
            System.out.println("Selection not valid");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void doManageList() {
        String selection = "";

        while (!(selection.equals("v") || selection.equals("a") || selection.equals("d") || selection.equals("m"))) {

            System.out.println("\tv -> view to-do list");
            System.out.println("\ta -> add a task to the to-do list");
            System.out.println("\td -> delete a task from the to-do list");
            System.out.println("\tm -> mark a task as complete");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("v")) {
            viewList();
        } else if (selection.equals("a")) {
            System.out.println("\n name the task");
            addToList(input.next());
        } else if (selection.equals("d")) {
            System.out.println("\n type the index of the task you want to remove");
            deleteFromListParsed(input.next());
        } else if (selection.equals("m")) {
            System.out.println("\n type the index of the task you finished");
            markCompleteParsed(input.next());
        } else {
            System.out.println("Selection not valid");
        }
    }

    //MODIFIES: this
    //EFFECTS: add an item named s to todoList.
    private void addToList(String s) {
        todoList.addItem(s);
    }

    //EFFECTS: prints out the names of all the items in todoList in the order they're added
    private void viewList() {
        System.out.println(todoList.returnAll());
    }

    //MODIFIES: this
    //EFFECTS: remove the item at index i from the todoList, throws exception if there is no
    // task with index = i
    private void deleteFromList(int i) {
        try {
            todoList.removeItem(i);
        } catch (NotInBoundsException e) {
            System.out.println("there is no task with that index");
        }
    }

    //EFFECTS: Parse input value, remove the item at index s from the todoList if
    //the input is an integer, throws NumberFormatException otherwise
    private void deleteFromListParsed(String s) {
        try {
            deleteFromList(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            System.out.println("input must be an integer");
        }
    }


    //MODIFIES: this
    //EFFECTS: set the completed status of the item at index i to true, throws exception if there
    // is no task with index = i
    private void markComplete(int i) {
        try {
            todoList.finishItem(i);
        } catch (NotInBoundsException e) {
            System.out.println("there is no task with that index");
        }
    }

    //EFFECTS: Parse input value, mark the item at index s as complete if
    //the input is an integer, throws NumberFormatException otherwise
    private void markCompleteParsed(String s) {
        try {
            markComplete(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            System.out.println("input must be an integer");
        }
    }

    //EFFECTS: fun thing that returns your chance of having a successful day using Random
    private void doFortune() {
        System.out.println("your chance of having a successful day today seems to be "
                 + randNum + "%");

    }

    // EFFECTS: saves the todoList to file
    private void saveTodoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(todoList);
            jsonWriter.close();
            System.out.println("Saved todoList to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todoList from file
    private void loadTodoList() {
        try {
            todoList = jsonReader.read();
            System.out.println("Loaded todoList from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: saves todolist to json if input is y, does nothing otherwise
    private void quitOptions() {
        System.out.println("You have selected to quit the program. "
                + "Do you wish to save your todo-list before quitting?");

        String selection = "";

        while (!(selection.equals("y") || selection.equals("n"))) {
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("y")) {
            saveTodoList();
        }
    }
}
