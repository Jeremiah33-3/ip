package command;

import exception.UserInputException;
import storage.Storage;
import task.Task;
import tasklist.TaskList;

/**
 * Represents a command to mark a task as done or undone.
 */
public class MarkCommand extends Command {
    private final int taskID;
    private final MarkAction action;

    /**
     * An enum class representing the action type of this command.
     */
    public enum MarkAction {
        MARK,
        UNMARK
    }

    /**
     * Constructs a MarkCommand with the action and the specified task ID.
     *
     * @param action The action to perform: mark or unmark
     * @param taskID The ID of the task to be deleted.
     */
    public MarkCommand(String action, int taskID) {
        this.action = MarkAction.valueOf(action.toUpperCase());
        this.taskID = taskID;
    }

    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        assert tasks != null: "TaskList provided should not be null in MarkCommand execute";
        assert fm != null: "Storage provided should not be null in MarkCommand execute";
        String result = updateTaskStatus(tasks);
        assert result != null: "result returned by markATask in MarkCommand should not be null.";
        fm.saveTasksToFile(tasks);
        return result + "\n"
                + tasks.getTask(taskID);
    }

    /**
     * Updates the task's status based on the action (mark/unmark).
     *
     * @param tasks The task list that contains the specific task.
     * @throws UserInputException If the task ID is out of range.
     */
    private String updateTaskStatus(TaskList tasks) throws UserInputException {
        if (taskID < 0 || taskID >= tasks.size()) {
            throw new UserInputException("Invalid task number, why are you not checking...");
        }

        Task task = tasks.getTask(taskID);
        assert task != null : "Task should not be null in updateTaskStatus in MarkCommand.";

        return switch (action) {
            case MARK -> markTask(task);
            case UNMARK -> unmarkTask(task);
            default -> throw new UserInputException("Unknown command?");
        };
    }

    private String markTask(Task task) throws UserInputException {
        if (task.getIsDone()) {
            throw new UserInputException("You okay? This item is already marked done...");
        }
        task.setIsDone();
        return "Nice! I've marked this task as done:)";
    }

    private String unmarkTask(Task task) {
        if (!task.getIsDone()) {
            return "You did not mark this as done, no panic...";
        }
        task.setIsDone();
        return "OK, I've marked this task as not done yet.";
    }

    public MarkAction getAction() {
        return this.action;
    }

    public int getTaskID() {
        return taskID;
    }
}
