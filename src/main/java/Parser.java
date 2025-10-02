public class Parser {
    public static Command parse(String fullCommand) throws ReverieException {
        String trimmedCommand = fullCommand.trim();

        if (trimmedCommand.isEmpty()) {
            throw new ReverieException("Please enter a command!");
        }

        String[] parts = trimmedCommand.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (commandWord) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(arguments, true);
            case "unmark":
                return new MarkCommand(arguments, false);
            case "todo":
                return new AddCommand(parseTodo(trimmedCommand));
            case "deadline":
                return new AddCommand(parseDeadline(trimmedCommand));
            case "event":
                return new AddCommand(parseEvent(trimmedCommand));
            case "delete":
                return new DeleteCommand(arguments);
            default:
                throw new ReverieException("I'm sorry, but I don't know what that means :-(");
        }
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
            throw new ReverieException("The description of a deadline cannot be empty!\nFormat: deadline <description> /by <time>");
        }

        String content = input.replaceFirst("(?i)^deadline\\s+", "").trim();
        String[] parts = content.split("\\s+/by\\s+", 2);

        if (parts.length < 2) {
            throw new ReverieException("Invalid deadline format!\nFormat: deadline <description> /by <time>");
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
        
    }
}
