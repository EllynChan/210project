package model;

import model.Item;
import model.TodoList;
import model.exception.NotInBoundsException;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TodoList t = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass.
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            TodoList t = reader.read();
            assertEquals(0, t.listSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList.json");
        try {
            TodoList t = reader.read();
            assertEquals(2, t.listSize());
            checkTask("Task1", t.getItem(0));
            checkTask("Task2", t.getItem(1));
            assertFalse(t.getItem(0).getTaskCompletion());
            assertTrue(t.getItem(1).getTaskCompletion());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (NotInBoundsException e) {
            fail("exception shouldn't have been thrown");
        }
    }
}