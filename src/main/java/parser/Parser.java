package parser;

import java.util.HashMap;

import command.AddCommand;
import command.Command;
import command.DeleteCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.MarkCommand;
import exception.UserInputException;

/**
 * Parses user input commands and returns the corresponding Command object.
 * This class is responsible for interpreting user input and creating the appropriate command
 * to be executed by the application.
 */
public class Parser {

    private static final HashMap<String, Command> COMMANDS = new HashMap<>();

    /**
     * Static block to initialise commands without heavy parsing.
     */
    static {
        COMMANDS.put("bye", new ExitCommand());
        COMMANDS.put("list", new ListCommand());
    }

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param userCommand The full user input as a string.
     * @return A Command object corresponding to the user's input.
     * @throws UserInputException If the user input is invalid or incomplete.
     */
    public static Command parse(String userCommand) throws UserInputException {
        if (userCommand == null || userCommand.trim().isEmpty()) {
            throw new UserInputException("What do you want? I can't see any command.");
        }

        String[] userArguments = userCommand.split(" ", 2);
        String commandKeyword = userArguments[0].toLowerCase();

        if (COMMANDS.containsKey(commandKeyword)) {
            return COMMANDS.get(commandKeyword);
        }

        switch (commandKeyword) {
        case "mark":
        case "unmark":
            return parseMarkCommand(userArguments);
        case "todo":
            return parseTodoCommand(userArguments);
        case "deadline":
            return parseDeadlineCommand(userArguments);
        case "event":
            return parseEventCommand(userArguments);
        case "delete":
            return parseDeleteCommand(userArguments);
        case "find":
            return parseFindCommand(userArguments);
        default:
            throw new UserInputException("idk what you are doing "
                    + ":< please input deadline/todo/event/mark/unmark properly");
        }
    }

    private static Command parseMarkCommand(String[] args) throws UserInputException {
        String action = args[0];
        int id = Integer.parseInt(args[1]) - 1;
        return new MarkCommand(action, id);
    }

    private static Command parseTodoCommand(String[] args) throws UserInputException {
        if (args[1].trim().isEmpty()) {
            throw new UserInputException("what are you going to do?? please input todo <description>");
        }
        return new AddCommand("todo", args[1]);
    }

    private static Command parseDeadlineCommand(String[] args) throws UserInputException {
        String[] tokens = args[1].split(" /by ", 2);
        if (tokens.length < 2) {
            throw new UserInputException("Why are you not giving me your details? "
                    + "How can I set deadline for you?? Format:\n"
                    + "       deadline <description> /by <yyyy-mm-dd HH:mm>");
        }
        return new AddCommand("deadline", tokens[0], tokens[1]);
    }

    private static Command parseEventCommand(String[] args) throws UserInputException {
        String[] tokens = args[1].split(" /from | /to ", 3);
        if (tokens.length < 3) {
            throw new UserInputException("You not giving me enough inputs, how i do for you?? "
                    + "Format:\n event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
        }
        return new AddCommand("event", tokens[0], tokens[1], tokens[2]);
    }

    private static Command parseDeleteCommand(String[] args) throws UserInputException {
        if (args.length < 2) {
            throw new UserInputException("Don't be ambitious. Please delete one task at a time. "
                    + "Format:\n"
                    + "delete <id of task>");
        }
        try {
            int taskIndex = Integer.parseInt(args[1]) - 1;
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new UserInputException("Invalid task ID format. Can you use a number!!");
        }
    }

    private static Command parseFindCommand(String[] args) throws UserInputException {
        if (args.length < 2 || args[1].trim().isEmpty()) {
            throw new UserInputException("Why are you not providing a search term?? Format: find <keyword>");
        }
        return new FindCommand(args[1]);
    }
}
