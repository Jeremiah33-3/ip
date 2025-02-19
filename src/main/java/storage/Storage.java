package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import exception.UserInputException;
import task.Deadline;
import task.Event;
import task.RecurringTask;
import task.Task;
import task.ToDo;
import tasklist.TaskList;

/**
 * Represents a storage that deals with operations between the tasks
 * in a task list and the file used to save tasks.
 * It includes loading, saving, formatting and parsing files from the task list.
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private static final String LOG_FILE_PATH = "./data/log.txt";
    private final String filePath;

    /**
     * Initialises a logger to log any IOEException to the backend.
     */
    static {
        try {
            File logFile = new File(LOG_FILE_PATH);
            logFile.getParentFile().mkdirs(); // Ensure log directory exists
            FileHandler fileHandler = new FileHandler(LOG_FILE_PATH, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Could not initialize logger: " + e.getMessage());
        }
    }

    /**
     * Constructs a Storage based on the given file path.
     *
     * @param path The path to the file in user's harddisk.
     */
    public Storage(String path) {
        assert path != null && !path.isEmpty(): "file path should not be empty.";
        this.filePath = path;
    }

    /**
     * Saves the tasks in the task list to the file on user's hard disk.
     *
     * @param tasks The task list that contains the tasks to be saved.
     */
    public void saveTasksToFile(TaskList tasks) {
        try {
            File file = new File(this.filePath);
            file.getParentFile().mkdirs(); // Ensure the directory exists
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath));
            ArrayList<Task> tasksToWrite = tasks.getTasks();
            assert tasksToWrite != null: "tasks fetched from TaskList should not be null";

            for (Task task : tasksToWrite) {
                writer.write(taskToFileFormat(task));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving tasks to file: " + filePath, e);
        }
    }

    private Task parseTodoTask(String description) {
        return new ToDo((description));
    }

    private Task parseDeadline(String description, String dateTimeParts) throws UserInputException {
        String[] dateTime = dateTimeParts.split("T");
        String formattedDateTime = dateTime[0] + " " + dateTime[1];
        return new Deadline(description, formattedDateTime);
    }

    private Task parseRecurringTask(String description, String dateTimeParts, String frequency)
            throws UserInputException {
        String[] dateTime = dateTimeParts.split("T");
        String formattedDateTime = dateTime[0] + " " + dateTime[1];
        return new RecurringTask(description, formattedDateTime, frequency);
    }

    private Task parseEvent(String description, String from, String to)
            throws UserInputException {
        return new Event(description, from, to);
    }

    /**
     * Parses each task from the file from String representation to a Task object.
     *
     * @param line The string representing the task.
     */
    public Task parseTaskFromFile(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("X");
            String description = parts[2];

            Task task;
            switch (type) {
            case "T":
                task = parseTodoTask(description);
                break;
            case "D":
                assert parts.length > 4 : "Invalid Deadline task format detected at parseTaskFromFile";
                task = parseDeadline(description, parts[3]);
                break;
            case "E":
                assert parts.length > 5 : "Invalid Event task format";
                task = parseEvent(description, parts[3], parts[4]);
                break;
            case "R":
                task = parseRecurringTask(description, parts[3], parts[4]);
                break;
            default:
                return null;
            }

            if (isDone) {
                task.setIsDone();
            }
            return task;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Corrupted task entry: " + line, e);
            return null;
        }
    }

    /**
     * Loads the tasks from the file to the given TaskList object.
     *
     * @param tasks The TaskList object receiving the Task objects.
     */
    public void loadTasksFromFile(TaskList tasks) {
        File file = new File(filePath);
        if (!file.exists()) {
            return; // No saved tasks, start fresh
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.addTask(task);
                }
            }
            reader.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading tasks from file: " + filePath, e);
        }
    }

    /**
     * Parses a task from the Task object to String that is savable in a file.
     *
     * @param task The task to be parsed.
     */
    private static String taskToFileFormat(Task task) {
        return task.toFileFormat();
    }
}
