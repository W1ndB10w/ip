package reverie;

import reverie.command.Command;
import reverie.exception.ReverieException;
import reverie.parser.Parser;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;

/**
 * Represents the main Reverie chatbot application.
 * A <code>Reverie</code> object manages the task list, user interface, storage,
 * and command execution loop.
 */
public class Reverie {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Reverie chatbot with the specified file path for data storage.
     * Initializes the UI, storage, and attempts to load existing tasks from file.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Reverie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
            ui.showLoadedTasks(tasks.size());
        } catch (ReverieException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop of the chatbot.
     * Continuously reads user commands, parses them, executes them,
     * and displays results until an exit command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (ReverieException e) {
                ui.showError(e.getMessage());
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }

        ui.close();
    }

    /**
     * Main entry point of the Reverie application.
     * Creates a new Reverie instance with default data file path and starts the program.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Reverie("./data/reverie.txt").run();
    }
}
