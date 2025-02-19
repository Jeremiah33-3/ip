package command;

import exception.UserInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import tasklist.TaskList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddCommandTest {
    private static final String TEST_FILE_PATH = "testdata/tasks.txt";
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        storage = new Storage(TEST_FILE_PATH);
    }

    @Test
    public void testAddCommand_todo() {
        AddCommand addToDo = new AddCommand("todo", "do laundry");
        assertDoesNotThrow(() -> addToDo.execute(taskList, storage));
    }

    @Test
    public void testAddCommand_event() {
        AddCommand addEvent= new AddCommand
                ("event", "do laundry", "2024-05-01", "2024-05-02");
        assertDoesNotThrow(() -> addEvent.execute(taskList, storage));
    }

    @Test
    public void testAddCommand_invalidDates() {
        AddCommand addEvent= new AddCommand
                ("event", "do laundry", "2024-06-01", "2024-05-02");
        assertThrows(UserInputException.class, () -> addEvent.execute(taskList, storage));
    }

    @Test
    public void testAddCommand_deadline() {
        AddCommand addDeadline = new AddCommand
                ("deadline", "do laundry", "2024-06-06 23:59");
        assertDoesNotThrow(() -> addDeadline.execute(taskList, storage));
    }

    @Test
    public void testAddCommand_invalidDateFormat() {
        AddCommand addDeadline = new AddCommand
                ("deadline", "do laundry", " 23:59 2024-06-06");
        assertThrows(UserInputException.class, () -> addDeadline.execute(taskList, storage));
    }

    @Test
    public void testAddCommand_recurring() {
        AddCommand addRecurring = new AddCommand
                ("recurring", "do laundry", "2024-06-06 23:59", "weekly");
        assertDoesNotThrow(() -> addRecurring.execute(taskList, storage));
    }

    @Test
    public void testAddCommand_invalidFrequency() {
        AddCommand addRecurring = new AddCommand
                ("recurring", "do laundry", "2024-06-06 23:59", "yay");
        assertThrows(UserInputException.class, () -> addRecurring.execute(taskList, storage));
    }
}
