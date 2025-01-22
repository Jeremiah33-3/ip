public class Task {
    protected String description;
    protected boolean isDone;
    protected int id;

    public Task(String description, int id) {
        this.description = description;
        this.isDone = false;
        this.id = id;
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

    public String toString() {
        return (id + 1) + ". [" + this.getStatusIcon() + "] " + this.description;
    }
}

