package reverie.command;

import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents a command to exit the Reverie chatbot.
 * An <code>ExitCommand</code> displays a goodbye message and signals
 * the application to terminate.
 */
public class ExitCommand extends Command {
    /**
     * Executes the exit command to display a goodbye message.
     *
     * @param tasks The task list (not used in this command).
     * @param ui The UI to display the goodbye message.
     * @param storage The storage (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Indicates that this is an exit command.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
