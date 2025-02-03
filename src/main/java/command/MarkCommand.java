package command;

import exception.UserInputException;
import storage.Storage;
import task.Task;
import tasklist.TaskList;

/**
 * Represents a command to mark a task as done or undone.
 */
public class MarkCommand extends Command {
    private int taskID;
    private String action;

    /**
     * Constructs a MarkCommand with the action and the specified task ID.
     *
     * @param action The action to perform: mark or unmark
     * @param taskID The ID of the task to be deleted.
     */
    public MarkCommand(String action, int taskID) {
        this.action = action;
        this.taskID = taskID;
    }

    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        String result = markATask(tasks);
        fm.saveTasksToFile(tasks);
        System.out.println("    ____________________________________________________________");
        System.out.println("     " + result);
        System.out.println("     " + tasks.getTask(taskID));
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Marks a task as done or undone according to the action the user has provided.
     *
     * @param tasks The task list that might contain the specific task to be marked.
     * @throws UserInputException If user is accessing a non-existent task (wrong index).
     */
    private String markATask(TaskList tasks) throws UserInputException {
        if (taskID >= tasks.size() || taskID < 0) {
            throw new UserInputException("invalid task number, why are you not checking...");
        }

        Task task = tasks.getTask(taskID);

        if (action.equalsIgnoreCase("mark")) {
            if (task.getIsDone()) {
                throw new UserInputException("You okay? This item is already marked done...");
            }
            task.setIsDone();
            return "Nice! I've marked this task as done:";
        } else if (action.equalsIgnoreCase("unmark")) {
            if (!task.getIsDone()) {
                return "You did not mark this as done, no panic...";
            }
            task.setIsDone();
            return "OK, I've marked this task as not done yet:";
        } else {
            return "unrecognised command :(";
        }
    }

    public String getAction() {
        return this.action;
    }

    public int getTaskID() {
        return taskID;
    }
}
