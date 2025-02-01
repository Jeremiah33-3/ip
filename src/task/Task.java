public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {

        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setIsDone() {
        this.isDone = !this.isDone;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public abstract String toString();
}
