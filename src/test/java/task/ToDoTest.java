package task;

import exception.UserInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest {
    @Test
    public void testDeadline_validInputs() {
        assertDoesNotThrow(() -> new ToDo("dance"));
    }

    @Test
    public void testToDo_doneStatus() throws UserInputException {
        ToDo toDo = new ToDo("dance");
        assertFalse(toDo.getIsDone());
        assertEquals(" ", toDo.getStatusIcon());
        toDo.setIsDone();
        assertTrue(toDo.getIsDone());
        assertEquals("X", toDo.getStatusIcon());
    }
}
