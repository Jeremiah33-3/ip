package ui;

/**
 * A utility class that helps with user interaction by formatting the bot's replies.
 */
public class UI {
    /**
     * Format a reply message from the bot.
     *
     * @param reply The message to send to the user.
     */
    public static void formattedReply(String reply) {
        System.out.println("    ____________________________________________________________");
        System.out.println("         " + reply);
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Prints a line for format and aesthetic.
     */
    public static void printLines() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Greets the users by introducing the bot and logo when the bot is started.
     */
    public static String greetUser() {
        return """
                HEYJUDY <33333333333333333333 \n
                I'm your angsty chatbot, Judy. What can I do for you?"\n
                List of commands to use: \n
                1. list -- list all the tasks in your list. \n
                2. todo <description> -- add a ToDo. \n
                3. deadline <description> /by <dd-mm-yyyy hh:mm> -- add a task with deadline. \n
                4. event <description> /from <dd-mm-yyyy> /to <dd-mm-yyyy> -- 
                add an event spanning a range of dates. \n
                5. delete <task number> -- delete the task with the corresponding task index on your list. \n
                6. mark <task number> -- mark a task as done. \n
                7. unmark <task number> -- unmark a task previously marked as done. \n
                9. find <date>/<keyword> -- search for a task with the given date or keyword.  \n
                9. bye -- exit the app
                """;
    }
}
