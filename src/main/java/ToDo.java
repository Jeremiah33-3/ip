public class ToDo extends Task {
    public ToDo(String description, int id) {
        super(description, id);
    }

    @Override
    public String toString() {
        return "[D]" + "[" + this.getStatusIcon() + "] " + this.description;
    }
}
