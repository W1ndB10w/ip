package reverie.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String getFullStatus() {
        return "[T]" + super.getFullStatus();
    }
}
