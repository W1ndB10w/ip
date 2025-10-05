package reverie.task;

import reverie.parser.DateTimeParser;
import java.time.LocalDate;
import java.time.LocalTime;
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

    // Constructor without time
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;

        DateTimeParser.ParseResult fromResult = DateTimeParser.parseDateTime(from);
        DateTimeParser.ParseResult toResult = DateTimeParser.parseDateTime(to);

        LocalDateTime fromDateTime = fromResult.getDateTime();
        LocalDateTime toDateTime = toResult.getDateTime();

        // Handle smart date inference
        fromDateTime = inferFromDateTime(fromDateTime, toDateTime, fromResult, toResult);
        toDateTime = inferToDateTime(fromDateTime, toDateTime, fromResult, toResult);

        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.hasTime = fromResult.hasTime() || toResult.hasTime();
    }

    // Constructor with time
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

    public boolean hasTime() {
        return hasTime;
    }

    public String getFromString() {
        return from;
    }

    public String getToString() {
        return to;
    }

    public LocalDateTime getFromDateTime() {
        return fromDateTime;
    }

    public LocalDateTime getToDateTime() {
        return toDateTime;
    }

    // Infer the 'from' date when only time is provided
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

    // Infer the 'to' date when only time is provided
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
