package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;
import tasklist.TaskList;

/**
 * Represents a storage that deals with operations between the tasks
 * in a task list and the file used to save tasks.
 * It includes loading, saving, formatting and parsing files from the task list.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage based on the given file path.
     *
     * @param path The path to the file in user's harddisk.
     */
    public Storage(String path) {
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

            for (Task task : tasksToWrite) {
                writer.write(taskToFileFormat(task));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Oops. Could not save tasks. Bops, you're stuck with me!");
            System.out.println("    ____________________________________________________________");
        }
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
                task = new ToDo(description);
                break;
            case "D":
                String[] dateTime = parts[3].split("T");
                String formattedDateTime = dateTime[0] + " " + dateTime[1];
                task = new Deadline(description, formattedDateTime);
                break;
            case "E":
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                return null;
            }

            if (isDone) {
                task.setIsDone();
            }
            return task;
        } catch (Exception e) {
            return null; // Corrupted lines
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
            System.out.println("    ____________________________________________________________");
            System.out.println("     Sad, your task list file is corrupted. "
                    + "I'm pretending it never existed.");
            System.out.println("    ____________________________________________________________");
        }
    }

    /**
     * Parses a task from the Task object to String that is savable in a file.
     *
     * @param task The task to be parsed.
     */
    private static String taskToFileFormat(Task task) {
        if (task instanceof ToDo) {
            return "T | " + (task.getStatusIcon()) + " | " + task.description;
        } else if (task instanceof Deadline) {
            return "D | " + (task.getStatusIcon()) + " | "
                    + task.description + " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            return "E | " + (task.getStatusIcon()) + " | "
                    + task.description + " | " + ((Event) task).from + " | " + ((Event) task).to;
        }
        return "";
    }
}
