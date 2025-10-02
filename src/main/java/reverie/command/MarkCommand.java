package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.task.Task;
import reverie.ui.TaskList;
import reverie.ui.Ui;

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

        try {
            int taskNumber = Integer.parseInt(arguments.trim()) - 1;
            Task task = tasks.get(taskNumber);

            if (isMark) {
                task.markAsDone();
                ui.showTaskMarked(task);
            } else {
                task.markAsUndone();
                ui.showTaskUnmarked(task);
            }

            storage.save(tasks.getAllTasks());
        } catch (NumberFormatException e) {
            throw new ReverieException("Please enter a valid number after '" + (isMark ? "mark" : "unmark") + "'");
        }
    }
}
