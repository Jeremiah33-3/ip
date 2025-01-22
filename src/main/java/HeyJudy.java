import java.util.Scanner;

public class HeyJudy {
    private static final Task[] tasks = new Task[100];
    private static int count = 0;
    public static void greetUser() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm HeyJudy.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    public static void addItem(String item) {
        Task newTask = new Task(item, count);
        tasks[count] = newTask;
        count++;
        System.out.println("    ____________________________________________________________");
        System.out.println("        added: " + item);
        System.out.println("    ____________________________________________________________");
    }

    public static String markTask(String action, Task task) {
        if (action.equalsIgnoreCase("mark")) {
            if (task.getIsDone()) {
                return "You okay? This item is already marked done...";
            }
            task.setIsDone();
            return "Nice! I've marked this task as done:";
        } else if (action.equalsIgnoreCase("unmark")) {
            if (!task.getIsDone()) {
                return "You did not mark this as done, no panic...";
            }
            task.setIsDone();
            return "OK, I've marked this task as not done yet:";
        } else {
            return "unrecognised command :(";
        }
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
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < count; i++) {
                System.out.println("     " + tasks[i]);
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
            } else if (userCommand.startsWith("unmark ")
                    || userCommand.startsWith("mark ")) {
                String[] splittedCommands = userCommand.split(" ");
                if (splittedCommands.length < 2) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     what task, bro? no task given to me!");
                    System.out.println("    ____________________________________________________________");
                    continue;
                }
                String action = splittedCommands[0];
                int id = Integer.parseInt(splittedCommands[1]) - 1;
                if (id >= count || id < 0) {
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     invalid task number, why are you not checking...");
                    System.out.println("    ____________________________________________________________");
                    continue;
                }
                String result = HeyJudy.markTask(action, tasks[id]);
                System.out.println("    ____________________________________________________________");
                System.out.println("     " + result);
                System.out.println("     " + tasks[id]);
                System.out.println("    ____________________________________________________________");
            }else {
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
