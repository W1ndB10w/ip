import java.io.File;

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
}
