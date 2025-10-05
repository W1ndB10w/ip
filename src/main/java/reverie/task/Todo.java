package reverie.task;

/**
 * Represents a todo task without any date/time attached.
 * A <code>Todo</code> object is a simple task with only a description.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the full status string representation of the todo task.
     * The format is: [T][status icon] description
     *
     * @return The formatted status string.
     */
    @Override
    public String getFullStatus() {
        return "[T]" + super.getFullStatus();
    }
}
