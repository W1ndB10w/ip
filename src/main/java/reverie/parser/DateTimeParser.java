package reverie.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
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
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy MMM dd HHmm").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd MMM yyyy HHmm").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm").withLocale(Locale.ENGLISH));

        // Date-only formats
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("MMM dd yyyy").withLocale(Locale.ENGLISH));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("dd MMM yyyy").withLocale(Locale.ENGLISH));
        DATE_FORMATTERS.add(DateTimeFormatter.ofPattern("yyyy MMM dd").withLocale(Locale.ENGLISH));

        // Time-only formats
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm"));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hh:mm a").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hh:mma").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("h:mm a").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("h:mma").withLocale(java.util.Locale.ENGLISH));
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

    /**
     * Parses a date-time string flexibly.
     * Returns LocalDateTime if parsing succeeds, null if input is plain text.
     * Returns a ParseResult indicating whether time was included in the input.
     */
    public static ParseResult parseDateTime(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ParseResult(null, false);
        }

        String trimmed = input.trim();

        // Normalize AM/PM to uppercase for easier parsing
        String normalized = trimmed.replaceAll("(?i)am", "AM").replaceAll("(?i)pm", "PM");

        // Try full date-time formats first
        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                return new ParseResult(LocalDateTime.parse(normalized, formatter), true);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try date-only formats (set time to 00:00)
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(normalized, formatter);
                return new ParseResult(date.atStartOfDay(), false);
            } catch (DateTimeParseException ignored) {
            }
        }

        // Try time-only formats (set date to today)
        for (DateTimeFormatter formatter : TIME_FORMATTERS) {
            try {
                LocalTime time = LocalTime.parse(normalized, formatter);
                return new ParseResult(LocalDateTime.of(LocalDate.now(), time), true);
            } catch (DateTimeParseException ignored) {
            }
        }

        // If all parsing fails, return null (treat as plain text)
        return new ParseResult(null, false);
    }
}
