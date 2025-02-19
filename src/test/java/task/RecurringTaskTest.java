package task;

import exception.UserInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecurringTaskTest {
    @Test
    public void testRecurringTask_validInputs() {
        assertDoesNotThrow(
                () -> new RecurringTask("project meeting", "2025-01-02 23:59", "weekly"));
    }

    @Test
    public void testRecurringTask_inValidFrequency() {
        assertThrows(UserInputException.class,
                () -> new RecurringTask("project meeting", "2025-01-02 23:59", "yearly"));
    }

    @Test
    public void testRecurringTask_inValidDateFormat() {
        assertThrows(UserInputException.class,
                () -> new RecurringTask("project meeting", "2025-01-02", "yearly"));
    }

    @Test
    public void testRecurringTask_doneStatus() throws UserInputException {
        RecurringTask recurringTask = new RecurringTask("project meeting", "2025-01-02 23:59", "weekly");
        assertFalse(recurringTask.getIsDone());
        assertEquals(" ", recurringTask.getStatusIcon());
        recurringTask.setIsDone();
        assertTrue(recurringTask.getIsDone());
        assertEquals("X", recurringTask.getStatusIcon());
    }
}
