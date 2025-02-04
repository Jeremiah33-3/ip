package parser;

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

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param userCommand The full user input as a string.
     * @return A Command object corresponding to the user's input.
     * @throws UserInputException If the user input is invalid or incomplete.
     */
    public static Command parse(String userCommand) throws UserInputException {
        String[] userArguments = userCommand.split(" ");

        if (userArguments.length < 1) {
            throw new UserInputException("What do you want? I can't see any command.");
        } else if (userArguments.length == 1) {
            if (userArguments[0].equalsIgnoreCase("bye")) {
                return new ExitCommand();
            } else if (userArguments[0].equalsIgnoreCase("list")) {
                return new ListCommand();
            } else {
                throw new UserInputException("idk what you are doing "
                        + ":< please input deadline/todo/event/mark/unmark properly");
            }
        } else {
            if (userCommand.startsWith("unmark ")
                    || userCommand.startsWith("mark ")) {
                String action = userArguments[0];
                int id = Integer.parseInt(userArguments[1]) - 1;
                return new MarkCommand(action, id);
            } else if (userCommand.startsWith("todo ")) {
                return new AddCommand("todo", userArguments[1]);
            } else if (userCommand.startsWith("deadline")) {
                String details = userCommand.substring(9);
                String[] tokens = details.split(" /by ", 2);
                if (tokens.length < 2) {
                    throw new UserInputException("Why are you not giving me your details? "
                            + "How can I set deadline for you?? Format:\n"
                            + "       deadline <description> /by <yyyy-mm-dd HH:mm>");
                }
                return new AddCommand("deadline", tokens[0], tokens[1]);
            } else if (userCommand.startsWith("event")) {
                String details = userCommand.substring(6);
                String[] tokens = details.split(" /from | /to ");
                if (tokens.length < 3) {
                    throw new UserInputException("You not giving me enough inputs, how i do for you?? "
                            + "Format:\n event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>");
                }
                return new AddCommand("event", tokens[0], tokens[1], tokens[2]);
            } else if (userCommand.startsWith("delete")) {
                if (userArguments.length > 2) {
                    throw new UserInputException("Don't be ambitious. Please delete one task at a time. "
                            + "Format:\n"
                            + "delete <id of task>");
                }
                int taskIndex = Integer.parseInt(userArguments[1]) - 1;

                return new DeleteCommand(taskIndex);
            } else if (userCommand.startsWith("find ")) {
                return new FindCommand(userCommand.substring(5));
            }
        }
        return null;
    }
}
