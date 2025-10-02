import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDate fromDate;
    protected LocalDate toDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDate = parseDate(from);
        this.toDate = parseDate(to);
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String getFullStatus() {
        String fromString = fromDate != null ? fromDate.format(OUTPUT_FORMAT) : from;
        String toString = toDate != null ? toDate.format(OUTPUT_FORMAT) : to;
        return "[E]" + super.getFullStatus() + " (from: " + fromString + " to: " + toString + ")";
    }
}
