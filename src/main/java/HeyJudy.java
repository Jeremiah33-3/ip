import command.Command;
import command.ExitCommand;
import exception.UserInputException;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;
import ui.UI;

import java.util.Scanner;

public class HeyJudy {
    private static final String FILE_PATH = "./data/task_manager.txt";
    private static final TaskList tasks = new TaskList();
    private static final Storage fm = new Storage(FILE_PATH);

    public static void loadTasksFromFile() {

        fm.loadTasksFromFile(tasks);
    }

    public static void readCommand() {
        Scanner scanner = new Scanner(System.in);
        String userCommand;
        boolean running = true;

        while (running) {
            try {
                userCommand = scanner.nextLine();
                Command command = Parser.parse(userCommand);
                if (command == null) {
                    throw new UserInputException("hmmm... don't want to admit it, but i might have a bug..");
                }
                command.execute(tasks, fm);
                if (command instanceof ExitCommand) {
                    running = false;
                }
            } catch (UserInputException e) {
                UI.formattedReply(e.getMessage());
            }
        }

        scanner.close();
    }

    public static void runBot() {
        UI.greetUser();
        HeyJudy.loadTasksFromFile();
        HeyJudy.readCommand();
    }

    public static void main(String[] args) {
        HeyJudy.runBot();
    }
}
