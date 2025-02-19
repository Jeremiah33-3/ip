package command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import exception.UserInputException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.RecurringTask;
import task.Task;
import tasklist.TaskList;

/**
 * Represents a command to find a task based on dates/keywords.
 */
public class FindCommand extends Command {
    private static final List<DateTimeFormatter> DATE_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("dd-MM-yyyy").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("MM-dd-yyyy").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("yyyy/MM/dd").withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.STRICT)
    );

    private String query;

    /**
     * Constructs a FindCommand based on the query the user inputted.
     *
     * @param query The dateStr/keyword the user inputted.
     */
    public FindCommand(String query) {
        this.query = query;
        assert this.query != null: "query stored in FindCommand should not be null";
    }

    @Override
    public String execute(TaskList tasks, Storage fm) throws UserInputException {
        assert tasks != null: "TaskList provided should not be null in FindCommand execute";
        assert fm != null: "Storage provided should not be null in FindCommand execute";
        if (isDateFormat(query)) {
            return searchByDate(tasks);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            String[] keywords = lowerCaseQuery.split(" ");
            return searchByKeyword(tasks, keywords);
        }
    }

    private boolean isDateFormat(String input) {
        try {
            for (DateTimeFormatter formatter : DATE_FORMATS) {
                LocalDate date = LocalDate.parse(input, formatter);
                if (date != null) {
                    return true;
                }
            }
        } catch (DateTimeParseException ignored) {
            return false;
        }

        return false;
    }

    private String searchByDate(TaskList tasks) throws UserInputException {
        try {
            LocalDate searchDate = LocalDate.parse(query, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            assert  searchDate != null: "searchDate should not be null "
                    + "after parsing in FindCommand searchByDate";
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks.getTasks()) {
                if (task instanceof Deadline) {
                    if (((Deadline) task).getBy().toLocalDate().equals(searchDate)) {
                        matchingTasks.add(task);
                    }
                } else if (task instanceof Event) {
                    if (hasSearchDate(searchDate, ((Event) task).getFrom(), ((Event) task).getTo())) {
                        matchingTasks.add(task);
                    }
                } else if (task instanceof RecurringTask) {
                    if (((RecurringTask) task).getOccurrenceDate().toLocalDate().equals(searchDate)){
                        matchingTasks.add(task);
                    }
                }
            }

            return getMatchingTasksString(matchingTasks, "Tasks on " + searchDate + ":");

        } catch (DateTimeParseException e) {
            throw new UserInputException("Excuse me, pls use yyyy-mm-dd (e.g., 2019-12-02).\n");
        }
    }

    private boolean hasSearchDate(LocalDate searchDate, LocalDate start, LocalDate end)
            throws UserInputException {
        try {
            LocalDate currDate = start;
            while (!currDate.equals(end.plusDays(1))) {
                if (currDate.equals(searchDate)) {
                    return true;
                } else {
                    currDate = currDate.plusDays(1);
                }
            }
            return false;
        } catch (NullPointerException e) {
            throw new UserInputException("search date is null???");
        }
    }

    private String searchByKeyword(TaskList tasks, String ... keywords) throws UserInputException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks.getTasks()) {
            String taskDescription = task.getDescription().toLowerCase();
            assert  taskDescription != null: "task description returned by task in " +
                    "FindCommand searchByKeyword should not be null";

            for (String keyword : keywords) {
                if (taskDescription.contains(keyword.toLowerCase())) {
                    matchingTasks.add(task);
                    break;
                }
            }
        }

        return getMatchingTasksString(matchingTasks, "Here are the matching tasks in your list:");
    }

    private String getMatchingTasksString(ArrayList<Task> tasks, String header) throws UserInputException {
        if (tasks.isEmpty()) {
            throw new UserInputException("     No matching tasks found!");
        } else {
            StringBuilder result = new StringBuilder();
            result.append(header).append("\n");
            int index = 1;
            for (Task task : tasks) {
                result.append(index).append(". ").append(task).append("\n");
                index++;
            }
            return result.toString();
        }
    }


    public String getQuery() {
        return this.query;
    }
}
