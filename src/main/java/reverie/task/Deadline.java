package reverie.task;

import reverie.parser.DateTimeParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a deadline task with a due date/time.
 * A <code>Deadline</code> object has a description and a deadline (by) date/time.
 */
public class Deadline extends Task {
    protected String by;
    protected LocalDateTime byDateTime;
    protected boolean hasTime;
    private static final DateTimeFormatter OUTPUT_FORMAT_WITH_TIME =
            DateTimeFormatter.ofPattern("HH:mm MMM dd yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMAT_DATE_ONLY =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /**
     * Constructs a Deadline task with the specified description and due date/time.
     * Automatically parses the date/time string.
     *
     * @param description The description of the deadline task.
     * @param by The due date/time string.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        DateTimeParser.ParseResult result = DateTimeParser.parseDateTime(by);
        this.byDateTime = result.getDateTime();
        this.hasTime = result.hasTime();
    }

    /**
     * Constructs a Deadline task with the specified description, due date/time, and time flag.
     * This constructor is typically used when loading from storage.
     *
     * @param description The description of the deadline task.
     * @param by The due date/time string.
     * @param hasTime Whether the deadline includes a specific time.
     */
    public Deadline(String description, String by, boolean hasTime) {
        super(description);
        this.by = by;
        DateTimeParser.ParseResult result = DateTimeParser.parseDateTime(by);
        this.byDateTime = result.getDateTime();
        this.hasTime = hasTime;
    }

    /**
     * Checks if the deadline has specific time information.
     *
     * @return True if the deadline has time information, false otherwise.
     */
    public boolean hasTime() {
        return hasTime;
    }

    /**
     * Returns the original "by" string as provided by the user.
     *
     * @return The by string.
     */
    public String getByString() {
        return by;
    }

    /**
     * Returns the full status string representation of the deadline task.
     * The format is: [D][status icon] description (by: due date/time)
     *
     * @return The formatted status string.
     */
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

    /**
     * Returns the parsed due date-time of the deadline.
     *
     * @return The by LocalDateTime.
     */
    public LocalDateTime getByDateTime() {
        return byDateTime;
    }
}
