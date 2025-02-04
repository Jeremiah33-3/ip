import java.util.Scanner;

import command.Command;
import command.ExitCommand;
import exception.UserInputException;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;
import ui.UI;

/**
 * The HeyJudy class represents the main entry point for the HeyJudy task manager bot.
 * It handles loading tasks from a file, reading user commands, executing them, and managing the
 * bot's lifecycle.
 *
 * <p>This class interacts with the TaskList, Storage, Command, Parser,
 * and UI classes to perform its functionalities.
 */
public class HeyJudy {
    private static final String FILE_PATH = "./data/task_manager.txt";
    private static final TaskList TASK_LIST = new TaskList();
    private static final Storage STORAGE = new Storage(FILE_PATH);

    /**
     * Loads tasks from the file into the task list.
     *
     * <p>Delegates the file loading operation to the Storage class.
     */
    public static void loadTasksFromFile() {

        STORAGE.loadTasksFromFile(TASK_LIST);
    }

    /**
     * Reads user commands from the standard input and executes them.
     *
     * <p>Continues to read and process commands until an ExitCommand is issued by the user.
     * Handles invalid user input through UserInputException.
     */
    public static void readCommand() {
        Scanner scanner = new Scanner(System.in);
        String userCommand;
        boolean running = true;

        while (running) {
            try {
                userCommand = scanner.nextLine();
                Command command = Parser.parse(userCommand);
                if (command == null) {
                    throw new UserInputException("are you typing the correct commands??");
                }
                command.execute(TASK_LIST, STORAGE);
                if (command instanceof ExitCommand) {
                    running = false;
                }
            } catch (UserInputException e) {
                UI.formattedReply(e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Runs the HeyJudy bot.
     *
     * <p>Greets the user, loads existing tasks from the file, and starts reading user commands.
     */
    public static void runBot() {
        UI.greetUser();
        HeyJudy.loadTasksFromFile();
        HeyJudy.readCommand();
    }

    /**
     * The main method to launch the HeyJudy bot.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        HeyJudy.runBot();
    }
}
