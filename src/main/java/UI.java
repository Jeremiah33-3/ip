public class UI {
    public static void formattedReply(String reply) {
        System.out.println("    ____________________________________________________________");
        System.out.println("         " + reply);
        System.out.println("    ____________________________________________________________");
    }

    public static void printLines() {
        System.out.println("    ____________________________________________________________");
    }

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
