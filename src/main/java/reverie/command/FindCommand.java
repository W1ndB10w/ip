package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;
import java.util.ArrayList;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        if (keyword.trim().isEmpty()) {
            throw new ReverieException("Please specify a keyword to search for");
        }

        ArrayList<Integer> matchingIndices = tasks.findTaskIndices(keyword);
        ui.showFoundTasks(tasks, matchingIndices);
    }
}
