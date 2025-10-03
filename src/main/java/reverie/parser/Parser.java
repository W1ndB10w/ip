package reverie.parser;

import reverie.command.*;
import reverie.exception.ReverieException;
import reverie.task.*;

public class Parser {
    public static Command parse(String fullCommand) throws ReverieException {
        String trimmedCommand = fullCommand.trim();

        if (trimmedCommand.isEmpty()) {
            throw new ReverieException("Please enter a command!");
        }

        String[] parts = trimmedCommand.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (commandWord) {
            case "bye" -> new ExitCommand();
            case "list" -> new ListCommand();
            case "mark" -> new MarkCommand(arguments, true);
            case "unmark" -> new MarkCommand(arguments, false);
            case "todo" -> new AddCommand(parseTodo(trimmedCommand));
            case "deadline" -> new AddCommand(parseDeadline(trimmedCommand));
            case "event" -> new AddCommand(parseEvent(trimmedCommand));
            case "delete" -> new DeleteCommand(arguments);
            case "find" -> new FindCommand(arguments);
            case "schedule" -> new ScheduleCommand(arguments);
            default -> throw new ReverieException("I'm sorry, but I don't know what that means :-(");
        };
    }

    private static Task parseTodo(String input) throws ReverieException {
        if (input.length() <= "todo ".length()) {
            throw new ReverieException("The description of a todo cannot be empty!");
        }

        String description = input.replaceFirst("(?i)^todo\\s+", "").trim();
        if (description.isEmpty()) {
            throw new ReverieException("The description of a todo cannot be empty!");
        }

        return new Todo(description);
    }

    private static Task parseDeadline(String input) throws ReverieException {
        if (input.length() <= "deadline ".length()) {
            throw new ReverieException("""
                    The description of a deadline cannot be empty!
                    Format: deadline <description> /by <date-time>
                    Date-time examples: 2019-12-02 1800, 2019-12-02, 1800, Dec 02 2019, meeting tomorrow, etc.""");
        }

        String content = input.replaceFirst("(?i)^deadline\\s+", "").trim();
        String[] parts = content.split("\\s+/by\\s+", 2);

        if (parts.length < 2) {
            throw new ReverieException("""
                    Invalid deadline format!
                    Format: deadline <description> /by <date-time>
                    Date-time examples: 2019-12-02 1800, 2019-12-02, 1800, Dec 02 2019, meeting tomorrow, etc.""");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new ReverieException("The description of a deadline cannot be empty!");
        }
        if (by.isEmpty()) {
            throw new ReverieException("The deadline time cannot be empty!");
        }

        return new Deadline(description, by);
    }

    private static Task parseEvent(String input) throws ReverieException {
        String[] parts = getEventParts(input);

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();

        if (description.isEmpty()) {
            throw new ReverieException("The description of an event cannot be empty!");
        }
        if (from.isEmpty()) {
            throw new ReverieException("The start time of an event cannot be empty!");
        }
        if (to.isEmpty()) {
            throw new ReverieException("The end time of an event cannot be empty!");
        }

        return new Event(description, from, to);
    }

    private static String[] getEventParts(String input) throws ReverieException {
        if (input.length() <= "event ".length()) {
            throw new ReverieException("""
                    The description of an event cannot be empty!
                    Format: event <description> /from <start> /to <end>
                    Date-time examples: 2019-12-02 1800, 2019-12-02, 1800, Dec 02 2019, etc.""");
        }

        String content = input.replaceFirst("(?i)^event\\s+", "").trim();
        String[] parts = content.split("\\s+/from\\s+|\\s+/to\\s+", 3);

        if (parts.length < 3) {
            throw new ReverieException("""
                    Invalid event format!
                    Format: event <description> /from <start> /to <end>
                    Date format: yyyy-MM-dd (e.g., 2019-12-02)""");
        }
        return parts;
    }
}
