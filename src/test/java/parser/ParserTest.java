package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import command.AddCommand;
import command.Command;
import command.DeleteCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.MarkCommand;
import exception.UserInputException;

public class ParserTest {
    @Test
    void parse_exitCommand() throws UserInputException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    void parse_listCommand() throws UserInputException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    void parse_todoCommand() throws UserInputException {
        Command command = Parser.parse("todo ReadBook");
        assertTrue(command instanceof AddCommand);
        AddCommand addCommand = (AddCommand) command;
        assertEquals("todo", addCommand.getType());
        assertEquals("ReadBook", addCommand.getDescription());
    }

    @Test
    void parse_deadlineCommand_validInput() throws UserInputException {
        Command command = Parser.parse("deadline SubmitReport /by 2025-02-10 23:59");
        assertTrue(command instanceof AddCommand);
        AddCommand addCommand = (AddCommand) command;
        assertEquals("deadline", addCommand.getType());
        assertEquals("SubmitReport", addCommand.getDescription());
        assertEquals("2025-02-10 23:59", addCommand.getBy());
    }

    @Test
    void parse_deadlineCommand_missingDetails() {
        assertThrows(UserInputException.class, () -> Parser.parse("deadline SubmitReport"));
    }

    @Test
    void parse_eventCommand_validInput() throws UserInputException {
        Command command = Parser.parse("event Conference /from 2025-03-01 /to 2025-03-03");
        assertTrue(command instanceof AddCommand);
        AddCommand addCommand = (AddCommand) command;
        assertEquals("event", addCommand.getType());
        assertEquals("Conference", addCommand.getDescription());
        assertEquals("2025-03-01", addCommand.getFrom());
        assertEquals("2025-03-03", addCommand.getTo());
    }

    @Test
    void parse_eventCommand_missingDetails() {
        assertThrows(UserInputException.class, () -> Parser.parse("event Conference /from 2025-03-01"));
    }

    @Test
    void parse_deleteCommand_validInput() throws UserInputException {
        Command command = Parser.parse("delete 2");
        assertTrue(command instanceof DeleteCommand);
        DeleteCommand deleteCommand = (DeleteCommand) command;
        assertEquals(1, deleteCommand.getTaskID());
    }

    @Test
    void parse_deleteCommand_invalidInput() {
        assertThrows(UserInputException.class, () -> Parser.parse("delete 2 3"));
    }

    @Test
    void parse_findCommand() throws UserInputException {
        Command command = Parser.parse("find book");
        assertTrue(command instanceof FindCommand);
        FindCommand findCommand = (FindCommand) command;
        assertEquals("book", findCommand.getQuery());
    }

    @Test
    void parse_markCommand() throws UserInputException {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);
        MarkCommand markCommand = (MarkCommand) command;
        assertEquals("mark", markCommand.getAction());
        assertEquals(0, markCommand.getTaskID());
    }

    @Test
    void parse_unmarkCommand() throws UserInputException {
        Command command = Parser.parse("unmark 3");
        assertTrue(command instanceof MarkCommand);
        MarkCommand markCommand = (MarkCommand) command;
        assertEquals("unmark", markCommand.getAction());
        assertEquals(2, markCommand.getTaskID());
    }

    @Test
    void parse_invalidCommand_throwsException() {
        assertThrows(UserInputException.class, () -> Parser.parse("unknownCommand"));
    }

    @Test
    void parse_emptyCommand_throwsException() {
        assertThrows(UserInputException.class, () -> Parser.parse(""));
    }
}
