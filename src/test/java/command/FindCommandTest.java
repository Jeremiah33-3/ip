package command;

import command.FindCommand;
import exception.UserInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.ToDo;
import tasklist.TaskList;

import static org.junit.jupiter.api.Assertions.*;

public class FindCommandTest {
    private TaskList taskList;
    private Storage storage;
    private static final String TEST_FILE_PATH = "testdata/tasks.txt";

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
    public void testFindByKeyword_matchingTasks() {
        FindCommand findCommand = new FindCommand("book");
        assertDoesNotThrow(() -> findCommand.execute(taskList, storage));
    }

    @Test
    public void testFindByKeyword_noMatchingTasks() {
        FindCommand findCommand = new FindCommand("assignment");
        Exception exception = assertThrows(UserInputException.class,
                () -> findCommand.execute(taskList, storage));
        assertEquals("     No matching tasks found!", exception.getMessage());
    }

    @Test
    public void testFindByDate_matchingTasks() {
        FindCommand findCommand = new FindCommand("2024-06-06");
        assertDoesNotThrow(() -> findCommand.execute(taskList, storage));
    }

    @Test
    public void testFindByDate_noMatchingTasks() {
        FindCommand findCommand = new FindCommand("2024-12-31");
        Exception exception = assertThrows(UserInputException.class,
                () -> findCommand.execute(taskList, storage));
        assertEquals("     No matching tasks found!", exception.getMessage());
    }

    @Test
    public void testFindByDate_invalidDateFormat() {
        FindCommand findCommand = new FindCommand("06-06-2024");
        Exception exception = assertThrows(UserInputException.class,
                () -> findCommand.execute(taskList, storage));
        assertEquals("     Excuse me, pls use yyyy-mm-dd (e.g., 2019-12-02).\n",
                exception.getMessage());
    }
}
