package command;

import exception.UserInputException;
import storage.Storage;
import task.Task;
import tasklist.TaskList;

public class MarkCommand extends Command {
    private int taskID;
    private String action;

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

}
