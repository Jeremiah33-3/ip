package task;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exception.UserInputException;

public class DeadlineTest {

    @Test
    public void testDeadline_validInputs() {
        assertDoesNotThrow(() -> new Deadline("cs3230", "2025-01-02 23:59"));
    }

    @Test
    public void testDeadline_inValidInputs() {
        assertThrows(UserInputException.class,
                () -> new Deadline("cs3230", "2025-01-02"));
    }

    @Test
    public void testDeadline_doneStatus() throws UserInputException {
        Deadline deadline = new Deadline("cs3230", "2025-01-02 23:59");
        assertFalse(deadline.getIsDone());
        assertEquals(" ", deadline.getStatusIcon());
        deadline.setIsDone();
        assertTrue(deadline.getIsDone());
        assertEquals("X", deadline.getStatusIcon());
    }
}
