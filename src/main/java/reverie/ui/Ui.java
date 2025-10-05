package reverie.ui;

import reverie.exception.ReverieException;
import reverie.task.Task;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents the user interface of the Reverie chatbot.
 * A <code>Ui</code> object handles all interactions with the user,
 * including displaying messages and reading user input.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private final Scanner scanner;
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    /**
     * Constructs a Ui object and initializes the scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the ASCII art logo of Reverie.
     *
     * @return An array of strings representing the logo lines.
     */
    private String[] getLogo() {
        return new String[]{
                "██████╗ ███████╗██╗   ██╗███████╗██████╗ ██╗███████╗",
                "██╔══██╗██╔════╝██║   ██║██╔════╝██╔══██╗██║██╔════╝",
                "██████╔╝█████╗  ██║   ██║█████╗  ██████╔╝██║█████╗  ",
                "██╔══██╗██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══██╗██║██╔══╝  ",
                "██║  ██║███████╗ ╚████╔╝ ███████╗██║  ██║██║███████╗",
                "╚═╝  ╚═╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝╚═╝╚══════╝"
        };
    }

    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Displays the welcome message with the Reverie logo.
     */
    public void showWelcome() {
        String[] logo = getLogo();  // Reverie picture

        showLine();

        // Print Reverie picture
        for (String line : logo) {
            System.out.println(line);
        }

        // Welcome message
        System.out.println(" Hello! I'm Reverie");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /**
     * Reads a command from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message when tasks cannot be loaded from file.
     */
    public void showLoadingError() {
        System.out.println(" Error loading tasks from file. Starting with empty task list.");
        showLine();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(" Error: " + message);
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks after addition.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task.getFullStatus());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays a message confirming that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks after deletion.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task.getFullStatus());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Displays a message confirming that a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task.getFullStatus());
    }

    /**
     * Displays a message confirming that a task has been marked as unfinished.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as unfinished:");
        System.out.println("   " + task.getFullStatus());
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks The task list to display.
     * @throws ReverieException If there is an error accessing tasks.
     */
    public void showTaskList(TaskList tasks) throws ReverieException {
        if (tasks.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i).getFullStatus());
            }
        }
    }

    /* public void showFoundTasks(TaskList tasks) throws ReverieException {
        if (tasks.isEmpty()) {
            System.out.println(" No matching tasks found!");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i).getFullStatus());
            }
        }
    } */

    /**
     * Displays tasks that match a search query.
     *
     * @param tasks The complete task list.
     * @param matchingIndices The indices of tasks that match the search.
     * @throws ReverieException If there is an error accessing tasks.
     */
    public void showFoundTasks(TaskList tasks, ArrayList<Integer> matchingIndices) throws ReverieException {
        if (matchingIndices.isEmpty()) {
            System.out.println(" No matching tasks found!");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            for (int index : matchingIndices) {
                System.out.println(" " + (index + 1) + "." + tasks.get(index).getFullStatus());
            }
        }
    }

    /**
     * Displays tasks scheduled for a specific date.
     *
     * @param tasks The complete task list.
     * @param matchingIndices The indices of tasks scheduled for the date.
     * @param date The date to display the schedule for.
     * @throws ReverieException If there is an error accessing tasks.
     */
    public void showSchedule(TaskList tasks, ArrayList<Integer> matchingIndices, LocalDate date) throws ReverieException {
        String formattedDate = date.format(OUTPUT_FORMAT);
        if (matchingIndices.isEmpty()) {
            System.out.println(" No tasks scheduled for " + formattedDate + "!");
        } else {
            System.out.println(" Here are the tasks scheduled for " + formattedDate + ":");
            for (int index : matchingIndices) {
                System.out.println(" " + (index + 1) + "." + tasks.get(index).getFullStatus());
            }
        }
    }

    /**
     * Displays the number of tasks loaded from file.
     *
     * @param count The number of tasks loaded.
     */
    public void showLoadedTasks(int count) {
        if (count > 0) {
            showLine();
            System.out.println(" Loaded " + count + " task(s) from file.");
        }
    }

    /**
     * Displays a goodbye message.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Closes the scanner and releases resources.
     */
    public void close() {
        scanner.close();
    }
}
