package reverie.storage;

import reverie.exception.ReverieException;
import reverie.task.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents the storage handler for the Reverie chatbot.
 * A <code>Storage</code> object handles reading from and writing to
 * the data file that stores task information.
 */
public class Storage {
    private final String filePath;
    private static final String DELIMITER = " \\| ";
    private static final String DONE_MARKER = "1";

    /**
     * Constructs a Storage object with the specified file path.
     * Creates the data directory if it doesn't exist.
     *
     * @param filePath The path to the data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        createDataDirectory();
    }

    /**
     * Creates the data directory if it doesn't exist.
     * Displays a warning if directory creation fails.
     */
    private void createDataDirectory() {
        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists() && !directory.mkdirs()) {
            System.out.println(" Warning: Could not create data directory");
        }
    }

    /**
     * Saves the list of tasks to the data file.
     * Overwrites any existing content in the file.
     *
     * @param tasks The list of tasks to save.
     * @throws ReverieException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws ReverieException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writeTasksToFile(writer, tasks);
        } catch (IOException e) {
            throw new ReverieException("Error saving tasks to file: " + e.getMessage());
        }
    }

    /**
     * Writes all tasks to the file using the specified FileWriter.
     *
     * @param writer The FileWriter to use for writing.
     * @param tasks The list of tasks to write.
     * @throws IOException If there is an error writing to the file.
     */
    private void writeTasksToFile(FileWriter writer, ArrayList<Task> tasks) throws IOException {
        for (Task task : tasks) {
            writer.write(taskToFileFormat(task) + System.lineSeparator());
        }
    }

    /**
     * Loads tasks from the data file.
     * Returns an empty list if the file doesn't exist.
     *
     * @return A list of tasks loaded from the file.
     * @throws ReverieException If there is an error reading from the file.
     */
    public ArrayList<Task> load() throws ReverieException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return loadTasksFromFile(file);
    }

    /**
     * Loads tasks from the specified file.
     * Skips corrupted lines and displays warnings for them.
     *
     * @param file The file to load tasks from.
     * @return A list of loaded tasks.
     * @throws ReverieException If there is an error reading from the file.
     */
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

    /**
     * Processes a single line from the data file and adds the parsed task to the list.
     * Displays a warning and skips the line if it's corrupted.
     *
     * @param line The line to process.
     * @param tasks The list to add the parsed task to.
     */
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

    /**
     * Converts a task to its file format representation.
     * The format varies based on the task type (Todo, Deadline, or Event).
     *
     * @param task The task to convert.
     * @return The file format string representation of the task.
     */
    private String taskToFileFormat(Task task) {
        String isDone = task.isDone() ? DONE_MARKER : "0";

        if (task instanceof Todo) {
            return formatTodo(task, isDone);
        } else if (task instanceof Deadline) {
            return formatDeadline((Deadline) task, isDone);
        } else if (task instanceof Event) {
            return formatEvent((Event) task, isDone);
        }
        return "";
    }

    /**
     * Formats a Todo task for file storage.
     *
     * @param task The Todo task.
     * @param isDone The completion status marker.
     * @return The formatted string.
     */
    private String formatTodo(Task task, String isDone) {
        return "T | " + isDone + " | " + task.getDescription();
    }

    /**
     * Formats a Deadline task for file storage.
     *
     * @param deadline The Deadline task.
     * @param isDone The completion status marker.
     * @return The formatted string.
     */
    private String formatDeadline(Deadline deadline, String isDone) {
        return "D | " + isDone + " | " + deadline.getDescription() + " | " +
                deadline.getByString() + " | " + (deadline.hasTime() ? "1" : "0");
    }

    /**
     * Formats an Event task for file storage.
     *
     * @param event The Event task.
     * @param isDone The completion status marker.
     * @return The formatted string.
     */
    private String formatEvent(Event event, String isDone) {
        return "E | " + isDone + " | " + event.getDescription() + " | " +
                event.getFromString() + " | " + event.getToString() + " | " +
                (event.hasTime() ? "1" : "0");
    }

    /**
     * Parses a task from a file format line.
     *
     * @param line The line to parse.
     * @return The parsed Task object.
     * @throws ReverieException If the line format is invalid.
     */
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

    /**
     * Validates that the basic file format has at least 3 parts.
     *
     * @param parts The parts of the split line.
     * @throws ReverieException If the format is invalid.
     */
    private void validateBasicFormat(String[] parts) throws ReverieException {
        if (parts.length < 3) {
            throw new ReverieException("Invalid file format");
        }
    }

    /**
     * Creates a task based on the task type identifier.
     *
     * @param taskType The task type (T, D, or E).
     * @param description The task description.
     * @param parts The parts of the split line.
     * @return The created Task object.
     * @throws ReverieException If the task type is unknown or format is invalid.
     */
    private Task createTaskByType(String taskType, String description, String[] parts)
            throws ReverieException {
        return switch (taskType) {
            case "T" -> createTodo(description, parts);
            case "D" -> createDeadline(description, parts);
            case "E" -> createEvent(description, parts);
            default -> throw new ReverieException("Unknown task type: " + taskType);
        };
    }

    /**
     * Creates a Todo task from file parts.
     *
     * @param description The task description.
     * @param parts The parts of the split line.
     * @return The created Todo task.
     * @throws ReverieException If the format is invalid.
     */
    private Task createTodo(String description, String[] parts) throws ReverieException {
        if (parts.length != 3) {
            throw new ReverieException("Invalid Todo format");
        }
        return new Todo(description);
    }

    /**
     * Creates a Deadline task from file parts.
     * Supports both old format (without time flag) and new format (with time flag).
     *
     * @param description The task description.
     * @param parts The parts of the split line.
     * @return The created Deadline task.
     * @throws ReverieException If the format is invalid.
     */
    private Task createDeadline(String description, String[] parts) throws ReverieException {
        if (parts.length == 4) {
            // Old format without hasTime flag - default to false (date only)
            return new Deadline(description, parts[3].trim(), false);
        } else if (parts.length == 5) {
            // New format with hasTime flag
            boolean hasTime = parts[4].trim().equals("1");
            return new Deadline(description, parts[3].trim(), hasTime);
        } else {
            throw new ReverieException("Invalid Deadline format");
        }
    }

    /**
     * Creates an Event task from file parts.
     * Supports both old format (without time flag) and new format (with time flag).
     *
     * @param description The task description.
     * @param parts The parts of the split line.
     * @return The created Event task.
     * @throws ReverieException If the format is invalid.
     */
    private Task createEvent(String description, String[] parts) throws ReverieException {
        if (parts.length == 5) {
            // Old format without hasTime flag - default to false (date only)
            return new Event(description, parts[3].trim(), parts[4].trim(), false);
        } else if (parts.length == 6) {
            // New format with hasTime flag
            boolean hasTime = parts[5].trim().equals("1");
            return new Event(description, parts[3].trim(), parts[4].trim(), hasTime);
        } else {
            throw new ReverieException("Invalid Event format");
        }
    }
}
