package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.task.Task;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents a command to mark or unmark a task as done.
 * A <code>MarkCommand</code> changes the completion status of a task
 * and saves the updated task list.
 */
public class MarkCommand extends Command {
    private final String arguments;
    private final boolean isMark;

    /**
     * Constructs a MarkCommand with the specified arguments and mark flag.
     *
     * @param arguments The task number to mark/unmark.
     * @param isMark True to mark as done, false to unmark.
     */
    public MarkCommand(String arguments, boolean isMark) {
        this.arguments = arguments;
        this.isMark = isMark;
    }

    /**
     * Executes the mark/unmark command.
     * Changes the task's completion status and saves to storage.
     *
     * @param tasks The task list containing the task to mark/unmark.
     * @param ui The UI to display confirmation.
     * @param storage The storage to save the updated task list.
     * @throws ReverieException If the task list is empty, task number is invalid, or storage fails.
     */
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
