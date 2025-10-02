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
}
