import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        String formattedBy = by.format(OUTPUT_FORMATTER);
        return "[D]" + "[" + this.getStatusIcon() + "] " + this.description + " (by: " + formattedBy + ")";
    }
}
