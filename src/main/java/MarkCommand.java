public class MarkCommand extends Command {
    private final String arguments;
    private final boolean isMark;

    public MarkCommand(String arguments, boolean isMark) {
        this.arguments = arguments;
        this.isMark = isMark;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        if (tasks.isEmpty()) {
            throw new ReverieException("No tasks available to mark!");
        }

        if (arguments.trim().isEmpty()) {
            throw new ReverieException("Please specify a task number to " + (isMark ? "mark" : "unmark"));
        }
    }
}
