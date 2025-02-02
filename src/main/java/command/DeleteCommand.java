package command;

import exception.UserInputException;
import storage.Storage;
import task.Task;
import tasklist.TaskList;
import ui.UI;

public class DeleteCommand extends Command {

    private int taskID;

    public DeleteCommand(int taskID) {

        this.taskID = taskID;
    }

    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        deleteTask(tasks, fm);
    }

    private void deleteTask(TaskList tasks, Storage fm) throws UserInputException {
        if (taskID >= tasks.size() || taskID < 0) {
            throw new UserInputException("Please get yourself together... this task does not exist. \n"
                    + "Check by listing: list");
        }
        Task removedTask = tasks.removeTask(taskID);
        fm.saveTasksToFile(tasks);
        UI.printLines();
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + removedTask);
        System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
        UI.printLines();
    }

    public int getTaskID() {
        return this.taskID;
    }
}
