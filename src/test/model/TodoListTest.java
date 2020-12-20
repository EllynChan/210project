package model;

import model.exception.NotInBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {
    private TodoList todoList;

    @BeforeEach
    void runBefore() {
        todoList = new TodoList();
    }

    @Test
    void testAddItem() {
        todoList.addItem("do hw");
        assertEquals(1,todoList.listSize());
    }
//
    @Test
    void testGetItem() {
        try {
            Item i = new Item("do hw");
            todoList.addItem(i);
            assertEquals("do hw", todoList.getItemName(0));
            assertEquals(i, todoList.getItem(0));
        } catch (NotInBoundsException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGetItemOverBoundsException() {
        try {
            Item i = new Item("do hw");
            todoList.addItem(i);
            todoList.getItem(5);
            todoList.getItemName(5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }

    @Test
    void testGetItemNameOverBoundsException() {
        try {
            Item i = new Item("do hw");
            todoList.addItem(i);
            todoList.getItemName(5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }

    @Test
    void testGetItemUnderBoundsException() {
        try {
            Item i = new Item("do hw");
            todoList.addItem(i);
            todoList.getItem(-5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }

    @Test
    void testGetItemNameUnderBoundsException() {
        try {
            Item i = new Item("do hw");
            todoList.addItem(i);
            todoList.getItemName(-5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }


    @Test
    void testReturnAll() {
        todoList.addItem("do hw");
        todoList.addItem("do hw2");
        todoList.addItem("do hw5");
        assertEquals( "do hw, incomplete" + "\n" + "do hw2, incomplete" +
                "\n" + "do hw5, incomplete" + "\n", todoList.returnAll());
    }

    @Test
    void testRemoveItem() {
        try {
            todoList.addItem("a");
            todoList.addItem("b");
            todoList.addItem("c");
            todoList.removeItem(1);
            assertEquals(2, todoList.listSize());
        } catch (NotInBoundsException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testRemoveItemOverBoundsException() {
        try {
            todoList.addItem("a");
            todoList.addItem("b");
            todoList.addItem("c");
            todoList.removeItem(5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }

    @Test
    void testRemoveItemUnderBoundsException() {
        try {
            todoList.addItem("a");
            todoList.addItem("b");
            todoList.addItem("c");
            todoList.removeItem(-5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }


    @Test
    void testFinishTask() {
        Item task = new Item("a");
        task.finishTask();
        assertTrue(task.getTaskCompletion());
    }

    @Test
    void testFinishItem() {
        try {
            todoList.addItem("a");
            todoList.finishItem(0);
            assertTrue(todoList.getItem(0).getTaskCompletion());
        } catch (NotInBoundsException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testFinishItemOverBoundsException() {
        try {
            todoList.addItem("a");
            todoList.finishItem(5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }

    @Test
    void testFinishItemUnderBoundsException() {
        try {
            todoList.addItem("a");
            todoList.finishItem(-5);
            fail("Exception expected");
        } catch (NotInBoundsException e) {
            //pass
        }
    }

    @Test
    void testCompletionStatus() {
        Item task = new Item("a");
        assertEquals("incomplete", task.completionStatus());
        task.finishTask();
        assertEquals("completed", task.completionStatus());
    }

    @Test
    void testReturnAllForGraphicalApp() {
        todoList.addItem("do hw");
        todoList.addItem("do hw2");
        todoList.addItem("do hw5");
        assertEquals( "<html>do hw, incomplete" + "<br>" + "do hw2, incomplete" +
                "<br>" + "do hw5, incomplete" + "<br><html>", todoList.returnAllForGraphicalApp());
    }
//
}