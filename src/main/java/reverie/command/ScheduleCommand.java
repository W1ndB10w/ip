import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ScheduleCommand extends Command {
    private final String dateString;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ScheduleCommand(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        if (dateString.trim().isEmpty()) {
            throw new ReverieException("Please specify a date to check the schedule\n" +
                    "Format: schedule yyyy-MM-dd (e.g., schedule 2019-12-02)");
        }

        LocalDate targetDate;
        try {
            targetDate = LocalDate.parse(dateString.trim(), INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ReverieException("Invalid date format! Please use yyyy-MM-dd (e.g., 2019-12-02)");
        }

        ArrayList<Integer> matchingIndices = getIndicesFromDate(tasks, targetDate);

        ui.showSchedule(tasks, matchingIndices, targetDate);
    }

    private static ArrayList<Integer> getIndicesFromDate(TaskList tasks, LocalDate targetDate) {
        ArrayList<Integer> matchingIndices = new ArrayList<>();
        ArrayList<Task> allTasks = tasks.getAllTasks();

        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            if (task instanceof Deadline deadline) {
                if (deadline.getByDate() != null && deadline.getByDate().equals(targetDate)) {
                    matchingIndices.add(i);
                }
            } else if (task instanceof Event event) {
                if (event.getFromDate() != null && event.getToDate() != null) {
                    if (!targetDate.isBefore(event.getFromDate()) && !targetDate.isAfter(event.getToDate())) {
                        matchingIndices.add(i);
                    }
                }
            }
        }
        return matchingIndices;
    }
}
