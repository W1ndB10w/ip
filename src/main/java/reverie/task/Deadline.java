package reverie.task;

import reverie.exception.ReverieException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Deadline extends Task {
    protected String by;
    protected LocalDate byDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    public Deadline(String description, String by) throws ReverieException {
        super(description);
        this.by = by;
        this.byDate = parseDate(by);
    }

    private LocalDate parseDate(String dateString) throws ReverieException {
        try {
            return LocalDate.parse(dateString, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ReverieException("Invalid date format. Please use yyyy-MM-dd (e.g., 2019-12-02)");
        }
    }

    @Override
    public String getFullStatus() {
        String dateString = byDate != null ? byDate.format(OUTPUT_FORMAT) : by;
        return "[D]" + super.getFullStatus() + " (by: " + dateString + ")";
    }

    public LocalDate getByDate() {
        return byDate;
    }
}
