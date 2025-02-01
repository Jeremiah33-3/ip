public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        UI.formattedReply("Bye. I hope you are more organised now.");
    }
}
