package command;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.ToDo;
import tasklist.TaskList;

/**
 * Represents a command to add a task to the task list.
 * The task can be of type ToDo, Deadline, or Event.
 */
public class AddCommand extends Command {
    private String taskType;
    private String description;
    private String from;
    private String to;
    private String by;

    /**
     * Constructs an AddCommand for a ToDo task.
     *
     * @param taskType    The type of the task (e.g., "todo").
     * @param description The description of the task.
     */
    public AddCommand(String taskType, String description) {
        this.taskType = taskType;
        this.description = description;
        this.from = null;
        this.to = null;
        this.by = null;
    }

    /**
     * Constructs an AddCommand for a Deadline task.
     *
     * @param taskType    The type of the task (e.g., "deadline").
     * @param description The description of the task.
     * @param by          The deadline date/time for the task.
     */
    public AddCommand(String taskType, String description, String by) {
        this.taskType = taskType;
        this.description = description;
        this.from = null;
        this.to = null;
        this.by = by;
    }

    /**
     * Constructs an AddCommand for an Event task.
     *
     * @param taskType    The type of the task (e.g., "event").
     * @param description The description of the task.
     * @param from        The start date/time of the event.
     * @param to          The end date/time of the event.
     */
    public AddCommand(String taskType, String description, String from, String to) {
        this.taskType = taskType;
        this.description = description;
        this.from = from;
        this.to = to;
        this.by = null;
    }

    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
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

    /**
     * Adds a ToDo task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the ToDo task will be added.
     * @param fm    The storage object used to save the updated task list.
     */
    private void addToDo(TaskList tasks, Storage fm) {
        ToDo toDo = new ToDo(this.description);
        tasks.addTask(toDo);
        fm.saveTasksToFile(tasks);
        tasks.printTaskAdded(toDo);
    }

    /**
     * Adds a Deadline task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the Deadline task will be added.
     * @param fm    The storage object used to save the updated task list.
     * @throws UserInputException If there is an error in user input (e.g., invalid date format).
     */
    private void addDeadline(TaskList tasks, Storage fm) throws UserInputException {
        try {
            Deadline deadline = new Deadline(this.description, this.by);
            tasks.addTask(deadline);
            fm.saveTasksToFile(tasks);
            tasks.printTaskAdded(deadline);
        } catch (UserInputException e) {
            throw new UserInputException(e.getMessage());
        }
    }

    /**
     * Adds an Event task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the Event task will be added.
     * @param fm    The storage object used to save the updated task list.
     */
    private void addEvent(TaskList tasks, Storage fm) {
        Event event = new Event(description.trim(), from, to);
        tasks.addTask(event);
        fm.saveTasksToFile(tasks);
        tasks.printTaskAdded(event);
    }

    public String getType() {
        return this.taskType;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBy() {
        return this.by;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }
}
