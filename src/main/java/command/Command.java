package command;

import exception.UserInputException;
import storage.Storage;
import tasklist.TaskList;

/**
 * Represents an abstract command that can be executed to perform an operation on a task list.
 * Subclasses of this class implement specific commands (e.g., adding, deleting, or listing tasks).
 */
public abstract class Command {

    /**
     * Executes a command the user provided.
     *
     * @param tasks The task list to which the task will be added.
     * @param fm    The storage object used to save the updated task list.
     * @throws UserInputException If there is an error in user input (e.g., invalid date format).
     */
    public abstract void execute(TaskList tasks, Storage fm) throws UserInputException;
}

