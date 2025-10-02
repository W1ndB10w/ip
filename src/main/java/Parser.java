public class Parser {
    public static Command parse(String fullCommand) throws ReverieException {
        String trimmedCommand = fullCommand.trim();

        if (trimmedCommand.isEmpty()) {
            throw new ReverieException("Please enter a command!");
        }

        String[] parts = trimmedCommand.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";
    }
}
