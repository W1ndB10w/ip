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
    private LocalDateTime inferFromDateTime() {
        
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
