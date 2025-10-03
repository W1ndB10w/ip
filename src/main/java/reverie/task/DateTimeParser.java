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

    /**
     * Result of parsing that includes both the LocalDateTime and whether time was present
     */
    public static class ParseResult {
        private final LocalDateTime dateTime;
        private final boolean hasTime;

        public ParseResult(LocalDateTime dateTime, boolean hasTime) {
            this.dateTime = dateTime;
            this.hasTime = hasTime;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public boolean hasTime() {
            return hasTime;
        }
    }

    public static ParseResult parseDateTime(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ParseResult(null, false);
        }

        String trimmed = input.trim();

        // Try full date-time formats first
        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                return new ParseResult(LocalDateTime.parse(trimmed, formatter), true);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try date-only formats (set time to 00:00)
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(trimmed, formatter);
                return new ParseResult(date.atStartOfDay(), false);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try time-only formats (set date to today)
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                LocalTime time = LocalTime.parse(trimmed, formatter);
                return new ParseResult(LocalDateTime.of(LocalDate.now(), time), true);
            } catch (DateTimeParseException ignored) {
            }
        }

        // If all parsing fails, return null (treat as plain text)
        return new ParseResult(null, false);
    }
}
