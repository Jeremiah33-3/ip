import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
public class TaskList {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static int count = 0;

    public void addTask(Task task) {
        tasks.add(task);
        count++;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task removeTask(int index) {
        Task task = tasks.remove(index);
        count--;
        return task;
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void printTaskAdded(Task task) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task: ");
        System.out.println("      " + task);
        System.out.println("      Now you have " + (count) + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
