package storage;

import exception.UserInputException;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;
import tasklist.TaskList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private static final String TEST_FILE_PATH = "testdata/tasks.txt";
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize Storage and TaskList before each test
        storage = new Storage(TEST_FILE_PATH);
        taskList = new TaskList();

        File file = new File(TEST_FILE_PATH);

        // Ensure the file is empty or doesn't exist before each test
        if (file.exists()) {
            new FileWriter(TEST_FILE_PATH).close(); // Clear the file contents
        } else {
            file.getParentFile().mkdirs(); // Create the directory if it doesn't exist
            file.createNewFile(); // Create the file if it doesn't exist
        }
    }

    @Test
    void testSaveTasksToFile() {
        try {
            // Add tasks to the TaskList
            taskList.addTask(new ToDo("Read a book"));
            taskList.addTask(new Deadline("Submit assignment", "2023-10-15 23:59")); // This may throw UserInputException
            taskList.addTask(new Event("Team meeting", "2023-10-10", "2023-10-10"));

            // Save tasks to file
            storage.saveTasksToFile(taskList);

            // Verify the file was created and contains the expected content
            File file = new File(TEST_FILE_PATH);
            assertTrue(file.exists(), "File should exist after saving tasks.");
        } catch (UserInputException e) {
            fail("Unexpected UserInputException: " + e.getMessage());
        }
    }

    @Test
    void testSaveTasksToFile_InvalidDeadline() {
        // Test that an invalid deadline throws UserInputException
        int sizeOfTaskList = taskList.size();
        assertThrows(UserInputException.class, () -> {
            taskList.addTask(new Deadline("Submit assignment", "invalid-date"));
        });

        // Verify that no tasks were added
        assertEquals(sizeOfTaskList, taskList.size(), "No tasks should be added if an exception is thrown.");
    }

    @Test
    void testParseTaskFromFile() {
        // Test parsing a ToDo task
        Task todoTask = storage.parseTaskFromFile("T |   | Read a book");
        assertNotNull(todoTask, "Parsed ToDo task should not be null");
        assertTrue(todoTask instanceof ToDo, "Parsed task should be an instance of ToDo");
        assertEquals("Read a book", todoTask.description, "ToDo description should match");

        // Test parsing a Deadline task
        Task deadlineTask = storage.parseTaskFromFile("D | X | Submit assignment | 2023-10-15T23:59");
        assertNotNull(deadlineTask, "Parsed Deadline task should not be null");
        assertTrue(deadlineTask instanceof Deadline, "Parsed task should be an instance of Deadline");
        assertEquals("Submit assignment", deadlineTask.description, "Deadline description should match");
        assertTrue(deadlineTask.getIsDone(), "Deadline task should be marked as done");

        // Test parsing an Event task
        Task eventTask = storage.parseTaskFromFile("E |   | Team meeting | 2023-10-10 | 2023-10-10");
        assertNotNull(eventTask, "Parsed Event task should not be null");
        assertTrue(eventTask instanceof Event, "Parsed task should be an instance of Event");
        assertEquals("Team meeting", eventTask.description, "Event description should match");
    }

    @Test
    void testLoadTasksFromFile() {
        // Prepare a test file with tasks
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write("T |   | Read a book");
            writer.newLine();
            writer.write("D | X | Submit assignment | 2023-10-15T23:59");
            writer.newLine();
            writer.write("E |   | Team meeting | 2023-10-10 | 2023-10-10");
            writer.newLine();
        } catch (IOException e) {
            fail("Failed to create test file: " + e.getMessage());
        }

        // Load tasks from the file
        storage.loadTasksFromFile(taskList);

        // Verify the tasks were loaded correctly
        assertEquals(3, taskList.size(), "There should be 3 tasks loaded from the file");

        // Verify the first task (ToDo)
        Task todoTask = taskList.getTask(0);
        assertTrue(todoTask instanceof ToDo, "First task should be an instance of ToDo");
        assertEquals("Read a book", todoTask.description, "ToDo description should match");

        // Verify the second task (Deadline)
        Task deadlineTask = taskList.getTask(1);
        assertTrue(deadlineTask instanceof Deadline, "Second task should be an instance of Deadline");
        assertEquals("Submit assignment", deadlineTask.description, "Deadline description should match");
        assertTrue(deadlineTask.getIsDone(), "Deadline task should be marked as done");

        // Verify the third task (Event)
        Task eventTask = taskList.getTask(2);
        assertTrue(eventTask instanceof Event, "Third task should be an instance of Event");
        assertEquals("Team meeting", eventTask.description, "Event description should match");
    }

    @Test
    void testLoadTasksFromFile_FileDoesNotExist() {
        // Delete the test file if it exists
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }

        // Attempt to load tasks from a non-existent file
        storage.loadTasksFromFile(taskList);

        // Verify no tasks were loaded
        assertEquals(0, taskList.size(), "No tasks should be loaded if the file does not exist");
    }

    @Test
    void testParseTaskFromFile_CorruptedLine() {
        // Test parsing a corrupted line
        Task corruptedTask = storage.parseTaskFromFile("Invalid | Format | Here");
        assertNull(corruptedTask, "Parsing a corrupted line should return null");
    }
}