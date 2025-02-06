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
                I'm your angsty chatbot, Judy. What can I do for you?"
                """;
    }
}
