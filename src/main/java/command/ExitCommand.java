package command;

import exception.UserInputException;
import storage.Storage;
import tasklist.TaskList;

/**
 * Represents a command to exit the chat bot.
 */
public class ExitCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        return "Bye. I hope you are more organised now.";
    }
}
