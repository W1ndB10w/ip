public class DeleteCommand extends Command {
    private final String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        if (tasks.isEmpty()) {
            throw new ReverieException("No tasks available to delete! Add some tasks first.");
        }

        if (arguments.trim().isEmpty()) {
            throw new ReverieException("Please specify a task number to delete");
        }

        try {
            int taskNumber = Integer.parseInt(arguments.trim()) - 1;
            Task removedTask = tasks.delete(taskNumber);
            ui.showTaskDeleted(removedTask, tasks.size());
            storage.save(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new ReverieException("Please enter a valid number after 'delete'");
        }
    }
}
