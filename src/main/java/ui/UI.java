package ui;

import exception.UserInputException;

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
    public static void greetUser() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        printLines();
        System.out.println("     Hello! I'm your angsty chatbot, HeyJudy.");
        System.out.println("     What can I do for you?");
        printLines();
    }
}
