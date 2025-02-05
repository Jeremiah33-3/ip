package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import exception.UserInputException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private LocalDateTime by;

    /**
     * Constructs a new Deadline with the specified description and due date/time.
     *
     * @param description The description of the deadline task.
     * @param by The due date and time as a string in the format "yyyy-MM-dd HH:mm".
     * @throws UserInputException If the date/time format is incorrect.
     */
    public Deadline(String description, String by) throws UserInputException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new UserInputException("     hellu humans, "
                    + "please type date in this format: yyyy-mm-dd HH:mm");
        }
    }

    /**
     * Retrieves the due date and time of the deadline.
     *
     * @return The due date and time as a LocalDateTime object.
     */
    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        String formattedBy = by.format(OUTPUT_FORMATTER);
        return "[D]" + "[" + this.getStatusIcon() + "] " + this.getDescription() + " (by: " + formattedBy + ")";
    }
}
