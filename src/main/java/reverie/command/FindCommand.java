package reverie.command;

import reverie.exception.ReverieException;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;
import java.util.ArrayList;

/**
 * Represents a command to find tasks matching a keyword.
 * A <code>FindCommand</code> searches for tasks containing the specified keyword
 * and displays the matching results.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command to search for matching tasks.
     * The search is case-insensitive and matches against the full task representation.
     *
     * @param tasks The task list to search.
     * @param ui The UI to display search results.
     * @param storage The storage (not used in this command).
     * @throws ReverieException If the keyword is empty.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ReverieException {
        if (keyword.trim().isEmpty()) {
            throw new ReverieException("Please specify a keyword to search for");
        }

        ArrayList<Integer> matchingIndices = tasks.findTaskIndices(keyword);
        ui.showFoundTasks(tasks, matchingIndices);
    }
}
