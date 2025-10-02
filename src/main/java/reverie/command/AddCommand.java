package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.task.Task;
import reverie.ui.TaskList;
import reverie.ui.Ui;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
        storage.save(tasks.getAllTasks());
    }
}
