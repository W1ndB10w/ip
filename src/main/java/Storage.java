import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/reverie.txt";

    public Storage() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        File directory = new File("./data");
        if (!directory.exists()) {
            boolean result = directory.mkdirs(); // Capture the result
            if (!result) {
                System.out.println(" Warning: Could not create data directory");
            }
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

    public ArrayList<Task> load() throws ReverieException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return loadedTasks; // Return empty list if file doesn't exist
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Task task = parseTaskFromFile(line);
                    if (task != null) {
                        loadedTasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println(" Warning: Skipping corrupted line: " + line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new ReverieException("Error reading file: " + e.getMessage());
        }

        return loadedTasks;
    }

    private String taskToFileFormat(Task task) {
        String isDone = task.isDone ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + isDone + " | " + task.description;
        } else if (task instanceof Deadline deadline) {
            return "D | " + isDone + " | " + task.description + " | " + deadline.by;
        } else if (task instanceof Event event) {
            return "E | " + isDone + " | " + task.description + " | " + event.from + " | " + event.to;
        }

        return "";
    }

    private Task parseTaskFromFile(String line) throws ReverieException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new ReverieException("Invalid file format");
        }

        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task;

        switch (taskType) {
            case "T":
                if (parts.length != 3) {
                    throw new ReverieException("Invalid Todo format");
                }
                task = new Todo(description);
                break;
            case "D":
                if (parts.length != 4) {
                    throw new ReverieException("Invalid Deadline format");
                }
                String by = parts[3].trim();
                task = new Deadline(description, by);
                break;
            case "E":
                if (parts.length != 5) {
                    throw new ReverieException("Invalid Event format");
                }
                String from = parts[3].trim();
                String to = parts[4].trim();
                task = new Event(description, from, to);
                break;
            default:
                throw new ReverieException("Unknown task type: " + taskType);
        }

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }
}
