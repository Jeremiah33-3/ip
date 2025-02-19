package task;

import exception.UserInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {
    @Test
    public void testEvent_validInputs() {
        assertDoesNotThrow(() -> new Event("grad trip", "2025-01-02", "2025-02-01"));
    }

    @Test
    public void testEvent_inValidDates() {
        assertThrows(UserInputException.class,
                () -> new Event("grad trip", "2025-01-02", "2024-12-30"));
    }

    @Test
    public void testEvent_doneStatus() throws UserInputException {
        Event event = new Event("grad trip", "2025-01-02", "2025-02-01");
        assertFalse(event.getIsDone());
        assertEquals(" ", event.getStatusIcon());
        event.setIsDone();
        assertTrue(event.getIsDone());
        assertEquals("X", event.getStatusIcon());
    }
}
