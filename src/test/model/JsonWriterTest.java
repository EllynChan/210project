package model;

import model.Item;
import model.TodoList;
import model.exception.NotInBoundsException;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TodoList t = new TodoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            TodoList t = new TodoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList.json");
            t = reader.read();
            assertEquals(0, t.listSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            TodoList t = new TodoList();
            t.addItem("Task1");
            t.addItem("Task2");
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json");
            t = reader.read();
            assertEquals(2, t.listSize());
            checkTask("Task1", t.getItem(0));
            checkTask("Task2", t.getItem(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NotInBoundsException e) {
            fail("Exception should not have been thrown");
        }
    }
}