public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        if (keyword.trim().isEmpty()) {
            throw new ReverieException("Please specify a keyword to search for");
        }

        TaskList matchingTasks = tasks.findTasks(keyword);
        ui.showFoundTasks(matchingTasks);
    }
}
