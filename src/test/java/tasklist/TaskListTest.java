package tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.UserInputException;
import task.RecurringTask;
import task.Task;
import task.ToDo;

class TaskListTest {
    private TaskList taskList;
    private Task task;
    private RecurringTask recurringTask;

    @BeforeEach
    void setUp() throws UserInputException {
        taskList = new TaskList();
        task = new ToDo("dance");
        recurringTask = new RecurringTask("project meeting", "2025-01-02 23:59", "weekly");
        taskList.addTask(task);
        taskList.addTask(recurringTask);
    }

    @Test
    void testAddTask() {
        assertEquals(1, taskList.getNonRecurringTasks().size());
        assertEquals(1, taskList.getRecurringTasks().size());
    }

    @Test
    void testGetTasks() {
        ArrayList<Task> allTasks = taskList.getTasks();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task));
        assertTrue(allTasks.contains(recurringTask));
    }

    @Test
    void testRemoveNonRecurringTask() {
        Task removedTask = taskList.removeTask(0);
        assertEquals(task, removedTask);
        assertEquals(1, taskList.size());
    }

    @Test
    void testRemoveRecurringTask() {
        Task removedTask = taskList.removeTask(1);
        assertEquals(recurringTask, removedTask);
        assertEquals(1, taskList.size());
    }

    @Test
    void testSize() {
        assertEquals(2, taskList.size());
    }

    @Test
    void testGetTask() {
        assertEquals(task, taskList.getTask(0));
        assertEquals(recurringTask, taskList.getTask(1));
    }

    @Test
    void testPrintTaskAdded() {
        String message = taskList.printTaskAdded(task);
        assertTrue(message.contains("dance"));
        assertTrue(message.contains("2 tasks in the list"));
    }
}
