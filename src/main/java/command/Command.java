package command;

import exception.UserInputException;
import storage.Storage;
import tasklist.TaskList;

public abstract class Command {

    public abstract void execute(TaskList tasks, Storage fm) throws UserInputException;
}

