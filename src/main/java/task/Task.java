package task;

public abstract class Task {
    public String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {

        return (isDone ? "X" : " ");
    }

    public void setIsDone() {
        this.isDone = !this.isDone;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public abstract String toString();
}
