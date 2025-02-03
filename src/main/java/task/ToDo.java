package task;

/**
 * Represents a task the user need to complete.
 */
public class ToDo extends Task {
    /**
     * Constructs a new ToDo task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + "[" + this.getStatusIcon() + "] " + this.description;
    }
}
