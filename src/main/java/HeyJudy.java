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
    public static void addToDo(String item) {
        ToDo toDo = new ToDo(item, count);
        tasks[count] = toDo;
        count++;
        printTask(toDo);
    }

    public static void addDeadline(String details) {
        String[] tokens = details.split(" /by ", 2);
        if (tokens.length < 2) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Why are you not giving me your details? "
                    + "How can I set deadline for you??");
            System.out.println("    ____________________________________________________________");
        } else {
            Deadline deadline = new Deadline(tokens[0], count, tokens[1]);
            tasks[count] = deadline;
            count++;
            printTask(deadline);
        }
    }

    public static void addEvent(String details) {
        String[] tokens = details.split(" /from | /to ");
        if (tokens.length < 3) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     You not giving me enough inputs, how i do for you??");
            System.out.println("    ____________________________________________________________");
        } else {
            Event event = new Event(tokens[0], count, tokens[1], tokens[2]);
            tasks[count] = event;
            count++;
            printTask(event);
        }
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
    public static void listTask() {
        if (count == 0) {
            System.out.println("    ____________________________________________________________");
            System.out.println("        Psss, I don't see any task yet. Please add");
            System.out.println("    ____________________________________________________________");
        } else {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < count; i++) {
                System.out.println("     " + (i + 1) + ". " + tasks[i]);
            }
            System.out.println("    ____________________________________________________________");
        }
    }

    public static void printTask(Task task) {
        System.out.println("    ____________________________________________________________");
        System.out.println("      Got it. I've added this task: ");
        System.out.println("      " + task);
        System.out.println("      Now you have " + (count) + " tasks in the list.");
        System.out.println("    ____________________________________________________________");
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
                HeyJudy.listTask();
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
            } else if (userCommand.startsWith("todo ")) {
                addToDo(userCommand.substring(5));
            } else if (userCommand.startsWith("deadline ")) {
                addDeadline(userCommand.substring(5));
            } else if (userCommand.startsWith("event ")) {
                addEvent(userCommand.substring(5));
            } else {
                System.out.println("    ____________________________________________________________");
                System.out.println("     idk what you are doing "
                        + ":< please input deadline/todo/event/mark before your inputs");
                System.out.println("    ____________________________________________________________");
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
