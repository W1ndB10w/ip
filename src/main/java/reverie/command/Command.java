package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException;

    public boolean isExit() {
        return false;
    }
}
