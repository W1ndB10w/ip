public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        ui.showTaskList(tasks);
    }
}
