package reverie.task;

import reverie.parser.DateTimeParser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents an event task with a start and end time.
 * An <code>Event</code> object has a description, start time (from), and end time (to).
 * It supports smart date inference when only times are provided.
 */
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

    /**
     * Constructs an Event task with the specified description and time range.
     * Automatically parses and infers dates when only times are provided.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;

        DateTimeParser.ParseResult fromResult = DateTimeParser.parseDateTime(from);
        DateTimeParser.ParseResult toResult = DateTimeParser.parseDateTime(to);

        LocalDateTime fromDateTime = fromResult.getDateTime();
        LocalDateTime toDateTime = toResult.getDateTime();

        // Handle intelligent date inference
        fromDateTime = inferFromDateTime(fromDateTime, toDateTime, fromResult, toResult);
        toDateTime = inferToDateTime(fromDateTime, toDateTime, fromResult, toResult);

        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.hasTime = fromResult.hasTime() || toResult.hasTime();
    }

    /**
     * Constructs an Event task with the specified description, time range, and time flag.
     * This constructor is typically used when loading from storage.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     * @param hasTime Whether the event includes specific times.
     */
    public Event(String description, String from, String to, boolean hasTime) {
        super(description);
        this.from = from;
        this.to = to;

        DateTimeParser.ParseResult fromResult = DateTimeParser.parseDateTime(from);
        DateTimeParser.ParseResult toResult = DateTimeParser.parseDateTime(to);

        this.fromDateTime = fromResult.getDateTime();
        this.toDateTime = toResult.getDateTime();
        this.hasTime = hasTime;
    }

    /**
     * Checks if the event has specific time information.
     *
     * @return True if the event has time information, false otherwise.
     */
    public boolean hasTime() {
        return hasTime;
    }

    /**
     * Returns the original "from" string as provided by the user.
     *
     * @return The from string.
     */
    public String getFromString() {
        return from;
    }

    /**
     * Returns the original "to" string as provided by the user.
     *
     * @return The to string.
     */
    public String getToString() {
        return to;
    }

    /**
     * Returns the parsed start date-time of the event.
     *
     * @return The from LocalDateTime.
     */
    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    /**
     * Returns the parsed end date-time of the event.
     *
     * @return The to LocalDateTime.
     */
    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    /**
     * Infers the 'from' date when only time is provided.
     * Uses smart inference based on the 'to' date and time comparison.
     *
     * @param fromDateTime The parsed from date-time.
     * @param toDateTime The parsed to date-time.
     * @param fromResult The parse result for from.
     * @param toResult The parse result for to.
     * @return The inferred from LocalDateTime.
     */
    private LocalDateTime inferFromDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime,
                                            DateTimeParser.ParseResult fromResult,
                                            DateTimeParser.ParseResult toResult) {
        // If 'from' has only time and 'to' has date+time
        if (fromDateTime != null && toDateTime != null &&
                fromResult.hasTime() && toResult.hasTime() &&
                fromDateTime.toLocalDate().equals(LocalDate.now()) &&
                !toDateTime.toLocalDate().equals(LocalDate.now())) {

            LocalDate toDate = toDateTime.toLocalDate();
            LocalTime fromTime = fromDateTime.toLocalTime();
            LocalTime toTime = toDateTime.toLocalTime();

            // If from_time is between 0000-toTime, use same day as 'to'
            if (fromTime.isBefore(toTime) || fromTime.equals(toTime)) {
                return LocalDateTime.of(toDate, fromTime);
            } else {
                // If from_time is after toTime, use previous day
                return LocalDateTime.of(toDate.minusDays(1), fromTime);
            }
        }

        return fromDateTime;
    }

    /**
     * Infers the 'to' date when only time is provided.
     * Uses smart inference based on the 'from' date and time comparison.
     *
     * @param fromDateTime The parsed from date-time.
     * @param toDateTime The parsed to date-time.
     * @param fromResult The parse result for from.
     * @param toResult The parse result for to.
     * @return The inferred to LocalDateTime.
     */
    private LocalDateTime inferToDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime,
                                          DateTimeParser.ParseResult fromResult,
                                          DateTimeParser.ParseResult toResult) {
        // If 'to' has only time and 'from' has date+time
        if (fromDateTime != null && toDateTime != null &&
                fromResult.hasTime() && toResult.hasTime() &&
                !fromDateTime.toLocalDate().equals(LocalDate.now()) &&
                toDateTime.toLocalDate().equals(LocalDate.now())) {

            LocalDate fromDate = fromDateTime.toLocalDate();
            LocalTime fromTime = fromDateTime.toLocalTime();
            LocalTime toTime = toDateTime.toLocalTime();

            // If to_time is between fromTime-2400, use same day as 'from'
            if (toTime.isAfter(fromTime) || toTime.equals(fromTime)) {
                return LocalDateTime.of(fromDate, toTime);
            } else {
                // If to_time is before fromTime, use next day
                return LocalDateTime.of(fromDate.plusDays(1), toTime);
            }
        }

        return toDateTime;
    }

    /**
     * Returns the full status string representation of the event task.
     * The format is: [E][status icon] description (from: start to: end)
     *
     * @return The formatted status string.
     */
    @Override
    public String getFullStatus() {
        String fromString;
        String toString;

        if (fromDateTime != null) {
            fromString = hasTime ? fromDateTime.format(OUTPUT_FORMAT_WITH_TIME) : fromDateTime.format(OUTPUT_FORMAT_DATE_ONLY);
        } else {
            fromString = from;
        }

        if (toDateTime != null) {
            toString = hasTime ? toDateTime.format(OUTPUT_FORMAT_WITH_TIME) : toDateTime.format(OUTPUT_FORMAT_DATE_ONLY);
        } else {
            toString = to;
        }

        return "[E]" + super.getFullStatus() + " (from: " + fromString + " to: " + toString + ")";
    }
}
