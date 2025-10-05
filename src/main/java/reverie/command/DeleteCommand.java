package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.task.Task;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 * A <code>DeleteCommand</code> removes a task at the specified index
 * and saves the updated task list to storage.
 */
public class DeleteCommand extends Command {
    private final String arguments;

    /**
     * Constructs a DeleteCommand with the specified arguments.
     *
     * @param arguments The task number to delete.
     */
    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the delete command to remove a task from the list.
     * The task list is saved to storage after deletion.
     *
     * @param tasks The task list to delete from.
     * @param ui The UI to display deletion confirmation.
     * @param storage The storage to save the updated task list.
     * @throws ReverieException If the task list is empty, task number is invalid, or storage fails.
     */
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
