package command;

import exception.UserInputException;
import storage.Storage;
import tasklist.TaskList;
import ui.UI;

/**
 * Represents a command to list all tasks from the task list.
 */
public class ListCommand extends Command {
    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        int count = tasks.size();
        if (count == 0) {
            String message = "     Psss, I don't see any task yet. Please add. Directory:\n"
                    + "      1. todo <description>\n"
                    + "      2. deadline <description> /by <yyyy-mm-dd HH:mm>\n"
                    + "      3. event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>\n";
            throw new UserInputException(message);
        } else {
            StringBuilder result = new StringBuilder();
            result.append("     Here are the tasks in your list:\n");
            for (int i = 0; i < count; i++) {
                result.append("     ").append(i + 1).append(". ").append(tasks.getTask(i)).append("\n");
            }
            return result.toString();
        }
    }
}
