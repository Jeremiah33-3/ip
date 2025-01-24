import java.util.ArrayList;
import java.util.Scanner;

public class HeyJudy {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static int count = 0;
    public static void greetUser() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm your angsty chatbot, HeyJudy.");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    public static void addToDo(String item) {
        ToDo toDo = new ToDo(item);
        tasks.add(toDo);
        count++;
        printTask(toDo);
    }

    public static void addDeadline(String details) {
        String[] tokens = details.split(" /by ", 2);
        if (tokens.length < 2) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Why are you not giving me your details? "
                    + "How can I set deadline for you?? Format:\n"
                    + "       deadline <description> /by <deadline>");
            System.out.println("    ____________________________________________________________");
        } else {
            Deadline deadline = new Deadline(tokens[0], tokens[1]);
            tasks.add(deadline);
            count++;
            printTask(deadline);
        }
    }

    public static void addEvent(String details) {
        String[] tokens = details.split(" /from | /to ");
        if (tokens.length < 3) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     You not giving me enough inputs, how i do for you?? Format:\n "
                    + "      event <description> /from <start date> /to <end date>");
            System.out.println("    ____________________________________________________________");
        } else {
            Event event = new Event(tokens[0], tokens[1], tokens[2]);
            tasks.add(event);
            count++;
            printTask(event);
        }
    }

    public static void deleteTask(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 2) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Which tasks do you want me to delete for you exactly??? "
                    + "Format:\n"
                    + "        delete <id of task>");
            System.out.println("    ____________________________________________________________");
            return;
        } else if (parts.length > 2) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Don't be ambitious. Please delete one task at a time. "
                    + "Format:\n"
                    + "        delete <id of task>");
            System.out.println("    ____________________________________________________________");
            return;
        }
        int taskIndex = Integer.parseInt(parts[1]) - 1;

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Please get yourself together... this task does not exist. "
                    + "Check by listing: list\n"
                    + "      Format:\n"
                    + "        delete <id of task>");
            System.out.println("    ____________________________________________________________");
            return;
        }

        Task removedTask = tasks.remove(taskIndex);
        count--;
        System.out.println("    ____________________________________________________________");
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + removedTask);
        System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
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
        System.out.println("         Bye. I hope you are more organised now.");
        System.out.println("    ____________________________________________________________");
    }
    public static void listTask() {
        if (count == 0) {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Psss, I don't see any task yet. Please add. Directory:\n"
                    + "      1. todo <description>\n"
                    + "      2. deadline <description> /by <deadline>\n"
                    + "      3. event <description> /from <start date> /to <end date>\n");
            System.out.println("    ____________________________________________________________");
        } else {
            System.out.println("    ____________________________________________________________");
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < count; i++) {
                System.out.println("     " + (i + 1) + ". " + tasks.get(i));
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
                    System.out.println("     what task, bro? no task given to me! "
                            + "please format as: \n mark/unmark <task number>");
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
                String result = HeyJudy.markTask(action, tasks.get(id));
                System.out.println("    ____________________________________________________________");
                System.out.println("     " + result);
                System.out.println("     " + tasks.get(id));
                System.out.println("    ____________________________________________________________");
            } else if (userCommand.startsWith("todo ")) {
                addToDo(userCommand.substring(5));
            } else if (userCommand.startsWith("deadline")) {
                addDeadline(userCommand.substring(5));
            } else if (userCommand.startsWith("event")) {
                addEvent(userCommand.substring(5));
            } else if (userCommand.startsWith("delete")) {
                HeyJudy.deleteTask(userCommand);
            }else {
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
