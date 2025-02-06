package command;

import exception.UserInputException;
import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.UI;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {

    private int taskID;

    /**
     * Constructs a DeleteCommand with the specified task ID.
     *
     * @param taskID The ID of the task to be deleted.
     */
    public DeleteCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        return deleteTask(tasks, fm);
    }

    /**
     * Deletes the task with the specified ID from the task list and updates the storage.
     *
     * @param tasks The task list from which the task will be deleted.
     * @param fm    The storage object used to save the updated task list.
     * @throws UserInputException If the task ID is invalid (e.g., out of bounds).
     */
    private String deleteTask(TaskList tasks, Storage fm) throws UserInputException {
        if (taskID >= tasks.size() || taskID < 0) {
            throw new UserInputException("Please get yourself together... this task does not exist. \n"
                    + "Check by listing: list");
        }
        Task removedTask = tasks.removeTask(taskID);
        fm.saveTasksToFile(tasks);
        return "Noted. I've removed this task:\n "
                + removedTask + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.";
    }

    public int getTaskID() {
        return this.taskID;
    }
}
