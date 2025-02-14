package task;

/**
 * An abstract class representing a task object to be inherited by the specific type of tasks.
 */
public abstract class Task {
    /**
     * Description of the task.
     */
    private String description;

    /**
     * Indicates whether the task is done.
     */
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if the task is done, otherwise a space " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Toggles the completion status of the task.
     * If the task is done, it will be marked as not done and vice versa.
     */
    public void setIsDone() {
        this.isDone = !this.isDone;
    }

    /**
     * Retrieves the completion status of the task.
     *
     * @return {@code true} if the task is done, {@code false} otherwise.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the task.
     * This method must be implemented by subclasses.
     *
     * @return A string representation of the task.
     */
    public abstract String toString();

    public abstract String toFileFormat();
}
