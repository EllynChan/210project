package model;

import model.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String name, Item item) {
        assertEquals(name, item.getTaskName());
    }
}
