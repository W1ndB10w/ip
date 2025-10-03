package reverie.task;

import reverie.exception.ReverieException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    protected String by;
    protected LocalDateTime byDateTime;
    protected boolean hasTime;
    private static final DateTimeFormatter OUTPUT_FORMAT_WITH_TIME =
            DateTimeFormatter.ofPattern("HH:mm MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);
    public Deadline(String description, String by) throws ReverieException {
        super(description);
        this.by = by;
        this.byDate = parseDate(by);
    }

    public String getByString() {
        return by;
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
