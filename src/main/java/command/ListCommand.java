package command;

import exception.UserInputException;
import storage.Storage;
import task.Task;
import tasklist.TaskList;

/**
 * Represents a command to list all tasks from the task list.
 */
public class ListCommand extends Command {
    private static final String EMPTY_TASKLIST_MSG = "Psss, I don't see any task yet. Please add. Directory:\n"
            + "1. todo <description>\n"
            + "2. deadline <description> /by <yyyy-mm-dd HH:mm>\n"
            + "3. event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>\n";

    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        assert tasks != null: "TaskList provided should not be null in ListCommand execute";
        assert fm != null: "Storage provided should not be null in ListCommand execute";
        int count = tasks.size();
        if (count == 0) {
            throw new UserInputException(EMPTY_TASKLIST_MSG);
        } else {
            return formatMessage(listTasks(tasks));
        }
    }

    private String formatMessage(String listedTasks) {
        StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
        result.append(listedTasks);
        return result.toString();
    }

    private String listTasks(TaskList tasks) {
        StringBuilder result = new StringBuilder();
        int index = 1;

        for (Task task : tasks.getNonRecurringTasks()) {
            result.append(index++).append(". ").append(task).append("\n");
        }

        if (!tasks.getRecurringTasks().isEmpty()) {
            result.append("\nThese are the recurring tasks in your list:\n");
            for (Task task : tasks.getRecurringTasks()) {
                result.append(index++).append(". ").append(task).append("\n");
            }
        }

        return result.toString();
    }
}
