package reverie;

import reverie.command.Command;
import reverie.exception.ReverieException;
import reverie.parser.Parser;
import reverie.storage.Storage;
import reverie.ui.TaskList;
import reverie.ui.Ui;

public class Reverie {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

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

    public static void main(String[] args) {
        new Reverie("./data/reverie.txt").run();
    }
}
