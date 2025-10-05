package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.task.Task;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents a command to add a task to the task list.
 * An <code>AddCommand</code> adds a new task and saves it to storage.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Constructs an AddCommand with the specified task.
     *
     * @param task The task to add to the task list.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command to add the task to the list and save it to storage.
     *
     * @param tasks The task list to add to.
     * @param ui The UI to display confirmation.
     * @param storage The storage to save the updated task list.
     * @throws ReverieException If there is an error saving to storage.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}
