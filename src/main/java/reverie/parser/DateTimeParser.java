package reverie.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a flexible date-time parser for the Reverie chatbot.
 * A <code>DateTimeParser</code> class provides methods to parse various
 * date and time formats into LocalDateTime objects.
 */
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
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm yyyy-MM-dd"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm yyyy/MM/dd"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm yyyy/MM/dd"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm dd-MM-yyyy"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm dd/MM/yyyy"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm yyyy MMM dd").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm yyyy MMM dd").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm MMM dd yyyy").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm MMM dd yyyy").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HHmm dd MMM yyyy").withLocale(Locale.ENGLISH));
        DATE_TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("HH:mm dd MMM yyyy").withLocale(Locale.ENGLISH));

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
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hhmm a").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hhmma").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hmm a").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hmma").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hh:mm a").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("hh:mma").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("h:mm a").withLocale(java.util.Locale.ENGLISH));
        TIME_FORMATTERS.add(DateTimeFormatter.ofPattern("h:mma").withLocale(java.util.Locale.ENGLISH));
    }

    /**
     * Represents the result of parsing a date-time string.
     * Contains both the parsed LocalDateTime and whether time was present in the input.
     */
    public static class ParseResult {
        private final LocalDateTime dateTime;
        private final boolean hasTime;

        /**
         * Constructs a ParseResult with the specified date-time and time flag.
         *
         * @param dateTime The parsed LocalDateTime.
         * @param hasTime Whether the input included time information.
         */
        public ParseResult(LocalDateTime dateTime, boolean hasTime) {
            this.dateTime = dateTime;
            this.hasTime = hasTime;
        }

        /**
         * Returns the parsed date-time.
         *
         * @return The LocalDateTime object, or null if parsing failed.
         */
        public LocalDateTime getDateTime() {
            return dateTime;
        }

        /**
         * Checks if the parsed input included time information.
         *
         * @return True if time was included, false otherwise.
         */
        public boolean hasTime() {
            return hasTime;
        }
    }

    /**
     * Parses a date-time string flexibly using various date and time formats.
     * Attempts to parse as full date-time first, then date-only, then time-only.
     * Returns null for the DateTime if the input cannot be parsed as a valid date/time.
     *
     * @param input The date-time string to parse.
     * @return A ParseResult containing the parsed LocalDateTime and whether time was included.
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
