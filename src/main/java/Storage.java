import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private final String filePath;
    private static final String DELIMITER = " \\| ";
    private static final String DONE_MARKER = "1";
    /* private static final String FILE_PATH = "./data/reverie.txt";
    private static final String DATA_DIRECTORY = "./data";
    private static final String DELIMITER = " \\| ";
    private static final String DONE_MARKER = "1";

    public Storage() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            System.out.println(" Warning: Could not create data directory");
        }
    }

    public void save(ArrayList<Task> tasks) throws ReverieException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writeTasksToFile(writer, tasks);
        } catch (IOException e) {
            throw new ReverieException("Error saving tasks to file: " + e.getMessage());
        }
    }

    private void writeTasksToFile(FileWriter writer, ArrayList<Task> tasks) throws IOException {
        for (Task task : tasks) {
            writer.write(taskToFileFormat(task) + System.lineSeparator());
        }
    }

    public ArrayList<Task> load() throws ReverieException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return loadTasksFromFile(file);
    }

    private ArrayList<Task> loadTasksFromFile(File file) throws ReverieException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                processLine(scanner.nextLine(), loadedTasks);
            }
        } catch (FileNotFoundException e) {
            throw new ReverieException("Error reading file: " + e.getMessage());
        }
        return loadedTasks;
    }

    private void processLine(String line, ArrayList<Task> tasks) {
        try {
            Task task = parseTaskFromFile(line);
            if (task != null) {
                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println(" Warning: Skipping corrupted line: " + line);
        }
    }

    private String taskToFileFormat(Task task) {
        String isDone = task.isDone ? DONE_MARKER : "0";

        if (task instanceof Todo) {
            return formatTodo(task, isDone);
        } else if (task instanceof Deadline) {
            return formatDeadline((Deadline) task, isDone);
        } else if (task instanceof Event) {
            return formatEvent((Event) task, isDone);
        }
        return "";
    }

    private String formatTodo(Task task, String isDone) {
        return "T | " + isDone + " | " + task.description;
    }

    private String formatDeadline(Deadline deadline, String isDone) {
        return "D | " + isDone + " | " + deadline.description + " | " + deadline.by;
    }

    private String formatEvent(Event event, String isDone) {
        return "E | " + isDone + " | " + event.description + " | " + event.from + " | " + event.to;
    }

    private Task parseTaskFromFile(String line) throws ReverieException {
        String[] parts = line.split(DELIMITER);
        validateBasicFormat(parts);

        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals(DONE_MARKER);
        String description = parts[2].trim();

        Task task = createTaskByType(taskType, description, parts);

        if (isDone) {
            task.markAsDone();
        }

        return task;
    }

    private void validateBasicFormat(String[] parts) throws ReverieException {
        if (parts.length < 3) {
            throw new ReverieException("Invalid file format");
        }
    }

    private Task createTaskByType(String taskType, String description, String[] parts)
            throws ReverieException {
        return switch (taskType) {
            case "T" -> createTodo(description, parts);
            case "D" -> createDeadline(description, parts);
            case "E" -> createEvent(description, parts);
            default -> throw new ReverieException("Unknown task type: " + taskType);
        };
    }

    private Task createTodo(String description, String[] parts) throws ReverieException {
        if (parts.length != 3) {
            throw new ReverieException("Invalid Todo format");
        }
        return new Todo(description);
    }

    private Task createDeadline(String description, String[] parts) throws ReverieException {
        if (parts.length != 4) {
            throw new ReverieException("Invalid Deadline format");
        }
        return new Deadline(description, parts[3].trim());
    }

    private Task createEvent(String description, String[] parts) throws ReverieException {
        if (parts.length != 5) {
            throw new ReverieException("Invalid Event format");
        }
        return new Event(description, parts[3].trim(), parts[4].trim());
    } */
}
