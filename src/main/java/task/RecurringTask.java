package task;

import exception.UserInputException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;

/**
 * Represents a task that is recurring with a certain frequency.
 */
public class RecurringTask extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("E, MMM dd yyyy, h:mm a");
    public enum Frequency {
        DAILY,
        WEEKLY,
        MONTHLY
    }

    private final LocalDateTime occurrenceDate;
    private final String frequency; // daily, weekly, monthly

    public RecurringTask(String description, String occurrenceDate, String freq)
            throws UserInputException {
        super(description);
        try {
            this.occurrenceDate = LocalDateTime.parse(occurrenceDate, INPUT_FORMATTER);
            boolean containsRightFreq = Arrays.stream(Frequency.values())
                    .anyMatch(f -> f.name().equalsIgnoreCase(freq));
            if (!containsRightFreq) {
                throw new UserInputException("JUDY IS NOT SMART ENOUGH "
                        + "TO SCHEDULE THIS FREQUENCY --> daily, weekly, or monthly only.");
            }
            this.frequency = freq;
        } catch (DateTimeParseException e) {
            throw new UserInputException("hellu humans, check if the date is valid and "
                    + "please type date in this format: yyyy-mm-dd HH:mm");
        }
    }

    public LocalDateTime getOccurrenceDate() {
        return occurrenceDate;
    }

    @Override
    public String toString() {
        String formattedTime = occurrenceDate.format(OUTPUT_FORMATTER);
        return "[R]"
                + "[" + this.getStatusIcon()
                + "] " + this.getDescription()
                + " (first occurrence: " + formattedTime + ")"
                + " (repeats: " + frequency + ")";
    }

    @Override
    public String toFileFormat() {
        return "R | " + (this.getStatusIcon()) + " | "
                + this.getDescription() + " | " + this.occurrenceDate + " | "
                + this.frequency;
    }
}

