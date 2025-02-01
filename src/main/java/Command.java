public abstract class Command {

    public abstract void execute(TaskList tasks, Storage fm) throws UserInputException;
}

