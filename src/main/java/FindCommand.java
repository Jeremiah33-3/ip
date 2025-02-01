import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class FindCommand extends Command {
    private String dateStr;

    public FindCommand(String dateStr) {
        this.dateStr = dateStr;
    }

    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        try {
            LocalDate searchDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks.getTasks()) {
                if (task instanceof Deadline) {
                    if (((Deadline) task).getBy().toLocalDate().equals(searchDate)) {
                        matchingTasks.add(task);
                    }
                } else if (task instanceof Event) {
                    if (((Event) task).getFrom().equals(searchDate) ||
                            ((Event) task).getTo().equals(searchDate)) {
                        matchingTasks.add(task);
                    }
                }
            }

            if (matchingTasks.isEmpty()) {
                throw new UserInputException("     No deadlines/events found on " + searchDate + ".");
            } else {
                UI.printLines();
                System.out.println("     Tasks on " + searchDate + ":");
                for (Task task : matchingTasks) {
                    System.out.println("     " + task);
                }
                UI.printLines();
            }

        } catch (DateTimeParseException e) {
            String message = "     Invalid date format! You should use yyyy-MM-dd (e.g., 2019-12-02).";
            throw new UserInputException(message);
        }
    }
}
