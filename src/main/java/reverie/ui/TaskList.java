package reverie.ui;

import reverie.exception.ReverieException;
import reverie.task.Task;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws ReverieException {
        if (index < 0 || index >= tasks.size()) {
            throw new ReverieException("Invalid task number! Please select between 1 and " + tasks.size());
        }
        return tasks.remove(index);
    }

    public Task get(int index) throws ReverieException {
        if (index < 0 || index >= tasks.size()) {
            throw new ReverieException("Invalid task number! Please select between 1 and " + tasks.size());
        }
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public ArrayList<Integer> findTaskIndices(String keyword) {
        ArrayList<Integer> matchingIndices = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).description.toLowerCase().contains(lowerKeyword)) {
                matchingIndices.add(i);
            }
        }

        return matchingIndices;
    }
    /* public TaskList findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }

        return new TaskList(matchingTasks);
    } */
}
