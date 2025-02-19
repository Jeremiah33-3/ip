package command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.ToDo;
import tasklist.TaskList;

public class ExitCommandTest {
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
    }
    @Test
    public void testExitCommand_correctMessage() throws UserInputException {
        ExitCommand exitCommand = new ExitCommand();
        assertEquals("Bye. I hope you are more organised now."
                , exitCommand.execute(taskList, storage));
    }

    @Test
    public void testExitCommand_taskSize() {
        ExitCommand exitCommand = new ExitCommand();
        assertDoesNotThrow(() -> exitCommand.execute(taskList, storage));
        assertEquals(3, taskList.size());
    }
}
