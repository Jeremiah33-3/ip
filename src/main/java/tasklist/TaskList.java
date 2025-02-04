package tasklist;

import java.util.ArrayList;

import task.Task;

/**
 * Represents a list of tasks and provides methods to manage them.
 * This class allows adding, removing, and retrieving tasks, as well as printing task-related information.
 */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the list of tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Removes a task from the task list at the specified index.
     *
     * @param index The index of the task to be removed.
     * @return The task that was removed.
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param id The index of the task to be retrieved.
     * @return The task at the specified index.
     */
    public Task getTask(int id) {
        return tasks.get(id);
    }

    /**
     * Prints a confirmation message after a task has been added to the task list.
     * The message includes the task details and the updated number of tasks in the list.
     *
     * @param task The task that was added.
     */
    public void printTaskAdded(Task task) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task: ");
        System.out.println("      " + task);
        System.out.println("      Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
    }
}
