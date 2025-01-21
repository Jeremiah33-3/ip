import java.util.Scanner;

public class HeyJudy {
    private static final String[] tasks = new String[100];
    private static int count = 0;
    public static void greetUser() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm HeyJudy.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    public static void addItem(String item) {
        tasks[count] = item;
        count++;
        System.out.println("    ____________________________________________________________");
        System.out.println("        added: " + item);
        System.out.println("    ____________________________________________________________");
    }
    public static void exit() {
        System.out.println("    ____________________________________________________________");
        System.out.println("         Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
    public static void printTask() {
        if (count == 0) {
            System.out.println("    ____________________________________________________________");
            System.out.println("        Psss, I don't see any task yet. Please add");
            System.out.println("    ____________________________________________________________");
        } else {
            System.out.println("    ____________________________________________________________");
            for (int i = 0; i < count; i++) {
                System.out.println("        " + (i + 1) + ". " + tasks[i]);
            }
            System.out.println("    ____________________________________________________________");
        }
    }
    public static void readCommand() {
        Scanner scanner = new Scanner(System.in);
        String userCommand;

        while (true) {
            userCommand = scanner.nextLine();
            if (userCommand.equalsIgnoreCase("bye")) {
                HeyJudy.exit();
                break;
            } else if (userCommand.equalsIgnoreCase("list")) {
                HeyJudy.printTask();
            } else {
                HeyJudy.addItem(userCommand);
            }
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
