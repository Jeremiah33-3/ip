package command;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.ToDo;
import tasklist.TaskList;

public class ListCommandTest {
    private static final String TEST_FILE_PATH = "testdata/tasks.txt";
    private static final String EMPTY_TASKLIST_MSG = "Psss, I don't see any task yet. Please add. Directory:\n"
            + "1. todo <description>\n"
            + "2. deadline <description> /by <yyyy-mm-dd HH:mm>\n"
            + "3. event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>\n";
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
    public void testListCommand_emptyTaskList() {
        ListCommand listCommand = new ListCommand();
        TaskList emptyList = new TaskList();

        assertThrows(UserInputException.class, () -> listCommand.execute(emptyList, storage));

        try {
            listCommand.execute(emptyList, storage);
        } catch (UserInputException e) {
            assertEquals(EMPTY_TASKLIST_MSG, e.getMessage());
        }
    }

    @Test
    public void testListCommand_existingTasks() {
        ListCommand listCommand = new ListCommand();

        assertDoesNotThrow(() -> listCommand.execute(taskList, storage));
    }
}
