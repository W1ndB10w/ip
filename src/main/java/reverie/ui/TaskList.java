package reverie.ui;

import reverie.exception.ReverieException;
import reverie.task.Task;
import java.util.ArrayList;

/**
 * Represents a list of tasks in the Reverie chatbot.
 * A <code>TaskList</code> object manages a collection of tasks and provides
 * methods to add, delete, retrieve, and search tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the specified tasks.
     *
     * @param tasks The list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list at the specified index.
     *
     * @param index The index of the task to delete (0-based).
     * @return The deleted task.
     * @throws ReverieException If the index is out of bounds.
     */
    public Task delete(int index) throws ReverieException {
        if (index < 0 || index >= tasks.size()) {
            throw new ReverieException("Invalid task number! Please select between 1 and " + tasks.size());
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param index The index of the task to retrieve (0-based).
     * @return The task at the specified index.
     * @throws ReverieException If the index is out of bounds.
     */
    public Task get(int index) throws ReverieException {
        if (index < 0 || index >= tasks.size()) {
            throw new ReverieException("Invalid task number! Please select between 1 and " + tasks.size());
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return True if the task list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns a copy of all tasks in the list.
     *
     * @return A new ArrayList containing all tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Finds tasks that match the specified keyword.
     * The search is case-insensitive and searches in the full task representation
     * including task number and status.
     *
     * @param keyword The keyword to search for.
     * @return A list of indices of matching tasks.
     */
    public ArrayList<Integer> findTaskIndices(String keyword) {
        ArrayList<Integer> matchingIndices = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (int i = 0; i < tasks.size(); i++) {
            // Search in the full task representation (including task number and status)
            String fullEntry = (i + 1) + "." + tasks.get(i).getFullStatus();
            if (fullEntry.toLowerCase().contains(lowerKeyword)) {
                matchingIndices.add(i);
            }
        }

        return matchingIndices;
    }
}
