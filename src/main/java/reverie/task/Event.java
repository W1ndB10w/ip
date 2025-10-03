package reverie.task;

import reverie.exception.ReverieException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;
    protected boolean hasTime;
    private static final DateTimeFormatter OUTPUT_FORMAT_WITH_TIME =
            DateTimeFormatter.ofPattern("HH:mm MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    public Event(String description, String from, String to) throws ReverieException {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDate = parseDate(from);
        this.toDate = parseDate(to);
    }

    public String getFromString() {
        return from;
    }

    public String getToString() {
        return to;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
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
        String fromString = fromDate != null ? fromDate.format(OUTPUT_FORMAT) : from;
        String toString = toDate != null ? toDate.format(OUTPUT_FORMAT) : to;
        return "[E]" + super.getFullStatus() + " (from: " + fromString + " to: " + toString + ")";
    }
}
