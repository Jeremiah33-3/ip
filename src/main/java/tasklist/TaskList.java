package tasklist;

import java.util.ArrayList;

import task.RecurringTask;
import task.Task;

/**
 * Represents a list of tasks and provides methods to manage them.
 * This class allows adding, removing, and retrieving tasks, as well as printing task-related information.
 */
public class TaskList {
    private final ArrayList<Task> nonRecurringTasks = new ArrayList<>();
    private final ArrayList<RecurringTask> recurringTasks = new ArrayList<>();
    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added to the list.
     */
    public void addTask(Task task) {
        if (task instanceof RecurringTask) {
            recurringTasks.add((RecurringTask) task);
        } else {
            nonRecurringTasks.add(task);
        }
    }

    /**
     * Returns the list of tasks.
     *
     * @return The ArrayList containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(nonRecurringTasks);
        allTasks.addAll(recurringTasks);
        return allTasks;
    }

    /**
     * Removes a task from the task list at the specified index.
     *
     * @param index The index of the task to be removed.
     * @return The task that was removed.
     */
    public Task removeTask(int index) {
        if (index < nonRecurringTasks.size()) {
            return nonRecurringTasks.remove(index);
        } else {
            return recurringTasks.remove(index - nonRecurringTasks.size());
        }
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return nonRecurringTasks.size() + recurringTasks.size();
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param id The index of the task to be retrieved.
     * @return The task at the specified index.
     */
    public Task getTask(int id) {
        if (id < nonRecurringTasks.size()) {
            return nonRecurringTasks.get(id);
        } else {
            return recurringTasks.get(id - nonRecurringTasks.size());
        }
    }

    /**
     * Prints a confirmation message after a task has been added to the task list.
     * The message includes the task details and the updated number of tasks in the list.
     *
     * @param task The task that was added.
     */
    public String printTaskAdded(Task task) {
        int totalTasksCount = nonRecurringTasks.size() + recurringTasks.size();
        return "Got it. I've added this task: \n"
                + task + "\n"
                + "Now you have " + totalTasksCount
                + " tasks in the list.";
    }

    /**
     * Returns a list of nonrecurring tasks.
     */
    public ArrayList<Task> getNonRecurringTasks() {
        return nonRecurringTasks;
    }

    /**
     * Returns a list of recurrig tasks.
     */
    public ArrayList<RecurringTask> getRecurringTasks() {
        return recurringTasks;
    }
}
