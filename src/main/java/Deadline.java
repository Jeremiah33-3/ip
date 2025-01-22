public class Deadline extends Task {
    protected String by;

    public Deadline(String description, int id, String by) {
        super(description, id);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + "[" + this.getStatusIcon() + "] " + this.description + " (by: " + by + ")";
    }
}
