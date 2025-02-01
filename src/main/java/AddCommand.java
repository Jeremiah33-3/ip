public class AddCommand extends Command {
    private String taskType;
    private String description;
    private String from;
    private String to;
    private String by;

    // todo
    public AddCommand(String taskType, String description) {
        this.taskType = taskType;
        this.description = description;
        this.from = null;
        this.to = null;
        this.by = null;
    }

    // deadline
    public AddCommand(String taskType, String description, String by) {
        this.taskType = taskType;
        this.description = description;
        this.from = null;
        this.to = null;
        this.by = by;
    }

    // event
    public AddCommand(String taskType, String description, String from, String to) {
        this.taskType = taskType;
        this.description = description;
        this.from = from;
        this.to = to;
        this.by = null;
    }

    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException{
        switch (this.taskType) {
            case "todo":
                addToDo(tasks, fm);
                break;
            case "deadline":
                addDeadline(tasks, fm);
                break;
            case "event":
                addEvent(tasks, fm);
                break;
            default:
                break;
        }
    }

    private void addToDo(TaskList tasks, Storage fm) {
        ToDo toDo = new ToDo(this.description);
        tasks.addTask(toDo);
        fm.saveTasksToFile(tasks);
        HeyJudy.printTask(toDo);
    }

    private void addDeadline(TaskList tasks, Storage fm) throws UserInputException {
        try {
            Deadline deadline = new Deadline(this.description, this.by);
            tasks.addTask(deadline);
            fm.saveTasksToFile(tasks);
            HeyJudy.printTask(deadline);
        } catch (UserInputException e) {
            throw new UserInputException(e.message);
        }
    }

    private void addEvent(TaskList tasks, Storage fm) {
        Event event = new Event(description.trim(), from, to);
        tasks.addTask(event);
        fm.saveTasksToFile(tasks);
        HeyJudy.printTask(event);
    }
}
