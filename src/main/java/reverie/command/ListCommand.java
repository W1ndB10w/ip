package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents a command to list all tasks.
 * A <code>ListCommand</code> displays all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command to display all tasks.
     *
     * @param tasks The task list to display.
     * @param ui The UI to display the task list.
     * @param storage The storage (not used in this command).
     * @throws ReverieException If there is an error accessing the tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        ui.showTaskList(tasks);
    }
}
