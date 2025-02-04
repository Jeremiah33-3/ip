package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");
    /**
     * The start date of the event.
     */
    public LocalDate from;

    /**
     * The end date of the event.
     */
    public LocalDate to;

    /**
     * Constructs a new Event with the specified description and date range as strings.
     *
     * @param description The description of the event.
     * @param from The start date as a string in the format "yyyy-MM-dd".
     * @param to The end date as a string in the format "yyyy-MM-dd".
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from, INPUT_FORMATTER);
        this.to = LocalDate.parse(to, INPUT_FORMATTER);
    }

    /**
     * Retrieves the start date of the event.
     *
     * @return The start date as a LocalDate object.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Retrieves the end date of the event.
     *
     * @return The end date as a LocalDate object.
     */
    public LocalDate getTo() {
        return to;
    }


    @Override
    public String toString() {
        String formattedFrom = from.format(OUTPUT_FORMATTER);
        String formattedTo = to.format(OUTPUT_FORMATTER);
        return "[E]" + "[" + this.getStatusIcon() + "] " + this.description
                + " (from: " + formattedFrom
                + " to: " + formattedTo + ")";
    }
}
