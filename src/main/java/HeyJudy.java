import java.util.Scanner;

public class HeyJudy {
    public static void greetUser() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm HeyJudy");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    public static void echoCommand(String command) {
        System.out.println("    ____________________________________________________________");
        System.out.println("        " + command);
        System.out.println("    ____________________________________________________________");
    }
    public static void exit() {
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
    public static void readCommand() {
        Scanner scanner = new Scanner(System.in);
        String userCommand;

        while (true) {
            userCommand = scanner.nextLine();
            if (userCommand.equalsIgnoreCase("bye")) {
                HeyJudy.exit();
                break;
            }
            HeyJudy.echoCommand(userCommand);
        }

        scanner.close();
    }
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        HeyJudy.greetUser();
        HeyJudy.readCommand();

    }
}
