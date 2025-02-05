package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import tasklist.TaskList;
import ui.UI;

/**
 * Represents a command to find a task based on dates/keywords.
 */
public class FindCommand extends Command {
    private static final List<DateTimeFormatter> DATE_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    );

    private String query;

    /**
     * Constructs a FindCommand based on the query the user inputted.
     *
     * @param query The dateStr/keyword the user inputted.
     */
    public FindCommand(String query) {
        this.query = query;
    }

    @Override
    public void execute(TaskList tasks, Storage fm) throws UserInputException {
        if (isDateFormat(query)) {
            searchByDate(tasks);
        } else {
            searchByKeyword(tasks);
        }
    }

    private boolean isDateFormat(String input) {
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                LocalDate.parse(input, formatter);
                return true;
            } catch (DateTimeParseException ignored) {
                return false;
            }
        }
        return false;
    }

    private void searchByDate(TaskList tasks) throws UserInputException {
        try {
            LocalDate searchDate = LocalDate.parse(query, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks.getTasks()) {
                if (task instanceof Deadline) {
                    if (((Deadline) task).getBy().toLocalDate().equals(searchDate)) {
                        matchingTasks.add(task);
                    }
                } else if (task instanceof Event) {
                    if (((Event) task).getFrom().equals(searchDate)
                            || ((Event) task).getTo().equals(searchDate)) {
                        matchingTasks.add(task);
                    }
                }
            }

            printMatchingTasks(matchingTasks, "Tasks on " + searchDate + ":");

        } catch (DateTimeParseException e) {
            throw new UserInputException("     Excuse me, pls use yyyy-mm-dd (e.g., 2019-12-02).\n");
        }
    }

    private void searchByKeyword(TaskList tasks) throws UserInputException {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (Task task : tasks.getTasks()) {
            if (task.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                matchingTasks.add(task);
            }
        }

        printMatchingTasks(matchingTasks, "Here are the matching tasks in your list:");
    }

    private void printMatchingTasks(ArrayList<Task> tasks, String header) throws UserInputException {
        if (tasks.isEmpty()) {
            throw new UserInputException("     No matching tasks found!");
        } else {
            UI.printLines();
            System.out.println("     " + header);
            int index = 1;
            for (Task task : tasks) {
                System.out.println("     " + index + ". " + task);
                index++;
            }
            UI.printLines();
        }
    }

    public String getQuery() {
        return this.query;
    }
}
