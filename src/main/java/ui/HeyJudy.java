package ui;

import command.Command;
import exception.UserInputException;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;

/**
 * The ui.HeyJudy class represents the main entry point for the ui.HeyJudy task manager bot.
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

    public HeyJudy() {
        assert STORAGE != null: "STORAGE const in HeyJudy class should not be null.";
        assert TASK_LIST != null: "TASK_LIST const in HeyJudy class should not be null.";
        loadTasksFromFile();
    }

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
    public String readCommand(String userCommand) {
        try {
            Command command = Parser.parse(userCommand);
            if (command == null) {
                return "are you typing the correct commands??";
            }

            return command.execute(TASK_LIST, STORAGE);
        } catch (UserInputException e) {
                return(e.getMessage());
        }
    }
}
