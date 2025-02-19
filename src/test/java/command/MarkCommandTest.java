package command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.ToDo;
import tasklist.TaskList;

public class MarkCommandTest {
    private static final String TEST_FILE_PATH = "testdata/tasks.txt";
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() throws UserInputException {
        taskList = new TaskList();
        storage = new Storage(TEST_FILE_PATH);

        // Adding sample tasks
        taskList.addTask(new ToDo("read book"));
        taskList.addTask(new Deadline("return book", "2024-06-06 23:59"));
        taskList.addTask(new Event("fag", "2024-05-01", "2024-05-02"));
        taskList.addTask(new Event("concert", "2024-05-01", "2024-05-02"));
        taskList.getTask(3).setIsDone();
    }

    @Test
    public void testMarkCommand_validMark() {
        MarkCommand markCommand = new MarkCommand("mark", 0);
        assertDoesNotThrow(() -> markCommand.execute(taskList, storage));
        assertTrue(taskList.getTask(0).getIsDone());
    }

    @Test
    public void testMarkCommand_invalidMark() {
        MarkCommand markCommand = new MarkCommand("mark", 9);
        assertThrows(UserInputException.class, () -> markCommand.execute(taskList, storage));
        boolean isAllTasksDone = taskList.getTask(0).getIsDone()
                && taskList.getTask(1).getIsDone()
                && taskList.getTask(2).getIsDone();
        assertFalse(isAllTasksDone);
    }

    @Test
    public void testMarkCommand_validUnmark() {
        MarkCommand markCommand = new MarkCommand("unmark", 3);
        assertDoesNotThrow(() -> markCommand.execute(taskList, storage));
        assertFalse(taskList.getTask(3).getIsDone());
    }

    @Test
    public void testMarkCommand_invalidUnmark() throws UserInputException {
        MarkCommand markCommand = new MarkCommand("unmark", 2);
        String message = markCommand.execute(taskList, storage);
        assertEquals("You did not mark this as done, no panic...\n"
                + "[E][ ] fag (from: May 01 2024 to: May 02 2024)", message);
        assertFalse(taskList.getTask(2).getIsDone());
    }
}
