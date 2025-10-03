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

    // Constructor without time
    public Deadline(String description, String by) throws ReverieException {
        super(description);
        this.by = by;
        DateTimeParser.ParseResult result = DateTimeParser.parseDateTime(by);
        this.byDateTime = result.getDateTime();
        this.hasTime = result.hasTime();
    }

    // Constructor with time
    public Deadline(String description, String by, boolean hasTime) throws ReverieException {
        super(description);
        this.by = by;
        DateTimeParser.ParseResult result = DateTimeParser.parseDateTime(by);
        this.byDateTime = result.getDateTime();
        this.hasTime = hasTime;
    }

    public boolean hasTime() {
        return hasTime;
    }

    public String getByString() {
        return by;
    }

    @Override
    public String getFullStatus() {
        String dateString;
        if (byDateTime != null) {
            dateString = hasTime ? byDateTime.format(OUTPUT_FORMAT_WITH_TIME) : byDateTime.format(OUTPUT_FORMAT_DATE_ONLY);
        } else {
            dateString = by;
        }
        return "[D]" + super.getFullStatus() + " (by: " + dateString + ")";
    }

    public LocalDateTime getByDateTime() {
        return byDateTime;
    }
}
