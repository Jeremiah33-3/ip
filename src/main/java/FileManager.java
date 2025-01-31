import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private final String FILE_PATH;

    public FileManager(String path) {
        this.FILE_PATH = path;
    }

    public void saveTasksToFile(ArrayList<Task> tasks) {
        try {
            File file = new File(this.FILE_PATH);
            file.getParentFile().mkdirs(); // Ensure the directory exists
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.FILE_PATH));

            for (Task task : tasks) {
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

    public Task parseTaskFromFile(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
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


    private static String taskToFileFormat(Task task) {
        if (task instanceof ToDo) {
            return "T | " + (task.getStatusIcon()) + " | " + task.description;
        } else if (task instanceof Deadline) {
            return "D | " + (task.getStatusIcon()) + " | " + task.description + " | " + ((Deadline) task).by;
        } else if (task instanceof Event) {
            return "E | " + (task.getStatusIcon()) + " | " + task.description + " | " + ((Event) task).from + " | " + ((Event) task).to;
        }
        return "";
    }
}
