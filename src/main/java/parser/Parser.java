package parser;

import java.util.Arrays;
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

    /*
      Static block to initialise commands without heavy parsing.
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
        String[] TrimmedUserArguments = trimCommand(userCommand);
        String commandKeyword = TrimmedUserArguments[0].toLowerCase();

        if (COMMANDS.containsKey(commandKeyword)) {
            return COMMANDS.get(commandKeyword);
        }

        switch (commandKeyword) {
        case "mark":
        case "unmark":
            return parseMarkCommand(TrimmedUserArguments);
        case "todo":
            return parseTodoCommand(TrimmedUserArguments);
        case "deadline":
            return parseDeadlineCommand(TrimmedUserArguments);
        case "event":
            return parseEventCommand(TrimmedUserArguments);
        case "recurring":
            return parseRecurringTaskCommand(TrimmedUserArguments);
        case "delete":
            return parseDeleteCommand(TrimmedUserArguments);
        case "find":
            return parseFindCommand(TrimmedUserArguments);
        default:
            throw new UserInputException("idk what you are doing "
                    + ":< please input deadline/todo/event/mark/unmark properly");
        }
    }

    private static String[] trimCommand(String userCommand) throws UserInputException {
        if (userCommand == null || userCommand.trim().isEmpty()) {
            throw new UserInputException("What do you want? I can't see any command.");
        }

        String[] trimmedParts = Arrays.stream(userCommand.trim().split(" ", 2))
                .map(String::trim)
                .toArray(String[]::new);

        return trimmedParts;
    }

    private static Command parseMarkCommand(String[] args) throws UserInputException {
        if (args.length < 2 || args[1].isEmpty()) {
            throw new UserInputException("which task are you tring to mark/unmark? \n"
            + "input as: mark <task_id> or unmark <task_id>");
        }
        String action = args[0];
        int id = Integer.parseInt(args[1]) - 1;
        return new MarkCommand(action, id);
    }

    private static Command parseTodoCommand(String[] args) throws UserInputException {
        if (args.length < 2 || args[1].isEmpty()) {
            throw new UserInputException("what are you going to do?? please input todo <description>");
        }
        return new AddCommand("todo", args[1]);
    }

    private static Command parseDeadlineCommand(String[] args) throws UserInputException {
        try {
            String[] tokens = args[1].trim().split(" /by ", 2);

            String[] trimmedParts = Arrays.stream(tokens)
                    .map(String::trim)
                    .toArray(String[]::new);

            return new AddCommand("deadline", trimmedParts[0], trimmedParts[1]);

        } catch (IndexOutOfBoundsException e) {
            throw new UserInputException("You not giving me enough inputs, how i do for you?? "
                    + "Format:\n event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
        }
    }

    private static Command parseEventCommand(String[] args) throws UserInputException {
        try {
            String[] tokens = args[1].trim().split("/from | /to ", 3);

            String[] trimmedParts = Arrays.stream(tokens)
                    .map(String::trim)
                    .toArray(String[]::new);

            return new AddCommand("event", trimmedParts[0], trimmedParts[1], trimmedParts[2]);

        } catch (IndexOutOfBoundsException e) {
            throw new UserInputException("You not giving me enough inputs, how i do for you?? "
                    + "Format:\n event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
        }
    }

    private static Command parseRecurringTaskCommand(String[] args) throws UserInputException {
        try {
            String[] tokens = args[1].trim().split("/at | /freq ", 3);

            String[] trimmedParts = Arrays.stream(tokens)
                    .map(String::trim)
                    .toArray(String[]::new);

            return new AddCommand("recurring", trimmedParts[0], trimmedParts[1], trimmedParts[2]);

        } catch (IndexOutOfBoundsException e) {
            throw new UserInputException("You not giving me enough inputs, how i do for you?? "
                    + "Format:\n recurring "
                    + "<description> /at <yyyy-mm-dd HH:mm> /freq <daily,weekly OR monthly>");
        }
    }

    private static Command parseDeleteCommand(String[] args) throws UserInputException {
        try {
            if (args[1].split(" ").length > 1) {
                throw new UserInputException("Don't be ambitious. Please delete one task at a time. "
                        + "Format:\n"
                        + "delete <id of task>");
            }
            int taskIndex = Integer.parseInt(args[1]) - 1;
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new UserInputException("Invalid task ID format. Can you use a number!!");
        } catch (IndexOutOfBoundsException e) {
            throw new UserInputException("Helloo what am I supposed to delete "
                    + "Format:\n"
                    + "delete <id of task>");
        }
    }

    private static Command parseFindCommand(String[] args) throws UserInputException {
        if (args.length < 2 || args[1].isEmpty()) {
            throw new UserInputException
                    ("Why are you not providing a search term?? Format: find <keyword>/<date>");
        }
        return new FindCommand(args[1].trim());
    }
}
