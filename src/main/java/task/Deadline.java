package task;

import exception.UserInputException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    public LocalDateTime by;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String description, String by) throws UserInputException {
        super(description);
        try {
            this.by = LocalDateTime.parse(by, INPUT_FORMATTER);
        } catch (DateTimeParseException e){
            throw new UserInputException("     hellu humans, please type date in this format: yyyy-mm-dd HH:mm");
        }
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
