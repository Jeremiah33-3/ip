package command;

import exception.UserInputException;
import storage.Storage;
import tasklist.TaskList;
import ui.UI;

/**
 * Represents a command to exit the chat bot.
 */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        UI.formattedReply("Bye. I hope you are more organised now.");
    }
}
