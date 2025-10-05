package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents an abstract command in the Reverie chatbot.
 * A <code>Command</code> object encapsulates a user action that can be executed.
 * Subclasses implement specific command behaviors.
 */
public abstract class Command {
    /**
     * Executes the command with the provided task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI for user interaction.
     * @param storage The storage for persisting tasks.
     * @throws ReverieException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException;

    /**
     * Indicates whether this command will cause the application to exit.
     *
     * @return True if this is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
