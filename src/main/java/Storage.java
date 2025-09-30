import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Storage {
    private static final String FILE_PATH = "./data/reverie.txt";

    public Storage() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        File directory = new File("./data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void save(Task[] tasks, int taskCount) throws ReverieException {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (int i = 0; i < taskCount; i++) {
                writer.write(taskToFileFormat(tasks[i]) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new ReverieException("Error saving tasks to file: " + e.getMessage());
        }
    }
}
