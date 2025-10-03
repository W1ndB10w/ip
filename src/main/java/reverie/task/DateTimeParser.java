package reverie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateTimeParser {
    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = new ArrayList<>();
    private static final List<DateTimeFormatter> DATE_FORMATTERS = new ArrayList<>();
    private static final List<DateTimeFormatter> TIME_FORMATTERS = new ArrayList<>();

    static {
        // Date-time formats (with both date and time)
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));

        // Date-only formats
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy MMM dd"));

        // Time-only formats
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hh:mm a"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hh:mma"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("h:mm a"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("h:mma"));
    }

    public static class ParseResult {
        private final LocalDateTime dateTime;
        private final boolean hasTime;

        public ParseResult(LocalDateTime dateTime, boolean hasTime) {
            this.dateTime = dateTime;
            this.hasTime = hasTime;
        }
    }
}
