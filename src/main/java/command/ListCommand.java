package command;

import exception.UserInputException;
import storage.Storage;
import tasklist.TaskList;
import ui.UI;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        int count = tasks.size();
        if (count == 0) {
            String message = "     Psss, I don't see any task yet. Please add. Directory:\n"
                    + "      1. todo <description>\n"
                    + "      2. deadline <description> /by <yyyy-mm-dd HH:mm>\n"
                    + "      3. event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>\n";
            throw new UserInputException(message);
        } else {
            UI.printLines();
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < count; i++) {
                System.out.println("     " + (i + 1) + ". " + tasks.getTask(i));
            }
            UI.printLines();
        }
    }
}
