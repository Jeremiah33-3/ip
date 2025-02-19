package command;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.RecurringTask;
import task.ToDo;
import tasklist.TaskList;

/**
 * Represents a command to add a task to the task list.
 * The task can be of type ToDo, Deadline, or Event.
 */
public class AddCommand extends Command {
    private final String taskType;
    private final String description;
    private final String fromOrDate;
    private final String toOrFreq;
    private final String by;

    /**
     * Constructs an AddCommand for a ToDo task.
     *
     * @param taskType    The type of the task (e.g., "todo").
     * @param description The description of the task.
     */
    public AddCommand(String taskType, String description) {
        this.taskType = taskType;
        this.description = description;
        this.fromOrDate = null;
        this.toOrFreq = null;
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
        this.fromOrDate = null;
        this.toOrFreq = null;
        this.by = by;
    }

    /**
     * Constructs an AddCommand for an Event or Recurring task.
     *
     * @param taskType    The type of the task (e.g., "event"/"recurring").
     * @param description The description of the task.
     * @param fromOrDate        The start date/time of the event.
     * @param toOrFreq          The end date/time of the event.
     */
    public AddCommand(String taskType, String description, String fromOrDate, String toOrFreq) {
        this.taskType = taskType;
        this.description = description;
        this.fromOrDate = fromOrDate;
        this.toOrFreq = toOrFreq;
        this.by = null;
    }

    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        assert tasks != null: "TaskList provided should not be null in AddCommand execute";
        assert fm != null: "Storage provided should not be null in AddCommand execute";
        switch (this.taskType) {
        case "todo":
            return addToDo(tasks, fm);
        case "deadline":
            return addDeadline(tasks, fm);
        case "event":
            return addEvent(tasks, fm);
        case "recurring":
            return addRecurringTask(tasks, fm);
        default:
            throw new UserInputException("don't want to admit it, but there might be a bug...");
        }
    }

    /**
     * Adds a ToDo task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the ToDo task will be added.
     * @param fm    The storage object used to save the updated task list.
     *
     * @return String The details of the task added.
     */
    private String addToDo(TaskList tasks, Storage fm) {
        ToDo toDo = new ToDo(this.description);
        tasks.addTask(toDo);
        fm.saveTasksToFile(tasks);
        return tasks.printTaskAdded(toDo);
    }

    /**
     * Adds a Deadline task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the Deadline task will be added.
     * @param fm    The storage object used to save the updated task list.
     * @throws UserInputException If there is an error in user input (e.g., invalid date format).
     *
     * @return String The details of the deadline added.
     */
    private String addDeadline(TaskList tasks, Storage fm) throws UserInputException {
        try {
            Deadline deadline = new Deadline(this.description, this.by);
            tasks.addTask(deadline);
            fm.saveTasksToFile(tasks);
            return tasks.printTaskAdded(deadline);
        } catch (UserInputException e) {
            throw new UserInputException(e.getMessage());
        }
    }

    /**
     * Adds an Event task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the Event task will be added.
     * @param fm    The storage object used to save the updated task list.
     *
     * @return String The details of the event added.
     */
    private String addEvent(TaskList tasks, Storage fm) {
        Event event = new Event(description.trim(), fromOrDate, toOrFreq);
        tasks.addTask(event);
        fm.saveTasksToFile(tasks);
        return tasks.printTaskAdded(event);
    }

    /**
     * Adds a recurring task to the task list and saves the updated list to storage.
     *
     * @param tasks The task list to which the Event task will be added.
     * @param fm    The storage object used to save the updated task list.
     *
     * @return String The details of the event added.
     */
    private String addRecurringTask(TaskList tasks, Storage fm)
            throws UserInputException {
        // TODO: parse recurring task and stores it specially
        RecurringTask recurringTask = new RecurringTask(description.trim(), fromOrDate, toOrFreq);
        tasks.addTask(recurringTask);
        fm.saveTasksToFile(tasks);
        return tasks.printTaskAdded(recurringTask);
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

    public String getFromOrDate() {
        return this.fromOrDate;
    }

    public String getToOrFreq() {
        return this.toOrFreq;
    }
}
