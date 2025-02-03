package tasklist;

import task.Task;

import java.util.ArrayList;
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task removeTask(int index) {
        return tasks.remove(index);
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
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
